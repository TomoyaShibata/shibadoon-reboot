package com.tomoyashibata.shibadoon.model.data

import com.squareup.moshi.Json

data class Account(
  val id: String,
  val username: String,
  val acct: String,
  @Json(name = "display_name")
  val displayName: String,
  val locked: Boolean,
  @Json(name = "created_at")
  val createdAt: String,
  val note: String,
  val url: String,
  val avatar: String,
  @Json(name = "avatarStatic")
  val avatar_static: String,
  val header: String,
  @Json(name = "header_static")
  val headerStatic: String,
  @Json(name = "followers_count")
  val followersCount: Number,
  @Json(name = "following_count")
  val followingCount: Number,
  @Json(name = "statuses_count")
  val statusesCount: Number,
  val source: Source,
  val emojis: List<Any>,
  val moved: Account?,
  val fields: List<Any>?,
  val bot: Boolean?
)
