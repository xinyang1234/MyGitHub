package com.example.mygithub.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mygithub.database.FavoriteUser
import com.example.mygithub.database.FavoriteUserRepository
import kotlinx.coroutines.launch

class FavoriteViewModel (private val newsRepository: FavoriteUserRepository) : ViewModel() {
    fun getListUserFavorite() = newsRepository.getListUserFavorite()

    fun findUserName(username: String) = newsRepository.findUserName(username)
    fun deleteFavoriteUser(news: FavoriteUser) {
        viewModelScope.launch{
            newsRepository.deleteUserFavorite(news)
        }
    }
    fun saveFavoriteUser(news: FavoriteUser) {
        viewModelScope.launch{
            newsRepository.saveFavoriteUser(news)
        }
    }
}