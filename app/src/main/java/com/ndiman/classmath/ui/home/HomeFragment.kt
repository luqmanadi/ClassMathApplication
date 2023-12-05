package com.ndiman.classmath.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.ndiman.classmath.data.Result
import com.ndiman.classmath.data.pref.ImageItem
import com.ndiman.classmath.databinding.FragmentHomeBinding
import com.ndiman.classmath.ui.ViewModelFactory
import com.ndiman.classmath.ui.home.adapter.ImageSlideAdapter
import com.ndiman.classmath.ui.home.materi.ListKelasActivity
import com.ndiman.classmath.ui.home.soal.ListKelasSoalActivity
import com.ndiman.classmath.ui.home.viewmodel.HomeViewModel
import com.ndiman.classmath.ui.onboarding.OnBoardingActivity
import java.util.UUID

class HomeFragment : Fragment() {

    private lateinit var viewpager2 : ViewPager2
    private lateinit var pageChangeListener: ViewPager2.OnPageChangeCallback

    private val params = LinearLayout.LayoutParams(
        LinearLayout.LayoutParams.WRAP_CONTENT,
        LinearLayout.LayoutParams.WRAP_CONTENT
    ).apply {
        setMargins(8,0,8,0)
    }

    private val viewModel by viewModels<HomeViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        viewpager2 = binding.viewpager2

        viewModel.getSession().observe(requireActivity() ) { user ->
            user?.let {
                if (!it.isLogin) {
                    val intent = Intent(requireContext(), OnBoardingActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(intent)

                    findNavController().popBackStack()
                }
            }
        }

        setUpSliderImage()
        getDetailUser()
        setUpActionMenu()
    }

    private fun setUpActionMenu(){
        binding.btnMateri.setOnClickListener {
            startActivity(Intent(requireActivity(), ListKelasActivity::class.java))
        }

        binding.btnSoal.setOnClickListener {
            startActivity(Intent(requireActivity(), ListKelasSoalActivity::class.java))
        }
    }

    private fun setUpSliderImage(){
        val packageName = context?.packageName

        val imageList = arrayListOf(
            ImageItem(
                UUID.randomUUID().toString(),
                "android.resource://" + packageName + "/" + com.ndiman.classmath.R.drawable.slider_1
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "android.resource://" + packageName + "/" + com.ndiman.classmath.R.drawable.slider_2
            ),
            ImageItem(
                UUID.randomUUID().toString(),
                "android.resource://" + packageName + "/" + com.ndiman.classmath.R.drawable.slider_3
            )
        )

        val imageAdapter = ImageSlideAdapter()
        viewpager2.adapter = imageAdapter
        imageAdapter.submitList(imageList)

        val slideDotLL = binding.slideDotLL
        val dotsImage = Array(imageList.size){ ImageView(context) }

        dotsImage.forEach {
            it.setImageResource(com.ndiman.classmath.R.drawable.outline_circle_10)
            slideDotLL.addView(it, params)
        }
        dotsImage[0].setImageResource(com.ndiman.classmath.R.drawable.baseline_circle_10)

        pageChangeListener = object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                dotsImage.mapIndexed { index, imageView ->
                    if (position == index){
                        imageView.setImageResource(
                            com.ndiman.classmath.R.drawable.baseline_circle_10
                        )
                    }else{
                        imageView.setImageResource(com.ndiman.classmath.R.drawable.outline_circle_10)
                    }
                }
                super.onPageSelected(position)
            }
        }
        viewpager2.registerOnPageChangeCallback(pageChangeListener)
    }


    private fun getDetailUser(){

        viewModel.getDetailUser().observe(requireActivity()){result ->
            if (result != null){
                when(result){
                    is Result.Loading -> {
                        showLoading(true)
                    }
                    is Result.Success -> {
                        showLoading(false)
                        binding.tvName.text = result.data.data?.name
                    }
                    is Result.Error -> {
                        showLoading(false)
                        Toast.makeText(requireContext(), result.error, Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun showLoading(state: Boolean) { binding.progressBar.visibility = if (state) View.VISIBLE else View.GONE }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}