package com.tomoyashibata.shibadoon.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Mention(
  val url: String,
  val username: String,
  val acct: String,
  val id: Long
)
