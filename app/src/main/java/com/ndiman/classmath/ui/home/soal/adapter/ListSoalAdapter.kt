package com.ndiman.classmath.ui.home.soal.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ndiman.classmath.BuildConfig
import com.ndiman.classmath.data.pref.QuizListModel
import com.ndiman.classmath.databinding.ItemListSoalBinding
import com.ndiman.classmath.ui.home.soal.SoalActivity

class ListSoalAdapter: ListAdapter<QuizListModel, ListSoalAdapter.MyViewHolder>(DIFF_CALLBACK) {
    class MyViewHolder(private val binding: ItemListSoalBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(listSoal: QuizListModel) {
            binding.apply {
                tvTitle.text = listSoal.title
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemListSoalBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val list = getItem(position)
        holder.bind(list)
        val setUpData = QuizListModel(
            list.idGrade,
            list.idTutorial,
            list.idQuiz,
            list.title
        )


        holder.itemView.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Memilih Soal ${list.title}", Toast.LENGTH_SHORT).show()
            val intent = Intent(holder.itemView.context, SoalActivity::class.java)
            intent.putExtra(SoalActivity.EXTRA_ID_QUIZ, setUpData)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            holder.itemView.context.startActivity(intent)
        }
    }

    companion object{
        private const val uri = BuildConfig.BASE_URI_PHOTO
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<QuizListModel>() {
            override fun areItemsTheSame(oldItem: QuizListModel, newItem: QuizListModel): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(
                oldItem: QuizListModel,
                newItem: QuizListModel
            ): Boolean {
                return oldItem == newItem
            }

        }
    }
}