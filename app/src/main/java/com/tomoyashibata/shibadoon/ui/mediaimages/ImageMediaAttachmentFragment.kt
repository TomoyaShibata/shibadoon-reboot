package com.tomoyashibata.shibadoon.ui.mediaimages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.tomoyashibata.shibadoon.GlideApp
import com.tomoyashibata.shibadoon.databinding.FragmentImageMediaAttachmentBinding
import kotlinx.android.synthetic.main.fragment_image_media_attachment.*

class ImageMediaAttachmentFragment : Fragment() {
  companion object {
    private const val IMAGE_MEDIA_ATTACHMENT_URL = "IMAGE_MEDIA_ATTACHMENT_URL"

    fun newInstance(url: String): ImageMediaAttachmentFragment {
      return ImageMediaAttachmentFragment().also {
        it.arguments = Bundle().also { bundle ->
          bundle.putString(IMAGE_MEDIA_ATTACHMENT_URL, url)
        }
      }
    }
  }

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val binding = FragmentImageMediaAttachmentBinding.inflate(inflater, container, false)
    binding.setLifecycleOwner(this)
    return binding.root
  }

  override fun onActivityCreated(savedInstanceState: Bundle?) {
    super.onActivityCreated(savedInstanceState)
    this.photo_view.transitionName = this.arguments?.getString(IMAGE_MEDIA_ATTACHMENT_URL)
    this.arguments?.getString(IMAGE_MEDIA_ATTACHMENT_URL)?.let { this.setupPhotoView(it) }
  }

  private fun setupPhotoView(url: String) {
    GlideApp.with(this.requireContext())
      .load(url)
      .transition(DrawableTransitionOptions.withCrossFade())
      .into(this.photo_view)
  }
}
