package com.tomoyashibata.shibadoon.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.tomoyashibata.shibadoon.model.data.AccessToken

@Dao
interface TokenDao {
  @Query("SELECT * FROM AccessToken")
  fun getAll(): List<AccessToken>

  @Insert
  fun insert(accessToken: AccessToken)

  @Update
  fun update(token: AccessToken)
}
