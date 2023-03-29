package com.bignerdranch.logintest.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bignerdranch.logintest.FeedFragment
import com.bignerdranch.logintest.UserProfileFragment

class MainViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {
    private val fragmentList = listOf<Fragment>(FeedFragment(),UserProfileFragment())

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment {
      return  fragmentList[position]
    }

}