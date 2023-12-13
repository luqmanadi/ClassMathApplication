package com.ndiman.classmath.ui.history

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ndiman.classmath.BuildConfig
import com.ndiman.classmath.data.local.entity.HistoriMateri
import com.ndiman.classmath.data.pref.FilePdf
import com.ndiman.classmath.databinding.ItemListFavoritHistoryBinding
import com.ndiman.classmath.ui.home.materi.PdfViewerActivity

class HistoryAdapter: ListAdapter<HistoriMateri, HistoryAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding:ItemListFavoritHistoryBinding): RecyclerView.ViewHolder(binding.root) {
        private fun ImageView.loadImage(photoUrl: String?){
            Glide.with(this.context)
                .load(uri+photoUrl)
                .into(this)
        }
        fun bind(listHistory: HistoriMateri){
            binding.apply {
                imgTutorial.loadImage(listHistory.tutorialImage)
                tvKelas.text = listHistory.grade
                tvTitleMateri.text = listHistory.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListFavoritHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = getItem(position)
        val setData = FilePdf(
            list.idTutorial!!,
            list.idGrade!!,
            list.grade!!,
            list.title!!,
            list.tutorialImage!!,
            list.tutorialFile!!
        )

        println("cek data : $setData")

        holder.bind(list)

        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Memilih Materi ${list.title}", Toast.LENGTH_SHORT).show()
            val intent = Intent(holder.itemView.context, PdfViewerActivity::class.java)
            intent.putExtra(PdfViewerActivity.EXTRA_ID_PDF, setData)
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object{
        private const val uri = BuildConfig.BASE_URI_PHOTO
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<HistoriMateri>(){
            override fun areItemsTheSame(oldItem: HistoriMateri, newItem: HistoriMateri): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: HistoriMateri,
                newItem: HistoriMateri
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}