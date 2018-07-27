package com.tomoyashibata.shibadoon.model.data

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class ValidAccountToken(
  @PrimaryKey(autoGenerate = true)
  val id: Long= 0,
  val instance: String,
  val accountId: String,
  val accessToken: String,
  val isCurrent: Boolean
)
