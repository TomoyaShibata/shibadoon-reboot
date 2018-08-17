package com.tomoyashibata.shibadoon.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.tomoyashibata.shibadoon.dao.SavedAccessTokenDao
import com.tomoyashibata.shibadoon.dao.TokenDao
import com.tomoyashibata.shibadoon.model.data.SavedAccessToken
import com.tomoyashibata.shibadoon.model.data.AccessToken

@Database(entities = [
  SavedAccessToken::class,
  AccessToken::class
], version = 5)
abstract class AppDatabase : RoomDatabase() {
  abstract fun savedAccessTokenDao(): SavedAccessTokenDao
  abstract fun tokenDao(): TokenDao
}
