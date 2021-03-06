package com.tomoyashibata.shibadoon.ui.mediaimages

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter

class ImageMediaAttachmentsFragmentPagerAdapter(
  private val urls: List<String>,
  fm: FragmentManager
) : FragmentPagerAdapter(fm) {
  override fun getItem(position: Int): Fragment {
    return ImageMediaAttachmentFragment.newInstance(urls[position])
  }

  override fun getCount(): Int = this.urls.count()
}
