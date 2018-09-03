package com.tomoyashibata.shibadoon.ui.home

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.tomoyashibata.shibadoon.ui.hometimeline.HomeTimelineFragment

class HomeFragmentPagerAdapter(
  fm: FragmentManager
) : FragmentPagerAdapter(fm) {
  override fun getItem(p0: Int): Fragment {
    return HomeTimelineFragment.newInstance()
  }

  override fun getCount(): Int = 1
}
