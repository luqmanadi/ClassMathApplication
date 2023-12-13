package com.ndiman.classmath.ui.home.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ndiman.classmath.data.remote.response.DataItemLeaderboard
import com.ndiman.classmath.databinding.ItemLeaderbordBinding


class LeaderboardAdapter : ListAdapter<DataItemLeaderboard, LeaderboardAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemLeaderbordBinding): RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n")
        fun bind(listLeaderboard: DataItemLeaderboard) {
            binding.apply {
                tvUser.text = listLeaderboard.name
                tvPoint.text = "${listLeaderboard.totalScore.toString()} points"


            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemLeaderbordBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)
    }

    companion object{
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemLeaderboard>(){
            override fun areItemsTheSame(
                oldItem: DataItemLeaderboard,
                newItem: DataItemLeaderboard
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemLeaderboard,
                newItem: DataItemLeaderboard
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}