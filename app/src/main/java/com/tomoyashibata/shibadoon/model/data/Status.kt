package com.tomoyashibata.shibadoon.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Status(
  val id: Long,
  val uri: String,
  val url: String?,
  val account: Account,
  @Json(name = "in_reply_to_id")
  val inReplyToId: Long?,
  @Json(name = "in_reply_to_account_id")
  val inReplyToAccountId: Long?,
  val reblog: Status?,
  val content: String,
  @Json(name = "created_at")
  val createdAt: String,
  val emojis: List<Emoji>,
  @Json(name = "replies_count")
  val repliesCount: Int?,
  @Json(name = "reblogs_count")
  val reblogsCount: Int?,
  @Json(name = "favourites_count")
  val favouritesCount: Int?,
  val reblogged: Boolean?,
  val favourited: Boolean?,
  val muted: Boolean?,
  val sensitive: Boolean,
  @Json(name = "spoiler_text")
  val spoilerText: String,
  val visibility: String,
  @Json(name = "media_attachments")
  val mediaAttachments: List<Attachment>,
  val mentions: List<Mention>,
  val tags: List<Tag>,
  val application: Application?,
  val language: String?,
  val pinned: Boolean?
)

enum class Visibility(
  private val visibilityString: String
) {
  PUBLIC("public"),
  UNLISTED("unlisted"),
  PRIVATE("private"),
  DIRECT("direct")
}
