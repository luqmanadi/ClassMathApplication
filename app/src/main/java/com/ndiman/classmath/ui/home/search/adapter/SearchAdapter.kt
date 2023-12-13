package com.ndiman.classmath.ui.home.search.adapter

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
import com.ndiman.classmath.data.pref.FilePdf
import com.ndiman.classmath.data.remote.response.TutorialItem
import com.ndiman.classmath.databinding.ItemListRiwayatFavoritBinding
import com.ndiman.classmath.ui.home.materi.PdfViewerActivity

class SearchAdapter: ListAdapter<TutorialItem, SearchAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemListRiwayatFavoritBinding): RecyclerView.ViewHolder(binding.root) {
        private fun ImageView.loadImage(photoUrl: String?) {
            Glide.with(this.context)
                .load(uri +photoUrl)
                .into(this)
        }

        fun bind(listTutorial: TutorialItem) {
            binding.apply {
                imgUser.loadImage(listTutorial.tutorialImage)
                tvUser.text = listTutorial.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListRiwayatFavoritBinding.inflate(LayoutInflater.from(parent.context), parent,false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)
        val setDataFile = FilePdf(
            list.id!!,
            list.grade!!.id!!,
            list.grade.name!!,
            list.title!!,
            list.tutorialImage!!,
            list.tutorialFile!!
        )

        Log.d("Search Adapter", "Cek : $setDataFile")
        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Memilih Materi ${list.title}", Toast.LENGTH_SHORT).show()
            val intent = Intent(holder.itemView.context, PdfViewerActivity::class.java)
            intent.putExtra(PdfViewerActivity.EXTRA_ID_PDF, setDataFile)
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object{
        private const val uri = BuildConfig.BASE_URI_PHOTO
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<TutorialItem>(){
            override fun areItemsTheSame(oldItem: TutorialItem, newItem: TutorialItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: TutorialItem, newItem: TutorialItem): Boolean {
                return oldItem == newItem
            }

        }
    }
}