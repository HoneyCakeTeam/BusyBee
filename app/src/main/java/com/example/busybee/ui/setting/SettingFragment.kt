package com.example.busybee.ui.setting

import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentSettingsBinding
import com.example.busybee.ui.login.view.LoginFragment
import com.example.busybee.utils.SharedPreferencesUtils
import com.example.busybee.utils.replaceFragment

class SettingFragment : BaseFragment<FragmentSettingsBinding>() {
    override val TAG: String
        get() = this::class.simpleName.toString()
    private val loginFragment = LoginFragment()

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
            replaceFragment(loginFragment)
        }
    }
}