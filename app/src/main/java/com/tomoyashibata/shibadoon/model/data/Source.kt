package com.tomoyashibata.shibadoon.model.data

data class Source(
  val privacy: String,
  val sensitive: Boolean,
  val note: String,
  val fields: List<Any>
)
