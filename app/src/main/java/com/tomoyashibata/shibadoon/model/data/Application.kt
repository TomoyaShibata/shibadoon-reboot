package com.tomoyashibata.shibadoon.model.data

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Application(
  val name: String,
  val website: String?
)
