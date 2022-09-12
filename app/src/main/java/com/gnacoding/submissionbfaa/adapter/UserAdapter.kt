package com.gnacoding.submissionbfaa.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.gnacoding.submissionbfaa.data.model.UserEntity
import com.gnacoding.submissionbfaa.databinding.ItemUserBinding
import com.gnacoding.submissionbfaa.ui.detail.DetailActivity
import com.gnacoding.submissionbfaa.ui.detail.DetailActivity.Companion.EXTRA_DATA

class UserAdapter : RecyclerView.Adapter<UserAdapter.HomeViewHolder>() {

    private val listUser = ArrayList<UserEntity>()

    @SuppressLint("NotifyDataSetChanged")
    fun setData(users: List<UserEntity>) {
        listUser.apply {
            clear()
            addAll(users)
        }
        notifyDataSetChanged()
    }

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
        fun bind(user: UserEntity) {
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
}