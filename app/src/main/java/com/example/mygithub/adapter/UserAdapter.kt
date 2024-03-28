package com.example.mygithub.adapter

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.example.mygithub.data.response.ItemsItem
import com.example.mygithub.databinding.ItemReviewBinding
import com.example.mygithub.ui.DetailUserGithubActivity

class UserAdapter :  ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(DIFF_CALLBACK){
    class MyViewHolder (private val binding: ItemReviewBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: ItemsItem) {
            val bundle = Bundle()
            bundle.putString("username", user.login)

            Glide.with(binding.root)
                .load(user.avatarUrl)
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.ALL))
                .into(binding.photo)
            binding.usernameitemjudul.text = user.login
            binding.root.setOnClickListener{ view ->
                Intent(view.context, DetailUserGithubActivity::class.java).apply {
                    putExtras(bundle)
                    view.context.startActivity(this)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemReviewBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val review = getItem(position)
        holder.bind(review)
    }
    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }
    }

}