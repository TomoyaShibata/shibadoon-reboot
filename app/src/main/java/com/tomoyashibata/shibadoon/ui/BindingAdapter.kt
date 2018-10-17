package com.tomoyashibata.shibadoon.ui

import android.graphics.Color
import android.graphics.drawable.Drawable
import android.net.Uri
import android.text.Html
import android.text.format.DateFormat
import android.text.method.LinkMovementMethod
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
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

  if (localDate == nowLocalDate) return "HH:mm:ss"
  if (localDate.year == nowLocalDate.year) return "MM.dd HH:mm:ss"

  return "YYYY.MM.dd HH:mm:ss"
}

@BindingAdapter("content")
fun TextView.setContent(status: Status) {
  this.text = Html.fromHtml(status.content, Html.FROM_HTML_MODE_COMPACT)
  this.movementMethod = LinkMovementMethod.getInstance()
  this.linksClickable = true
  this.highlightColor = Color.TRANSPARENT
}

@BindingAdapter("boostedAccount")
fun TextView.setBoostedAccount(account: Account) {
  val displayName = if (account.displayName.isBlank()) account.username else account.displayName
  this.text = this.context.getString(R.string.boosted_account_text, displayName)
}

@BindingAdapter("favoritedAccount")
fun TextView.setFavoritedAccount(account: Account) {
  val displayName = if (account.displayName.isBlank()) account.username else account.displayName
  this.text = this.context.getString(R.string.favorited_account_text, displayName)
}
