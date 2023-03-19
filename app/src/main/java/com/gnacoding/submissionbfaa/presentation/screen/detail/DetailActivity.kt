package com.gnacoding.submissionbfaa.presentation.screen.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.gnacoding.submissionbfaa.R
import com.gnacoding.submissionbfaa.databinding.ActivityDetailBinding
import com.gnacoding.submissionbfaa.domain.model.User
import com.gnacoding.submissionbfaa.domain.utils.NetworkResult
import com.gnacoding.submissionbfaa.domain.utils.ViewStateCallback
import com.gnacoding.submissionbfaa.presentation.adapter.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity(), ViewStateCallback<User?> {

    private lateinit var detailBinding: ActivityDetailBinding
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val username = intent.getStringExtra(EXTRA_DATA)

        lifecycleScope.launch {
            detailViewModel.getDetailUser(username.toString())
                .observe(this@DetailActivity) { user ->
                    when (user) {
                        is NetworkResult.Success -> onSuccess(user.data)
                        is NetworkResult.Error -> onFailed(user.message)
                        is NetworkResult.Loading -> onLoading()
                    }
                }
        }

        val sectionsPagerAdapter = SectionsPagerAdapter(this, username.toString())
        detailBinding.apply {
            viewPager.adapter = sectionsPagerAdapter
            TabLayoutMediator(tabs, viewPager) { tabs, position ->
                tabs.text = resources.getString(TAB_TITLES[position])
            }.attach()
        }

        detailBinding.backButton.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

        detailBinding.btnShare.setOnClickListener {
            val share = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Look at his profile: ${detailBinding.tvUsername.text}")
            }
            startActivity(Intent.createChooser(share, "Share via"))
        }
    }

    override fun onSuccess(data: User?) {
        hideShimmerEffect()
        detailBinding.mainLayout.visibility = View.VISIBLE
        detailBinding.apply {
            detailBinding.userToolbarTitle.text = data?.login

            Glide.with(this@DetailActivity)
                .load(data?.avatar_url)
                .into(ivAvatar)
            tvName.text = data?.name
            tvUsername.text = data?.login ?: "-"
            tvLocation.text = data?.location ?: "-"
            tvCompany.text = data?.company ?: "-"
            userRepos.text = data?.public_repos.toString()
            userFollowers.text = data?.followers.toString()
            userFollowing.text = data?.following.toString()

            if (data?.isFavorite == true) {
                btnFavorite.setImageResource(R.drawable.ic_like)
            } else {
                btnFavorite.setImageResource(R.drawable.ic_like_outlined)
            }

            btnFavorite.setOnClickListener {
                if (data?.isFavorite == true) {
                    data.isFavorite = false
                    detailViewModel.deleteFavoriteUser(data)
                    btnFavorite.setImageResource(R.drawable.ic_like_outlined)
                    Toast.makeText(
                        this@DetailActivity,
                        getString(R.string.favorite_deleted),
                        Toast.LENGTH_SHORT
                    ).show()
                } else {
                    data?.isFavorite = true
                    if (data != null) {
                        detailViewModel.insertFavoriteUser(data)
                    }
                    btnFavorite.setImageResource(R.drawable.ic_like)
                    Toast.makeText(
                        this@DetailActivity,
                        getString(R.string.favorite_add),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    override fun onLoading() {
        showShimmerEffect()
        detailBinding.mainLayout.visibility = View.GONE
        detailBinding.btnShare.visibility = View.GONE
        detailBinding.btnFavorite.visibility = View.GONE
    }

    override fun onFailed(message: String?) {
        showShimmerEffect()
        detailBinding.mainLayout.visibility = View.GONE
        detailBinding.btnShare.visibility = View.GONE
        detailBinding.btnFavorite.visibility = View.GONE
    }

    private fun showShimmerEffect() {
        detailBinding.shimmerFrameLayout.startShimmer()
        detailBinding.shimmerFrameLayout.visibility = View.VISIBLE
    }

    private fun hideShimmerEffect() {
        detailBinding.shimmerFrameLayout.stopShimmer()
        detailBinding.shimmerFrameLayout.visibility = View.GONE
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}