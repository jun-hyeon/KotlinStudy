package com.example.matchingapp

import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.animation.AnticipateInterpolator
import androidx.annotation.RequiresApi
import androidx.core.animation.doOnEnd
import androidx.core.splashscreen.SplashScreen
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.matchingapp.auth.IntroActivity
import com.example.matchingapp.databinding.ActivityMainBinding
import com.example.matchingapp.slider.CardStackAdapter
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.yuyakaido.android.cardstackview.CardStackLayoutManager
import com.yuyakaido.android.cardstackview.CardStackListener
import com.yuyakaido.android.cardstackview.Direction

class MainActivity : AppCompatActivity() {

    private val binding : ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    private lateinit var cardStackAdapter : CardStackAdapter
    private lateinit var manager : CardStackLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        binding.settingIcon.setOnClickListener {
            val auth = Firebase.auth
            auth.signOut()

            val intent = Intent(this, IntroActivity::class.java)
            startActivity(intent)
        }

        val cardStackView = binding.cardStackView
        manager = CardStackLayoutManager(baseContext, object  : CardStackListener{
            override fun onCardDragging(direction: Direction?, ratio: Float) {

            }

            override fun onCardSwiped(direction: Direction?) {

            }

            override fun onCardRewound() {

            }

            override fun onCardCanceled() {

            }

            override fun onCardAppeared(view: View?, position: Int) {

            }

            override fun onCardDisappeared(view: View?, position: Int) {

            }

        })
        val testList = mutableListOf<String>()
        testList.add("a")
        testList.add("b")
        testList.add("c")


        cardStackView.apply {
            cardStackAdapter = CardStackAdapter(testList)
            this.adapter = cardStackAdapter
            this.layoutManager = manager
        }
    }


}