package com.tomoyashibata.shibadoon.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class RequestAccessToken(
  @Json(name = "grant_type")
  val grantType: String = "authorization_code",
  @Json(name = "redirect_uri")
  val redirectUri: String = "shibadoon://mastodon_callback/",
  @Json(name = "client_id")
  val clientId: String,
  @Json(name = "client_secret")
  val clientSecret: String,
  val code: String
)
