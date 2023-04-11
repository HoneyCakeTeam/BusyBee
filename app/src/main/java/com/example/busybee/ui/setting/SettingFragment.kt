package com.example.busybee.ui.setting

import android.graphics.Color
import android.widget.Toast
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentSettingsBinding
import com.example.busybee.utils.SharedPreferencesUtils
import org.eazegraph.lib.models.PieModel

class SettingFragment : BaseFragment<FragmentSettingsBinding>() {
    override val TAG: String
        get() = this::class.simpleName.toString()

    private var personalTodos = 30.0f
    private var personalInProgressTodos = 30.0f
    private var personalDoneTodos = 40.0f

    override fun getViewBinding(): FragmentSettingsBinding =
        FragmentSettingsBinding.inflate(layoutInflater)


    override fun setUp() {
        setUpPieChart()
        addCallBacks()
    }

    private fun setUpPieChart() {
        binding.piechart.addPieSlice(PieModel(personalTodos,Color.parseColor(R.color.secondary_500.toString())))
        binding.piechart.addPieSlice(PieModel(personalInProgressTodos,Color.parseColor(R.color.primary_500.toString())))
        binding.piechart.addPieSlice(PieModel(personalDoneTodos,Color.parseColor(R.color.color_green.toString())))
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