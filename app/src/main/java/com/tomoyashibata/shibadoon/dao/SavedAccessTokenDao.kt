package com.tomoyashibata.shibadoon.dao

import android.arch.persistence.room.*
import com.tomoyashibata.shibadoon.model.data.SavedAccessToken

@Dao
interface SavedAccessTokenDao {
  @Query("SELECT * FROM SavedAccessToken")
  fun getAll(): List<SavedAccessToken>

  @Query("SELECT * FROM SavedAccessToken")
  fun get(): SavedAccessToken

  @Query("SELECT * FROM SavedAccessToken WHERE id = :id")
  fun getById(id: Long): SavedAccessToken

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  fun insert(savedAccessToken: SavedAccessToken): Long

  @Delete
  fun delete(savedAccessToken: SavedAccessToken)
}
