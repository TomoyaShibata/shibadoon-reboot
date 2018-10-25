package com.tomoyashibata.shibadoon.ui.mediaimages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.tomoyashibata.shibadoon.databinding.FragmentImageMediaAttachmentsBinding
import kotlinx.android.synthetic.main.fragment_image_media_attachments.*

class ImageMediaAttachmentsFragment : Fragment() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val binding = FragmentImageMediaAttachmentsBinding.inflate(inflater, container, false)
    binding.setLifecycleOwner(this)
    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val bundle = ImageMediaAttachmentsFragmentArgs.fromBundle(this.arguments)
    val urls = bundle.urls.split(",")
    val firstViewPosition = bundle.firstViewPosition
    this.setupViewPager(urls, firstViewPosition)
  }

  private fun setupViewPager(urls: List<String>, firstViewPosition: Int) {
    this.image_media_attachments_view_pager.also {
      it.adapter = ImageMediaAttachmentsFragmentPagerAdapter(urls, this.childFragmentManager)
      it.currentItem = firstViewPosition
    }
  }
}
