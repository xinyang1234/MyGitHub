package com.example.mygithub.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mygithub.adapter.UserAdapter
import com.example.mygithub.databinding.FragmentHomeBinding
import com.example.mygithub.viewModel.FollowersViewModel
import com.example.mygithub.viewModel.FollowingViewModel

class HomeFragment : Fragment() {
    private var position: Int? = null
    private var _binding: FragmentHomeBinding? = null
    private var username: String? = null
    private val binding get() = _binding
    private val followersViewModel by viewModels<FollowersViewModel>()
    private val followingViewModel by viewModels<FollowingViewModel>()

    companion object {
        const val ARG_POSITION = "arg_position"
        const val ARG_USERNAME = "arg_username"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            position = it.getInt(ARG_POSITION)
            username = it.getString(ARG_USERNAME)
        }
        val adapter = UserAdapter()

        setupRecyclerView()

        if (position == 1) {
            showFollowersData(adapter)
        } else {
            showFollowingData(adapter)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        binding?.rvDataFollow?.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(requireActivity(), layoutManager.orientation)
        binding?.rvDataFollow?.addItemDecoration(itemDecoration)
    }

    private fun showFollowersData(adapter: UserAdapter) {
        with(followersViewModel) {
            if (user.value == null) {
                findFollowers(username!!)
                user.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    binding?.rvDataFollow?.adapter = adapter
                }
            }
            user.observe(viewLifecycleOwner) {
                adapter.submitList(it)
                binding?.rvDataFollow?.adapter = adapter
            }
            isloading.observe(viewLifecycleOwner) {
                showLoader(it)
            }
        }
    }

    private fun showFollowingData(adapter: UserAdapter) {
        with(followingViewModel) {
            if (user.value == null) {
                findFollowing(username!!)
                user.observe(viewLifecycleOwner) {
                    adapter.submitList(it)
                    binding?.rvDataFollow?.adapter = adapter
                }
            }
            user.observe(viewLifecycleOwner) {
                adapter.submitList(it)
                binding?.rvDataFollow?.adapter = adapter
            }
            isloading.observe(viewLifecycleOwner) {
                showLoader(it)
            }
        }
    }

    private fun showLoader(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressLoading?.visibility = View.VISIBLE
            binding?.rvDataFollow?.visibility = View.INVISIBLE
        } else {
            binding?.progressLoading?.visibility = View.GONE
            binding?.rvDataFollow?.visibility = View.VISIBLE
        }
    }
}