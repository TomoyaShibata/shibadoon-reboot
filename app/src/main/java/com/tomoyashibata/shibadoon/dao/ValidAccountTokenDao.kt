package com.tomoyashibata.shibadoon.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.tomoyashibata.shibadoon.model.data.ValidAccountToken

@Dao
interface ValidAccountTokenDao {
  @Query("SELECT * FROM ValidAccountToken")
  fun getAll(): List<ValidAccountToken>

  @Insert
  fun insert(validAccountToken: ValidAccountToken)

  @Update
  fun update(validAccountToken: ValidAccountToken)
}
