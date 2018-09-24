package com.tomoyashibata.shibadoon.ui.home

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.tomoyashibata.shibadoon.ui.hometimeline.HomeTimelineFragment

class HomeFragmentPagerAdapter(
  fm: androidx.fragment.app.FragmentManager
) : androidx.fragment.app.FragmentPagerAdapter(fm) {
  override fun getItem(p0: Int): androidx.fragment.app.Fragment {
    return HomeTimelineFragment.newInstance()
  }

  override fun getCount(): Int = 1
}
