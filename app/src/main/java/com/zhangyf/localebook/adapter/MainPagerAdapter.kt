package com.zhangyf.localebook.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.zhangyf.localebook.ui.ListFragment

class MainPagerAdapter(
    fragmentManager: FragmentManager,
    val fragmentList: List<ListFragment>
) : FragmentPagerAdapter(fragmentManager) {

    override fun getItem(position: Int): Fragment {
        return fragmentList[position]
    }

    override fun getCount() = fragmentList.size

    override fun getPageTitle(position: Int): CharSequence? {
        return fragmentList[position].bookType
    }

}