package com.ndiman.classmath.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.ndiman.classmath.data.local.entity.HistoriMateri
import com.ndiman.classmath.databinding.FragmentHistoryBinding
import com.ndiman.classmath.ui.ViewModelFactory

class HistoryFragment : Fragment() {

    private val viewModel by viewModels<HistoryViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private var _binding: FragmentHistoryBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getAllHistory()
    }


    private fun getAllHistory(){
        viewModel.getAllHistoryStudy().observe(viewLifecycleOwner){
            showLoading(true)
            if (it.isNotEmpty()){
                showLoading(false)
                setListHistory(it)
            }else{
                showLoading(false)
                showListNull(true)
            }
        }
    }

    private fun setListHistory(listHistory: List<HistoriMateri>){
        if (!isAdded) {
            return
        }
        val layoutmanager = LinearLayoutManager(requireContext())
        binding.rvHistory.layoutManager = layoutmanager
        val itemDecoration = DividerItemDecoration(requireContext(), layoutmanager.orientation)
        binding.rvHistory.addItemDecoration(itemDecoration)
        binding.rvHistory.setHasFixedSize(true)
        val adapter = HistoryAdapter()
        binding.rvHistory.adapter = adapter
        adapter.submitList(listHistory)
    }


    private fun showListNull(state: Boolean) { binding.tvListEmpty.visibility = if (state) View.VISIBLE else View.GONE }

    private fun showLoading(state: Boolean) {
        _binding?.let {
        it.progressBar.visibility = if (state) View.VISIBLE else View.GONE
    } }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}