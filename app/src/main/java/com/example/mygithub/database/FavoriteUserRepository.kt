package com.example.mygithub.database

import androidx.lifecycle.LiveData

class FavoriteUserRepository private constructor(
    private val userFavoriteDao: FavoriteDao,
) {

    fun getListUserFavorite(): LiveData<List<FavoriteUser>> {
        return userFavoriteDao.getListUserFavorite()
    }

    fun findUserName(username: String): LiveData<FavoriteUser> {
        return userFavoriteDao.findUserName(username)
    }

    suspend fun deleteUserFavorite(favoriteUser: FavoriteUser) {
        return userFavoriteDao.deleteFavoriteUser(favoriteUser)
    }

    suspend fun saveFavoriteUser(favoriteUser: FavoriteUser) {
        return userFavoriteDao.saveFavoriteUser(favoriteUser)
    }

    companion object {
        @Volatile
        private var instance: FavoriteUserRepository? = null
        fun getInstance(
            userFavoriteDao: FavoriteDao,
        ): FavoriteUserRepository = instance ?: synchronized(this) {
            instance ?: FavoriteUserRepository(userFavoriteDao).also {
                instance = it
            }
        }
    }
}