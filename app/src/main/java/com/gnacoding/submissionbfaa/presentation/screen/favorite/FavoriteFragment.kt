package com.gnacoding.submissionbfaa.presentation.screen.favorite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.gnacoding.submissionbfaa.domain.utils.NetworkResult
import com.gnacoding.submissionbfaa.domain.model.User
import com.gnacoding.submissionbfaa.databinding.FragmentFavoriteBinding
import com.gnacoding.submissionbfaa.presentation.adapter.UserAdapter
import com.gnacoding.submissionbfaa.domain.utils.ViewStateCallback
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FavoriteFragment : Fragment(), ViewStateCallback<List<User>> {

    private lateinit var favoriteAdapter: UserAdapter

    private var _favoriteBinding: FragmentFavoriteBinding? = null
    private val favoriteBinding get() = _favoriteBinding!!
    private val favoriteViewModel by viewModels<FavoriteViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _favoriteBinding = FragmentFavoriteBinding.inflate(layoutInflater, container, false)
        return favoriteBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favoriteAdapter = UserAdapter()

        favoriteBinding.rvFavorite.apply {
            layoutManager = LinearLayoutManager(context)
            setHasFixedSize(true)
            adapter = favoriteAdapter
        }

        observeFavorite()
    }

    private fun observeFavorite() {
        CoroutineScope(Dispatchers.Main).launch {
            favoriteViewModel.getFavoriteList().observe(viewLifecycleOwner) { listUser ->
                when (listUser) {
                    is NetworkResult.Success -> listUser.data?.let { onSuccess(it) }
                    is NetworkResult.Error -> onFailed(listUser.message)
                    is NetworkResult.Loading -> onLoading()
                }
            }
        }
    }

    override fun onSuccess(data: List<User>) {
        favoriteAdapter.setData(data)
        favoriteBinding.progressBarFavorite.visibility = View.GONE
        favoriteBinding.emptyLayout.favoriteEmpty.visibility = View.GONE
        favoriteBinding.rvFavorite.visibility = View.VISIBLE
    }

    override fun onLoading() {
        favoriteBinding.emptyLayout.favoriteEmpty.visibility = View.GONE
        favoriteBinding.rvFavorite.visibility = View.GONE
        favoriteBinding.progressBarFavorite.visibility = View.VISIBLE
    }

    override fun onFailed(message: String?) {
        favoriteBinding.progressBarFavorite.visibility = View.GONE
        favoriteBinding.rvFavorite.visibility = View.GONE
        favoriteBinding.emptyLayout.favoriteEmpty.visibility = View.VISIBLE
    }

    override fun onResume() {
        super.onResume()
        observeFavorite()
    }

    override fun onDestroy() {
        super.onDestroy()
        _favoriteBinding = null
    }
}