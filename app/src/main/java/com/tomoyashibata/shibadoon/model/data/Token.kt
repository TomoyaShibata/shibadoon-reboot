package com.tomoyashibata.shibadoon.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Token(
  @Json(name = "access_token")
  val accessToken: String,
  @Json(name = "token_type")
  val tokenType: String,
  val scope: String,
  @Json(name = "created_at")
  val createdAt: String
)
