package com.tomoyashibata.shibadoon.model.repository

import com.tomoyashibata.shibadoon.database.AppDatabase
import com.tomoyashibata.shibadoon.model.data.ValidAccountToken

class ValidAccountTokenRepository(
  private val database: AppDatabase
) {
  fun getAll(): List<ValidAccountToken> {
    return this.database.validAccountTokenDao().getAll()
  }

  fun insert(validAccountToken: ValidAccountToken) {
    this.database.validAccountTokenDao().insert(validAccountToken)
  }

  fun update(validAccountToken: ValidAccountToken) {
    this.database.validAccountTokenDao().update(validAccountToken)
  }
}
