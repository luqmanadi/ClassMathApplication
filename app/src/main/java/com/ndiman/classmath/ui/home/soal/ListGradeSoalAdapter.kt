package com.ndiman.classmath.ui.home.soal

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ndiman.classmath.BuildConfig
import com.ndiman.classmath.data.remote.response.DataItemGrade
import com.ndiman.classmath.databinding.ItemListRiwayatFavoritBinding

class ListGradeSoalAdapter: ListAdapter<DataItemGrade, ListGradeSoalAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemListRiwayatFavoritBinding): RecyclerView.ViewHolder(binding.root) {
        private fun ImageView.loadImage(photoUrl: String?) {
            Glide.with(this.context)
                .load(uri +photoUrl)
                .into(this)
        }

        fun bind(listGrade: DataItemGrade) {
            binding.apply {
                imgUser.loadImage(listGrade.gradeImage)
                tvUser.text = listGrade.name
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemListRiwayatFavoritBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Memilih Materi ${list.name}", Toast.LENGTH_SHORT).show()
        }
    }

    companion object{
        private const val uri = BuildConfig.BASE_URI_PHOTO
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<DataItemGrade>() {
            override fun areItemsTheSame(oldItem: DataItemGrade, newItem: DataItemGrade): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: DataItemGrade,
                newItem: DataItemGrade
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}