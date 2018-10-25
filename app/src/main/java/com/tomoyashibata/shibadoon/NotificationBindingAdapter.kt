package com.tomoyashibata.shibadoon

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.tomoyashibata.shibadoon.model.data.Account

@BindingAdapter("notificationUserAvatar")
fun ImageView.setNotificationUserAvatar(account: Account) {
  GlideApp.with(this)
    .load(account.avatar)
    .apply(RequestOptions().transform(RoundedCorners(this.resources.getDimensionPixelSize(R.dimen.user_avatar_radius))))
    .transition(DrawableTransitionOptions.withCrossFade())
    .into(this)
}
