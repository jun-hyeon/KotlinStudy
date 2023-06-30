package com.example.msololife.fragments

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.msololife.R
import com.example.msololife.board.BoardInsideActivity
import com.example.msololife.board.BoardListRVAdapter
import com.example.msololife.board.BoardModel
import com.example.msololife.board.BoardWriteActivity
import com.example.msololife.databinding.FragmentTalkBinding
import com.example.msololife.utils.FBRef
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener


class TalkFragment : Fragment() {

    private lateinit var binding: FragmentTalkBinding
    private val boardList = mutableListOf<BoardModel>()
    private val boardKeyList = mutableListOf<String>()
    private lateinit var boardAdapter : BoardListRVAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater,R.layout.fragment_talk, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val rv = binding.boardListView





        boardAdapter = BoardListRVAdapter(boardList)
        rv.adapter = boardAdapter
        rv.layoutManager = LinearLayoutManager(context)

        getFBBoardData()

        boardAdapter.itemClick = object : BoardListRVAdapter.ItemClick{
            override fun onClick(view: View, position: Int) {

//                데이터 넘기는 방법 1. 인텐트로 넘기기
//                val intent = Intent(context, BoardInsideActivity::class.java)
//                intent.putExtra("title",boardList[position].title)
//                intent.putExtra("content",boardList[position].content)
//                intent.putExtra("uid",boardList[position].uid)
//                intent.putExtra("time",boardList[position].time)
//                startActivity(intent)

                // 2. FireBase에 있는 board에 대한 데이터의 id를 기반으로 다시 데이터를 받아오는 방법
                val intent = Intent(context, BoardInsideActivity::class.java)
                intent.putExtra("key", boardKeyList[position])
                startActivity(intent)
            }

        }

        binding.homeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_homeFragment)
        }

        binding.tipTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_tipFragment)
        }

        binding.bookmarkTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_bookmarkFragment)
        }

        binding.storeTap.setOnClickListener {
            it.findNavController().navigate(R.id.action_talkFragment_to_storeFragment)
        }


        binding.writeBtn.setOnClickListener {
            val intent = Intent(context, BoardWriteActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getFBBoardData(){

        FBRef.boardRef.addValueEventListener(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {

                boardList.clear()


                for (dataModel in snapshot.children){
                    Log.d("TalkFragment",snapshot.toString())
                    val item = dataModel.getValue(BoardModel::class.java)
                    boardList.add(item!!)
                    boardKeyList.add(dataModel.key.toString())

                }
                boardKeyList.reverse()
                boardList.reverse()
                boardAdapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {

            }

        })

    }

}