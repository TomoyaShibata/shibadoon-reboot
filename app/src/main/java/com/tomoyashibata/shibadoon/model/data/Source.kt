package com.tomoyashibata.shibadoon.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Source(
  val privacy: String,
  val sensitive: Boolean,
  val note: String,
  val fields: List<Any>
)
