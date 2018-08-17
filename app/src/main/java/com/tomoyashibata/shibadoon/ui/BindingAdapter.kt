package com.tomoyashibata.shibadoon.ui

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.constraint.ConstraintLayout
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.TransitionOptions
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.tomoyashibata.shibadoon.R
import com.tomoyashibata.shibadoon.model.data.Account

@BindingAdapter("android:canonicalUsername")
fun TextView.setCanonicalUsername(account: Account?) {
  if (account == null) return

  val instance = Uri.parse(account.url).host
  this.text = this.context.getString(R.string.canonical_username_text, account.username, instance)
}

@BindingAdapter("android:userAvatar")
fun ImageView.setUserAvatar(account: Account?) {
  if (account == null) return

  Glide.with(this).load(account.avatarStatic)
    .apply(RequestOptions().transform(RoundedCorners(this.resources.getDimensionPixelSize(R.dimen.user_avatar_radius))))
    .into(this)
}

@BindingAdapter("android:accountHeader")
fun ConstraintLayout.setAccountHeader(account: Account?) {
  if (account == null) return

  Glide.with(this).load(account.headerStatic)
    .apply(RequestOptions().centerCrop())
    .into(object : SimpleTarget<Drawable>() {
      override fun onResourceReady(resource: Drawable, transition: Transition<in Drawable>?) {
        this@setAccountHeader.background = resource
      }
    })
}


