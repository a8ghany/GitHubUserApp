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
import com.gnacoding.submissionbfaa.databinding.FragmentFollowingBinding
import com.gnacoding.submissionbfaa.presentation.adapter.UserAdapter
import com.gnacoding.submissionbfaa.domain.utils.Constants.ARG_USERNAME
import com.gnacoding.submissionbfaa.domain.utils.ViewStateCallback

class FollowingFragment : Fragment(), ViewStateCallback<List<User>> {

    private lateinit var followingAdapter: UserAdapter

    private var _followingBinding: FragmentFollowingBinding? = null
    private val followingBinding get() = _followingBinding!!
    private val followingViewModel by viewModels<FollowViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _followingBinding = FragmentFollowingBinding.inflate(layoutInflater, container, false)
        return followingBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val username = arguments?.getString(ARG_USERNAME).toString()
        followingAdapter = UserAdapter()

        followingViewModel.getFollowing(username).observe(viewLifecycleOwner) { listUser ->
            when (listUser) {
                is NetworkResult.Success -> listUser.data?.let { onSuccess(it) }
                is NetworkResult.Error -> onFailed(listUser.message)
                is NetworkResult.Loading -> onLoading()
            }
        }

        followingBinding.rvFollowing.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = followingAdapter
        }
    }

    override fun onSuccess(data: List<User>) {
        followingAdapter.setData(data)
        followingBinding.progressBarFollowing.visibility = View.GONE
        followingBinding.ivEmpty.visibility = View.GONE
        followingBinding.rvFollowing.visibility = View.VISIBLE
    }

    override fun onLoading() {
        followingBinding.progressBarFollowing.visibility = View.VISIBLE
        followingBinding.ivEmpty.visibility = View.GONE
        followingBinding.rvFollowing.visibility = View.GONE
    }

    override fun onFailed(message: String?) {
        followingBinding.progressBarFollowing.visibility = View.GONE
        followingBinding.rvFollowing.visibility = View.GONE
        followingBinding.ivEmpty.visibility = View.VISIBLE
    }

    companion object {
        fun newInstance(username: String) =
            FollowingFragment().apply {
                arguments = Bundle().apply {
                    putString(ARG_USERNAME, username)
                }
            }
    }
}