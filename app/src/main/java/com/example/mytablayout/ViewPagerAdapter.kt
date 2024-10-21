package com.example.mytablayout

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        const val TAB_LOGIN = 1
        const val TAB_REGISTER = 0
        const val TAB_COUNT = 2
    }

    override fun getItemCount(): Int = TAB_COUNT

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            TAB_LOGIN -> LoginFragment()
            TAB_REGISTER -> RegisterFragment()
            else -> throw IllegalArgumentException("Invalid position")
        }
    }
}
