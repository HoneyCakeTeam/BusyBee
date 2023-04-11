package com.example.busybee.ui.setting

import android.widget.Toast
import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentSettingsBinding
import com.example.busybee.utils.SharedPreferencesUtils

class SettingFragment : BaseFragment<FragmentSettingsBinding>() {
    override val TAG: String
        get() = this::class.simpleName.toString()

    override fun getViewBinding(): FragmentSettingsBinding =
        FragmentSettingsBinding.inflate(layoutInflater)


    override fun setUp() {
        setUpPieChart()
        addCallBacks()
    }

    private fun setUpPieChart() {
        TODO("Not yet implemented")
    }

    private fun addCallBacks() {
        onClickLogout()
    }

    private fun onClickLogout() {
        binding.viewLogoutSettings.setOnClickListener {
            SharedPreferencesUtils.token = null
            Toast.makeText(
                requireContext(),
                "User logged out successfully!",
                Toast.LENGTH_SHORT
            )
        }
    }
}