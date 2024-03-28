package com.example.mygithub.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithub.data.response.GithubResponse
import com.example.mygithub.data.response.ItemsItem
import com.example.mygithub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {
    private val _user = MutableLiveData<List<ItemsItem>?>()
    val user: LiveData<List<ItemsItem>?> = _user

    private val _isloading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = _isloading

    init {
        findUsers()
    }

    fun findUsers(username: String = "ab") {
        _isloading.value = true
        val client = ApiConfig.getApiService().getUserGithub(username)
        client.enqueue(object : Callback<GithubResponse> {
            override fun onResponse(
                call: Call<GithubResponse>,
                response: Response<GithubResponse>
            ) {
                _isloading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()?.items
                }
            }

            override fun onFailure(call: Call<GithubResponse>, t: Throwable) {
                _isloading.value = false
            }
        })
    }
}