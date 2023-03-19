package com.gnacoding.submissionbfaa.presentation.screen.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gnacoding.submissionbfaa.R
import com.gnacoding.submissionbfaa.databinding.FragmentHomeBinding
import com.gnacoding.submissionbfaa.domain.model.User
import com.gnacoding.submissionbfaa.domain.utils.NetworkResult
import com.gnacoding.submissionbfaa.domain.utils.ViewStateCallback
import com.gnacoding.submissionbfaa.presentation.adapter.UserAdapter

class HomeFragment : Fragment(), ViewStateCallback<List<User>> {

    private lateinit var userAdapter: UserAdapter
    private lateinit var userQuery: String

    private var _homeBinding: FragmentHomeBinding? = null
    private val homeBinding get() = _homeBinding!!
    private val homeViewModel by viewModels<HomeViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _homeBinding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return homeBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        userAdapter = UserAdapter()
        homeBinding.rvHome.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = userAdapter
        }

        searchUser()
    }

    private fun searchUser() {
        homeBinding.search.apply {
            queryHint = resources.getString(R.string.find_username)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    userQuery = query.toString()
                    homeViewModel.searchUser(userQuery).observe(viewLifecycleOwner) { listUser ->
                        when (listUser) {
                            is NetworkResult.Success -> listUser.data?.let { onSuccess(it) }
                            is NetworkResult.Error -> onFailed(listUser.message)
                            is NetworkResult.Loading -> onLoading()
                        }
                    }
                    clearFocus()
                    return true
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
        }
    }

    override fun onSuccess(data: List<User>) {
        userAdapter.setData(data)
        homeBinding.homeProgressBar.visibility = View.GONE
        homeBinding.notFoundLayout.homeNotFound.visibility = View.GONE
        homeBinding.waitingSearchLayout.searchEmpty.visibility = View.GONE
        homeBinding.rvHome.visibility = View.VISIBLE
    }

    override fun onLoading() {
        homeBinding.homeProgressBar.visibility = View.VISIBLE
        homeBinding.notFoundLayout.homeNotFound.visibility = View.GONE
        homeBinding.waitingSearchLayout.searchEmpty.visibility = View.GONE
        homeBinding.rvHome.visibility = View.GONE
    }

    override fun onFailed(message: String?) {
        if (message == null) {
            homeBinding.waitingSearchLayout.searchEmpty.visibility = View.GONE
            homeBinding.notFoundLayout.homeNotFound.visibility = View.VISIBLE
        } else {
            homeBinding.notFoundLayout.homeNotFound.visibility = View.GONE
            homeBinding.waitingSearchLayout.searchEmpty.visibility = View.VISIBLE
        }
        homeBinding.homeProgressBar.visibility = View.GONE
        homeBinding.rvHome.visibility = View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _homeBinding = null
    }
}