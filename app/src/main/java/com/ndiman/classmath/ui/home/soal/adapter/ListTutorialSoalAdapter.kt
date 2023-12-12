package com.ndiman.classmath.ui.home.soal.adapter

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ndiman.classmath.BuildConfig
import com.ndiman.classmath.data.pref.TutorialListModel
import com.ndiman.classmath.databinding.ItemListRiwayatFavoritBinding
import com.ndiman.classmath.ui.home.soal.ListSoalActivity

class ListTutorialSoalAdapter: ListAdapter<TutorialListModel, ListTutorialSoalAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding:ItemListRiwayatFavoritBinding): RecyclerView.ViewHolder(binding.root) {
        private fun ImageView.loadImage(photoUrl: String?) {
            Glide.with(this.context)
                .load(uri +photoUrl)
                .into(this)
        }

        fun bind(listTutorial: TutorialListModel) {
            binding.apply {
                imgUser.loadImage(listTutorial.tutorialImage)
                tvUser.text = listTutorial.titleTutorial
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListRiwayatFavoritBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)
        val setUpData = TutorialListModel(
            list.idGrade,
            list.idTutorial,
            list.tutorialImage,
            list.titleTutorial
        )

        Log.d("Cek Adapter", "Cek Data $setUpData")


        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Memilih Soal Materi ${list.titleTutorial}", Toast.LENGTH_SHORT).show()
            val intent = Intent(holder.itemView.context, ListSoalActivity::class.java)
            intent.putExtra(ListSoalActivity.ID_SOAL, setUpData)
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object{
        private const val uri = BuildConfig.BASE_URI_PHOTO
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TutorialListModel>() {
            override fun areItemsTheSame(
                oldItem: TutorialListModel,
                newItem: TutorialListModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: TutorialListModel,
                newItem: TutorialListModel
            ): Boolean {
                return oldItem == newItem
            }


        }
    }
}