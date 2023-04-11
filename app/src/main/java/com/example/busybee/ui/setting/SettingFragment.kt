package com.example.busybee.ui.setting

import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentSettingsBinding

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
        TODO("Not yet implemented")
    }
}