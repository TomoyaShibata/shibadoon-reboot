package com.tomoyashibata.shibadoon.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
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
