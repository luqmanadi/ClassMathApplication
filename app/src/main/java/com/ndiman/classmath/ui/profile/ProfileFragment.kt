package com.ndiman.classmath.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.ndiman.classmath.R
import com.ndiman.classmath.databinding.FragmentProfileBinding
import com.ndiman.classmath.ui.ViewModelFactory
import com.ndiman.classmath.ui.onboarding.OnBoardingActivity

class ProfileFragment : Fragment() {

    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    private var binding: FragmentProfileBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setUpAction()
        getTheme()
    }


    private fun setUpAction(){
        binding?.btnLogout?.setOnClickListener {
            binding?.btnLogout?.setOnClickListener {
                MaterialAlertDialogBuilder(requireActivity())
                    .setTitle(resources.getString(R.string.logout))
                    .setMessage(resources.getString(R.string.logout_description))
                    .setPositiveButton(resources.getString(R.string.yes)){ _, _ ->
                        viewModel.logout()
                        Toast.makeText(requireContext(), "Berhasil Logout", Toast.LENGTH_SHORT).show()
                        val intent = Intent(requireActivity(), OnBoardingActivity::class.java)
                        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
                        startActivity(intent)

                    }
                    .setNegativeButton(resources.getString(R.string.no)){ _, _ ->
                    }
                    .create()
                    .show()

            }
        }

        binding?.switchTheme?.setOnCheckedChangeListener { _, isChecked: Boolean ->
            viewModel.saveThemeSetting(isChecked)
        }
    }

    private fun getTheme(){
        viewModel.getThemeSetting().observe(requireActivity()){isDarkModeActive: Boolean ->
            if (isDarkModeActive){
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                binding?.switchTheme?.isChecked = true
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                binding?.switchTheme?.isChecked = false
            }
        }
    }

    private fun showLoading(isLoading: Boolean){binding?.progressBar?.visibility = if (isLoading) View.VISIBLE else View.GONE}

    override fun onDestroyView() {
        super.onDestroyView()
        binding = null
    }
}