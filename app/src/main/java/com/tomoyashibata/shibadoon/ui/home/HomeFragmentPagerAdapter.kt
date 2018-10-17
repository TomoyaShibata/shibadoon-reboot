package com.tomoyashibata.shibadoon.ui.home

import com.tomoyashibata.shibadoon.ui.hometimeline.HomeTimelineFragment
import com.tomoyashibata.shibadoon.ui.notifications.NotificationsFragment

class HomeFragmentPagerAdapter(
  fm: androidx.fragment.app.FragmentManager
) : androidx.fragment.app.FragmentPagerAdapter(fm) {
  override fun getItem(p0: Int): androidx.fragment.app.Fragment {
    return when (p0) {
      0 -> HomeTimelineFragment.newInstance()
      1 -> NotificationsFragment()
      else -> HomeTimelineFragment.newInstance()
    }
  }

  override fun getCount(): Int = 2
}
