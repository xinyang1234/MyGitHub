package com.example.mygithub.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithub.R
import com.example.mygithub.adapter.UserAdapter
import com.example.mygithub.data.response.ItemsItem
import com.example.mygithub.databinding.ActivityMainBinding
import com.example.mygithub.viewModel.MainViewModel

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val mainViewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        seUpRecyclerView()

        mainViewModel.user.observe(this) { listUserData ->
            if (listUserData != null) {
                setListUserData(listUserData)
            }
        }

        mainViewModel.isloading.observe(this){
            showLoading(it)
        }
        searchUserBar()
    }

    private fun seUpRecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

        val adapter = UserAdapter()
        binding.rvUsers.adapter = adapter
    }

    private fun setListUserData(users: List<ItemsItem>) {
        val adapter = UserAdapter()
        adapter.submitList(users)
        binding.rvUsers.adapter = adapter
    }

    private fun searchUserBar(){
        with(binding) {
            searchView.setupWithSearchBar(searchBar)
            searchView
                .editText
                .setOnEditorActionListener { _, _, _ ->
                    val username =
                        if (searchView.text.isEmpty()) "a" else searchView.text.toString()
                    searchBar.setText(searchView.text)
                    searchView.hide()
                    mainViewModel.findUsers(username)
                    false
                }
            searchBar.inflateMenu(R.menu.option_menu)
            searchBar.setOnMenuItemClickListener { menuItem ->
                when (menuItem.itemId){
                    R.id.menu1 -> {
                        val intent = Intent(this@MainActivity, FavoriteActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    R.id.menu2 -> {
                        val intent = Intent(this@MainActivity, ThemesActivity::class.java)
                        startActivity(intent)
                        true
                    }
                    else -> false
                }
            }
        }
    }

    private fun showLoading(isLoading: Boolean) {
        with(binding) {
            if (isLoading) {
                binding.progressBar.visibility = View.VISIBLE
                rvUsers.visibility = View.INVISIBLE
            } else {
                binding.progressBar.visibility = View.GONE
                rvUsers.visibility = View.VISIBLE
            }
        }
    }
}