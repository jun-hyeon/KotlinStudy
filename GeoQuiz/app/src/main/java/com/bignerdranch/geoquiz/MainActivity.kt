@file:Suppress("UnusedImport")

package com.bignerdranch.geoquiz

import android.app.ActivityOptions
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    private lateinit var trueButton : Button
    private lateinit var falseButton : Button
    private lateinit var nextButton : ImageButton
    private lateinit var previousButton : ImageButton
    private lateinit var questionTextView : TextView
    private lateinit var cheatButton: Button

    private val quizViewModel : QuizViewModel by lazy {
        ViewModelProvider(this)[QuizViewModel::class.java]
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG,"onCreate(Bundle?) called")
        setContentView(R.layout.activity_main)

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX,0) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById(R.id.true_button)
        falseButton = findViewById(R.id.false_button)
        nextButton = findViewById(R.id.next_Button)
        previousButton = findViewById(R.id.previous_Button)
        questionTextView = findViewById(R.id.question_text_view)
        cheatButton = findViewById(R.id.cheat_Button)




        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        previousButton.setOnClickListener {
            quizViewModel.moveToPrevious()
            updateQuestion()
        }


        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){

            if(it.resultCode == RESULT_OK){
                quizViewModel.isCheater = it.data?.getBooleanExtra(EXTRA_ANSWER_SHOWN,false) ?: false
            }

        }

        cheatButton.setOnClickListener{
            val answerIsTrue = quizViewModel.currentQuestionAnswer
            val intent = CheatActivity.newIntent(this@MainActivity,answerIsTrue)
            val options = ActivityOptions.makeClipRevealAnimation(it,0,0,it.width,it.height)

            resultLauncher.launch(intent)

        }

        updateQuestion()
    }


    private fun updateQuestion(){
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    private fun checkAnswer(userAnswer : Boolean){
        val correctAnswer : Boolean = quizViewModel.currentQuestionAnswer
       val messageResId = when{
           quizViewModel.isCheater -> R.string.judgement_toast
           userAnswer ==  correctAnswer -> R.string.correct_toast
           else -> R.string.incorrect_toast
       }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG,"onStart() called")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG,"onResume() called")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG,"onPause() called")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.d(TAG,"onSaveInstanceState")
        outState.putInt(KEY_INDEX,quizViewModel.currentIndex)
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG,"onStop() called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG,"onDestroy() called")
    }

}























