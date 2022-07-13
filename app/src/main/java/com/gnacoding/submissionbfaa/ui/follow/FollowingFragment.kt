package com.gnacoding.submissionbfaa.ui.follow

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gnacoding.submissionbfaa.data.Result
import com.gnacoding.submissionbfaa.data.model.UserEntity
import com.gnacoding.submissionbfaa.databinding.FragmentFollowingBinding
import com.gnacoding.submissionbfaa.ui.home.HomeAdapter
import com.gnacoding.submissionbfaa.utils.Constants.ARG_USERNAME
import com.gnacoding.submissionbfaa.utils.ViewStateCallback

class FollowingFragment : Fragment(), ViewStateCallback<List<UserEntity>> {

    private lateinit var followingAdapter: HomeAdapter

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
        followingAdapter = HomeAdapter()

        followingViewModel.getFollowing(username).observe(viewLifecycleOwner) {
            when (it) {
                is Result.Error -> onFailed(it.message)
                is Result.Loading -> onLoading()
                is Result.Success -> it.data?.let { it1 -> onSuccess(it1) }
            }
        }

        followingBinding.rvFollowing.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = followingAdapter
        }
    }

    override fun onSuccess(data: List<UserEntity>) {
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