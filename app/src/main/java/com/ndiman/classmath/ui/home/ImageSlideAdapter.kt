package com.ndiman.classmath.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ndiman.classmath.data.pref.ImageItem
import com.ndiman.classmath.databinding.ItemImageSliderBinding

class ImageSlideAdapter: ListAdapter<ImageItem,ImageSlideAdapter.ViewHolder>(DiffCallback()) {

    class ViewHolder(private val binding: ItemImageSliderBinding): RecyclerView.ViewHolder(binding.root) {
        private fun ImageView.loadImage(image: String){
            Glide.with(this.context)
                .load(image)
                .into(this)
        }

        fun bind(image: ImageItem){
            binding.imageView.loadImage(image.url)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemImageSliderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val imageItem = getItem(position)
        holder.bind(imageItem)
    }

    class DiffCallback: DiffUtil.ItemCallback<ImageItem>(){
        override fun areItemsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: ImageItem, newItem: ImageItem): Boolean {
            return oldItem == newItem
        }

    }
}