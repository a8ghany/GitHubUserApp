package com.gnacoding.submissionbfaa.presentation.screen.detail.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gnacoding.submissionbfaa.domain.utils.NetworkResult
import com.gnacoding.submissionbfaa.domain.model.User
import com.gnacoding.submissionbfaa.databinding.FragmentFollowersBinding
import com.gnacoding.submissionbfaa.presentation.adapter.UserAdapter
import com.gnacoding.submissionbfaa.domain.utils.Constants.ARG_USERNAME
import com.gnacoding.submissionbfaa.domain.utils.ViewStateCallback

class FollowersFragment : Fragment(), ViewStateCallback<List<User>> {

    private lateinit var followersAdapter: UserAdapter

    private var _followersBinding: FragmentFollowersBinding? = null
    private val followersBinding get() = _followersBinding!!
    private val followersViewModel by viewModels<FollowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _followersBinding = FragmentFollowersBinding.inflate(layoutInflater, container, false)
        return followersBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME).toString()
        followersAdapter = UserAdapter()

        followersViewModel.getFollowers(username).observe(viewLifecycleOwner) { listUser ->
            when (listUser) {
                is NetworkResult.Success -> listUser.data?.let { onSuccess(it) }
                is NetworkResult.Error -> onFailed(listUser.message)
                is NetworkResult.Loading -> onLoading()
            }
        }

        followersBinding.rvFollowers.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = followersAdapter
        }
    }

    override fun onSuccess(data: List<User>) {
        followersAdapter.setData(data)
        followersBinding.progressBarFollowers.visibility = View.GONE
        followersBinding.ivEmpty.visibility = View.GONE
        followersBinding.rvFollowers.visibility = View.VISIBLE
    }

    override fun onLoading() {
        followersBinding.progressBarFollowers.visibility = View.VISIBLE
        followersBinding.ivEmpty.visibility = View.GONE
        followersBinding.rvFollowers.visibility = View.GONE
    }

    override fun onFailed(message: String?) {
        followersBinding.progressBarFollowers.visibility = View.GONE
        followersBinding.rvFollowers.visibility = View.GONE
        followersBinding.ivEmpty.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance(username: String) =
            FollowersFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                }
            }
    }
}