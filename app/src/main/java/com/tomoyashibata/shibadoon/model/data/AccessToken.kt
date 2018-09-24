package com.tomoyashibata.shibadoon.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Entity
@JsonClass(generateAdapter = true)
data class AccessToken(
  @PrimaryKey
  @Json(name = "access_token")
  val accessToken: String,
  @Json(name = "token_type")
  val tokenType: String,
  val scope: String,
  @Json(name = "created_at")
  val createdAt: String
)
