package com.tomoyashibata.shibadoon.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import com.tomoyashibata.shibadoon.model.data.Token

@Dao
interface TokenDao {
  @Query("SELECT * FROM Token")
  fun getAll(): List<Token>

  @Insert
  fun insert(token: Token)

  @Update
  fun update(token: Token)
}
