package com.example.mygithub.database

import android.content.Context

object Injection {
    fun provideUserFavoriteRepository(context: Context): FavoriteUserRepository {
        val database = FavoriteRoomDatabase.getInstanceFavoriteRoomDatabase(context)
        val dao = database.favoriteDao()
        return FavoriteUserRepository.getInstance(dao)
    }
}