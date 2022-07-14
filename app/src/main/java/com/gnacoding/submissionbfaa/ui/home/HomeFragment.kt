package com.gnacoding.submissionbfaa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gnacoding.submissionbfaa.R
import com.gnacoding.submissionbfaa.utils.Result
import com.gnacoding.submissionbfaa.data.model.UserEntity
import com.gnacoding.submissionbfaa.databinding.FragmentHomeBinding
import com.gnacoding.submissionbfaa.utils.ViewStateCallback

class HomeFragment : Fragment(), ViewStateCallback<List<UserEntity>> {

    private lateinit var homeAdapter: HomeAdapter
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

        homeAdapter = HomeAdapter()

        homeBinding.rvHome.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = homeAdapter
        }

        searchUser()
    }

    private fun searchUser() {
        homeBinding.search.apply {
            queryHint = resources.getString(R.string.find_username)
            setOnQueryTextListener(object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    userQuery = query.toString()
                    homeViewModel.searchUser(userQuery).observe(viewLifecycleOwner) {
                        when (it) {
                            is Result.Error -> onFailed(it.message)
                            is Result.Loading -> onLoading()
                            is Result.Success -> it.data?.let { it1 -> onSuccess(it1) }
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

    override fun onSuccess(data: List<UserEntity>) {
        homeAdapter.setData(data)
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