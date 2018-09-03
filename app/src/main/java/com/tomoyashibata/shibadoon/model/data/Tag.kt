package com.tomoyashibata.shibadoon.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Tag(
  val name: String,
  val url: String,
  val history: List<Any>?
)
