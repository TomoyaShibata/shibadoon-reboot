package com.tomoyashibata.shibadoon.model.data

import com.squareup.moshi.Json

data class App(
  @Json(name = "client_name")
  val clientName: String = "Shibadoon",
  @Json(name = "redirect_uris")
  val redirectUris: String = "shibadoon://mastodon_callback/",
  val scopes: String = "read write follow",
  val website: String = "https://twitter.com/tomoya_shibata"
)
