package com.tomoyashibata.shibadoon.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Emoji(
  val shortcode: String,
  @Json(name = "static_url")
  val staticUrl: String,
  val url: String,
  @Json(name = "visible_in_picker")
  val visibleInPicker: Boolean
)
