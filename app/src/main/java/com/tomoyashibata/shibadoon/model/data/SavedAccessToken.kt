package com.tomoyashibata.shibadoon.model.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SavedAccessToken(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val accessToken: String,
  val instance: String
)
