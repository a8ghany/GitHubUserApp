package com.gnacoding.submissionbfaa.presentation.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gnacoding.submissionbfaa.databinding.ItemUserBinding
import com.gnacoding.submissionbfaa.domain.model.User
import com.gnacoding.submissionbfaa.domain.utils.UserDiffCallback
import com.gnacoding.submissionbfaa.presentation.screen.detail.DetailActivity
import com.gnacoding.submissionbfaa.presentation.screen.detail.DetailActivity.Companion.EXTRA_DATA

class UserAdapter : RecyclerView.Adapter<UserAdapter.HomeViewHolder>() {

    private val listUser = ArrayList<User>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HomeViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(listUser[position])
    }

    override fun getItemCount(): Int = listUser.size

    inner class HomeViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.apply {
                Glide.with(itemView.context)
                    .load(user.avatar_url)
                    .apply(RequestOptions().override(250, 250))
                    .into(ivAvatar)
                tvUsername.text = user.login
                itemView.setOnClickListener {
                    val mIntent = Intent(itemView.context, DetailActivity::class.java)
                    mIntent.putExtra(EXTRA_DATA, user.login)
                    it.context.startActivity(mIntent)
                }
            }
        }
    }

    fun setData(newListUser: List<User>) {
        val diffCallback = UserDiffCallback(listUser, newListUser)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        listUser.clear()
        listUser.addAll(newListUser)
        diffResult.dispatchUpdatesTo(this)
    }
}