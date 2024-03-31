package com.example.mygithub.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.mygithub.database.FavoriteUser


@Database(entities = [FavoriteUser::class], version = 1, exportSchema = false)
abstract class FavoriteRoomDatabase : RoomDatabase(){
    abstract fun favoriteDao(): FavoriteDao

    companion object {
        @Volatile
        private var instance: FavoriteRoomDatabase? = null
        fun getInstanceFavoriteRoomDatabase (context: Context): FavoriteRoomDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteRoomDatabase::class.java,
                    "FavoriteRoomDatabase.db"
                ).build()
            }

    }
}