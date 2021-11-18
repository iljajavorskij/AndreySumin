package org.myapp.andreysumin.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ShopItemDbModel::class],views = [],version = 1,exportSchema = false)
abstract class AppDataBase :RoomDatabase(){

    abstract fun shopListDao():ShopItemDao

    companion object {
        private var INSTANSE: AppDataBase? = null
        private val LOCK = Any()
        private val DATABASE_NAME = "name_db"

        fun getInstanse(application: Application): AppDataBase {
            INSTANSE?.let {
                return it
            }
            synchronized(LOCK) {
                    INSTANSE?.let {
                        return it
                    }
            }
            val db = Room.databaseBuilder(
                application,
                AppDataBase::class.java,
                DATABASE_NAME
            ).build()
            INSTANSE = db
            return db
        }
    }
}