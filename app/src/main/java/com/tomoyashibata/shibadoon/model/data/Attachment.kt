package com.tomoyashibata.shibadoon.model.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Attachment(
  val id: Long,
  val type: String,
  val url: String,
  @Json(name = "remote_url")
  val remoteUrl: String?,
  @Json(name = "preview_url")
  val previewUrl: String,
  @Json(name = "text_url")
  val textUrl: String?,
  //val meta: String?,
  val description: String?
)

enum class AttachmentType(
  val type: String
) {
  IMAGE("image"),
  VIDEO("video"),
  GIFV("gifv"),
  UNKNOWN("unknown")
}
