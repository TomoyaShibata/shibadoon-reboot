package com.tomoyashibata.shibadoon.model.repository

import android.content.SharedPreferences
import androidx.core.content.edit
import com.tomoyashibata.shibadoon.database.AppDatabase
import com.tomoyashibata.shibadoon.model.data.SavedAccessToken

class SavedAccessTokenRepository(
  private val database: AppDatabase,
  private val sharedPreferences: SharedPreferences
) {
  companion object {
    const val CURRENT_SAVED_ACCESS_TOKEN_ID = "currentSavedAccountId"
  }

  fun getSavedAccessToken(): List<SavedAccessToken> = this.database.savedAccessTokenDao().getAll()

  fun getSavedAccessToken(id: Long): SavedAccessToken = this.database.savedAccessTokenDao().getById(id)

  fun getCurrentSavedAccessToken(): SavedAccessToken {
    val currentSavedAccessTokenId = this.sharedPreferences.getLong(CURRENT_SAVED_ACCESS_TOKEN_ID, 0)
    if (currentSavedAccessTokenId < 0L) {
      this.changeCurrentSavedAccessTokenId(1)
      return this.database.savedAccessTokenDao().getById(1)
    }

    return this.database.savedAccessTokenDao().getById(currentSavedAccessTokenId)
  }

  fun addSavedAccessToken(savedAccessToken: SavedAccessToken) {
    val id = this.database.savedAccessTokenDao().insert(savedAccessToken)
    this.changeCurrentSavedAccessTokenId(id)
  }

  fun changeCurrentSavedAccessTokenId(accessTokenId: Long) {
    this.sharedPreferences.edit {
      putLong(CURRENT_SAVED_ACCESS_TOKEN_ID, accessTokenId)
    }
  }

  fun delete(savedAccessToken: SavedAccessToken) = this.database.savedAccessTokenDao().delete(savedAccessToken)
}
