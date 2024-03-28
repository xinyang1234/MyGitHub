package com.example.mygithub.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mygithub.data.response.DetailResponse
import com.example.mygithub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel: ViewModel() {
    private val _user = MutableLiveData<DetailResponse>()
    val user: LiveData<DetailResponse> = _user

    private val _isloading = MutableLiveData<Boolean>()
    val isloading: LiveData<Boolean> = _isloading

    fun getDataUserDetail(username: String = "iman"){
        _isloading.value = true
        val client = ApiConfig.getApiService().getDetailUserGithub(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isloading.value = false
                if (response.isSuccessful) {
                    _user.value = response.body()
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isloading.value = false
            }

        })
    }
}