package com.example.mygithub.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class SectionsPagerAdapter (activity: AppCompatActivity, cek: String) : FragmentStateAdapter(activity) {

    var username: String = cek
    override fun createFragment(position: Int): Fragment {
        val fragment = HomeFragment()
        fragment.arguments = Bundle().apply {
            putString(HomeFragment.ARG_USERNAME, username)
            putInt(HomeFragment.ARG_POSITION, position + 1)
        }
        return fragment
    }
    override fun getItemCount(): Int {
        return 2
    }
}