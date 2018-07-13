package com.tomoyashibata.shibadoon.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Authentication(
  val id: String,
  @Json(name = "client_id")
  val clientId: String,
  @Json(name = "client_secret")
  val clientSecret: String,
  @Json(name = "redirect_uri")
  val redirectUri: String
)
