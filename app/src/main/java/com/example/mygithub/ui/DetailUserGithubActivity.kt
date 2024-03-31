package com.example.mygithub.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mygithub.R
import com.example.mygithub.data.response.DetailResponse
import com.example.mygithub.database.FavoriteUser
import com.example.mygithub.databinding.ActivityDetailUserGithubBinding
import com.example.mygithub.viewModel.DetailViewModel
import com.example.mygithub.viewModel.FavoriteViewModel
import com.example.mygithub.viewModel.FavoriteViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserGithubActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserGithubBinding

    companion object  {
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }


    private val detailUserViewModel by viewModels<DetailViewModel>()
    private var userDetailResponse = DetailResponse()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailUserGithubBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val userFavoriteViewModelFactory = FavoriteViewModelFactory.getInstance(this)
        val userFavoriteViewModel: FavoriteViewModel by viewModels {
            userFavoriteViewModelFactory
        }
        var isUserFavoriteNotEmpty = false

        val username = intent.extras?.getString("username")
        val sectionsPagerAdapter = SectionsPagerAdapter(this, username!!)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = findViewById(R.id.tabs)
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()

        supportActionBar?.elevation = 0f

        if (detailUserViewModel.user.value == null){
            detailUserViewModel.getDataUserDetail(username!!)
        }

        detailUserViewModel.user.observe(this){
            setDataDetail(it!!)
            userDetailResponse = it
        }

        detailUserViewModel.isloading.observe(this){
            loading(it)
        }

        findViewById<ImageView>(R.id.arrowback).setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        userFavoriteViewModel.findUserName(username)
            .observe(this) { favoriteUser ->
                isUserFavoriteNotEmpty = favoriteUser != null
                if (isUserFavoriteNotEmpty) {
                    binding.FavButton.setImageResource(R.drawable.baseline_favorite_24)
                } else {
                    binding.FavButton.setImageResource(R.drawable.baseline_favorite_border_24)
                }
            }

        binding.FavButton.setOnClickListener {
            val favoriteUser = FavoriteUser(
                username = userDetailResponse.login ?: "-",
                avatarUrl = userDetailResponse.avatarUrl
            )
            if (isUserFavoriteNotEmpty) {
                userFavoriteViewModel.deleteFavoriteUser(favoriteUser)
            } else {
                userFavoriteViewModel.saveFavoriteUser(favoriteUser)
            }
        }
    }

    fun setDataDetail (data: DetailResponse){
        val detailUsername = "@${data.login ?: "username"}"
        val detailFullname = "${data.name ?: "Full Name"}"
        val detailFollowers = "${data.followers} Followers"
        val detailFollowing = "${data.following} Following"
        Glide.with(binding.root)
            .load(data.avatarUrl)
            .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
            .into(binding.imageDetail)
        binding.UserName.text = detailUsername
        binding.FullName.text = detailFullname
        binding.myFollowers.text = detailFollowers
        binding.myFollowing.text = detailFollowing

    }


    private fun loading (isLoading : Boolean){
        with(binding) {
            if (isLoading) {
                binding.progressLoader.visibility = View.VISIBLE
            } else {
                binding.progressLoader.visibility = View.GONE
            }
        }
    }
}