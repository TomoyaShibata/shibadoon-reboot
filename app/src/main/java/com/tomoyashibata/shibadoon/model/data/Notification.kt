package com.tomoyashibata.shibadoon.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Notification(
  val id: Long,
  val type: NotificationType,
  @Json(name = "created_at")
  val createdAt: String,
  val account: Account,
  val status: Status?
)

enum class NotificationType {
  mention,
  reblog,
  favourite,
  follow
}
