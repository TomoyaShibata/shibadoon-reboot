package com.tomoyashibata.shibadoon.ui

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.format.DateFormat
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.text.HtmlCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.SimpleTarget
import com.bumptech.glide.request.transition.Transition
import com.tomoyashibata.shibadoon.GlideApp
import com.tomoyashibata.shibadoon.R
import com.tomoyashibata.shibadoon.model.data.Account
import com.tomoyashibata.shibadoon.model.data.Status
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import org.threeten.bp.ZonedDateTime
import org.threeten.bp.format.DateTimeFormatter

@BindingAdapter("displayName")
fun TextView.setDisplayName(account: Account) {
  val displayName = if (account.displayName.isBlank()) account.username else account.displayName
  this.text = displayName
}

@BindingAdapter("android:canonicalUsername")
fun TextView.setCanonicalUsername(account: Account?) {
  if (account == null) return

  val instance = Uri.parse(account.url).host
  this.text = this.context.getString(R.string.canonical_username_text, account.username, instance)
}

@BindingAdapter("android:userAvatar")
fun ImageView.setUserAvatar(account: Account?) {
  if (account == null) return

  GlideApp.with(this)
    .load(account.avatar)
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

@BindingAdapter("createdAt")
fun TextView.setCreatedAt(status: Status) {
  val zoneId = ZoneId.of(DateFormat.getDateFormat(this.context).timeZone.id)
  val zonedDateTime = ZonedDateTime.parse(status.createdAt).withZoneSameInstant(zoneId)
  val formatter = DateTimeFormatter.ofPattern(generateDateTimeFormatterPattern(zonedDateTime))

  this.text = zonedDateTime.format(formatter)
}

private fun generateDateTimeFormatterPattern(zonedDateTime: ZonedDateTime): String {
  val localDate = zonedDateTime.toLocalDate()
  val nowLocalDate = LocalDate.now()

  return when {
    localDate == nowLocalDate -> "HH:mm:ss"
    localDate.year == nowLocalDate.year -> "MM.dd HH:mm:ss"
    else -> "YYYY.MM.dd HH:mm:ss"
  }
}

@BindingAdapter("content")
fun TextView.setContent(status: Status) {
  // 最終行の下部に不要な行が生まれてしまうため \n を取り除いている
  this.text = HtmlCompat.fromHtml(status.content, HtmlCompat.FROM_HTML_MODE_COMPACT).removeSuffix("\n")
  this.movementMethod = LinkMovementMethod.getInstance()
  this.linksClickable = true
  this.highlightColor = Color.TRANSPARENT
}

@BindingAdapter("hasMediaAttachments")
fun ConstraintLayout.hasMediaAttachments(status: Status) {
  this.visibility = if (status.mediaAttachments.isEmpty()) View.GONE else View.VISIBLE
}

@BindingAdapter(value = ["status", "mediaAttachmentPosition"])
fun ImageView.setContentImage(status: Status, mediaAttachmentPosition: Int) {
  val mediaAttachement = status.mediaAttachments.getOrNull(mediaAttachmentPosition)
  if (mediaAttachement == null) {
    this.visibility = View.GONE
    GlideApp.with(this.context).clear(this)
    return
  }

  this.visibility = View.VISIBLE
  GlideApp.with(this.context)
    .load(mediaAttachement.url)
    .transition(DrawableTransitionOptions.withCrossFade())
    .into(this)
}

@BindingAdapter("notificationPrepend")
fun TextView.setNotificationPrepend(account: Account) {
  val displayName = if (account.displayName.isBlank()) account.username else account.displayName
  this.text = this.context.getString(R.string.notification_prepend_text, displayName)
}

@BindingAdapter("statusPrepend")
fun TextView.setStatusPrepend(account: Account) {
  val displayName = if (account.displayName.isBlank()) account.username else account.displayName
  this.text = this.context.getString(R.string.status_prepend_text, displayName)
}

@BindingAdapter("favoritedAccount")
fun TextView.setFavoritedAccount(account: Account) {
  val displayName = if (account.displayName.isBlank()) account.username else account.displayName
  this.text = this.context.getString(R.string.favorited_account_text, displayName)
}
