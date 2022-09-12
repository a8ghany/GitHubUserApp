package com.gnacoding.submissionbfaa.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.gnacoding.submissionbfaa.R
import com.gnacoding.submissionbfaa.utils.Result
import com.gnacoding.submissionbfaa.data.model.UserEntity
import com.gnacoding.submissionbfaa.databinding.ActivityDetailBinding
import com.gnacoding.submissionbfaa.adapter.SectionsPagerAdapter
import com.gnacoding.submissionbfaa.utils.ViewStateCallback
import com.gnacoding.submissionbfaa.viewmodel.DetailViewModel
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity(), ViewStateCallback<UserEntity?> {

    private var _detailBinding: ActivityDetailBinding? = null
    private val detailBinding get() = _detailBinding!!
    private val detailViewModel by viewModels<DetailViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _detailBinding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(detailBinding.root)

        val username = intent.getStringExtra(EXTRA_DATA)

        CoroutineScope(Dispatchers.Main).launch {
            detailViewModel.getDetailUser(username.toString()).observe(this@DetailActivity) {
                when (it) {
                    is Result.Error -> onFailed(it.message)
                    is Result.Loading -> onLoading()
                    is Result.Success -> onSuccess(it.data)
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

        detailBinding.backButton.setOnClickListener { onBackPressed() }

        detailBinding.btnShare.setOnClickListener {
            val share = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, "Look at his profile: ${detailBinding.tvUsername.text}")
            }
            startActivity(Intent.createChooser(share, "Share via"))
        }
    }

    override fun onSuccess(data: UserEntity?) {
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
                btnFavorite.setImageResource(R.drawable.ic_favorite)
            } else {
                btnFavorite.setImageResource(R.drawable.ic_favorite_outlined)
            }

            btnFavorite.setOnClickListener {
                if (data?.isFavorite == true) {
                    data.isFavorite = false
                    detailViewModel.deleteFavoriteUser(data)
                    btnFavorite.setImageResource(R.drawable.ic_favorite_outlined)
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
                    btnFavorite.setImageResource(R.drawable.ic_favorite)
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
        detailBinding.btnShare.visibility = View.INVISIBLE
        detailBinding.btnFavorite.visibility = View.INVISIBLE
    }

    override fun onFailed(message: String?) {
        detailBinding.btnShare.visibility = View.INVISIBLE
        detailBinding.btnFavorite.visibility = View.INVISIBLE
    }

    companion object {
        const val EXTRA_DATA = "extra_data"
        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}