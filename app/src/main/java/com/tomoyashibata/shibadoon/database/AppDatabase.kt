package com.tomoyashibata.shibadoon.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.tomoyashibata.shibadoon.dao.TokenDao
import com.tomoyashibata.shibadoon.dao.ValidAccountTokenDao
import com.tomoyashibata.shibadoon.model.data.Token
import com.tomoyashibata.shibadoon.model.data.ValidAccountToken

@Database(entities = [
  Token::class,
  ValidAccountToken::class
], version = 1)
abstract class AppDatabase : RoomDatabase() {
  abstract fun tokenDao(): TokenDao
  abstract fun validAccountTokenDao(): ValidAccountTokenDao
}
