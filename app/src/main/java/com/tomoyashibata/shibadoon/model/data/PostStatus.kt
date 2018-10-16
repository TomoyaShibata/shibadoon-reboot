package com.tomoyashibata.shibadoon.model.data

import com.squareup.moshi.Json

data class PostStatus(
  val status: String,
  @Json(name = "in_reply_to_id")
  val inReplyToId: Long? = null,
  @Json(name = "media_ids")
  val mediaIds: Array<Long>? = null,
  @Json(name = "sensitive")
  val sensitive: Boolean? = null,
  @Json(name = "spoiler_text")
  val spoilerText: String? = null,
  @Json(name = "visibility")
  val visibility: String? = null,
  @Json(name = "language")
  val language: String? = null
)
