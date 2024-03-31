package com.example.mygithub.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithub.R
import com.example.mygithub.adapter.UserAdapter
import com.example.mygithub.data.response.ItemsItem
import com.example.mygithub.databinding.ActivityFavoriteBinding
import com.example.mygithub.viewModel.FavoriteViewModel
import com.example.mygithub.viewModel.FavoriteViewModelFactory

class FavoriteActivity : AppCompatActivity() {
    private lateinit var _Binding: ActivityFavoriteBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _Binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(_Binding.root)

        findViewById<ImageView>(R.id.arrowback).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        val userFavoriteViewModelFactory = FavoriteViewModelFactory.getInstance(this)
        val userFavoriteViewModel: FavoriteViewModel by viewModels { userFavoriteViewModelFactory }

        setupRecyclerView()

        userFavoriteViewModel.getListUserFavorite().observe(this) { users ->
            val items = arrayListOf<ItemsItem>()
            users.map { user ->
                val item = ItemsItem(login = user.username, avatarUrl = user.avatarUrl)
                items.add(item)
            }
            val adapter = UserAdapter()
            adapter.submitList(items)
            _Binding.rvDataFavorite.adapter = adapter
        }
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        _Binding.rvDataFavorite.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        _Binding.rvDataFavorite.addItemDecoration(itemDecoration)
    }
}