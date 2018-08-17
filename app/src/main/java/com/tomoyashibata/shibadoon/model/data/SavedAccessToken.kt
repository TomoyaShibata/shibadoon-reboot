package com.tomoyashibata.shibadoon.model.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class SavedAccessToken(
  @PrimaryKey(autoGenerate = true)
  val id: Long = 0,
  val accessToken: String,
  val instance: String
)
