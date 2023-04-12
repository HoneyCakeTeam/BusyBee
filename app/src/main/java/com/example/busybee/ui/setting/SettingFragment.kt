package com.example.busybee.ui.setting

import android.graphics.Color
import androidx.core.content.ContextCompat
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentSettingsBinding
import com.example.busybee.ui.home.HomeFragment
import com.example.busybee.ui.login.view.LoginFragment
import com.example.busybee.utils.SharedPreferencesUtils
import com.example.busybee.utils.replaceFragment
import org.eazegraph.lib.models.PieModel

class SettingFragment : BaseFragment<FragmentSettingsBinding>() {
    override val TAG: String
        get() = this::class.simpleName.toString()
    private val loginFragment = LoginFragment()

    private var personalTodos = 30.0f
    private var personalInProgressTodos = 30.0f
    private var personalDoneTodos = 40.0f

    override fun getViewBinding(): FragmentSettingsBinding =
        FragmentSettingsBinding.inflate(layoutInflater)


    override fun setUp() {
       setUpPieChart()
        addCallBacks()
        showToDosPercentage()
    }

    private fun setUpPieChart() {
        binding.piechart.addPieSlice(
            PieModel(
                personalTodos,
                ContextCompat.getColor(requireContext(), R.color.secondary_500)
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                personalInProgressTodos,
                ContextCompat.getColor(requireContext(), R.color.primary_500)
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                personalDoneTodos,
                ContextCompat.getColor(requireContext(), R.color.color_green)
            )
        )

    }

    private fun addCallBacks() {
        onClickLogout()
        buttonBackPressed()
    }

    private fun onClickLogout() {
        binding.viewLogoutSettings.setOnClickListener {
            SharedPreferencesUtils.token = null
            replaceFragment(loginFragment)
        }
    }

    private fun showToDosPercentage() {
        val sumOfPersonalToDos = sum(personalTodos, personalInProgressTodos, personalDoneTodos)
        val toDoPercentage = calculatePercentage(sumOfPersonalToDos, personalTodos)
        val inProgressPercentage = calculatePercentage(sumOfPersonalToDos, personalInProgressTodos)
        val donePercentage = calculatePercentage(sumOfPersonalToDos, personalDoneTodos)

        binding.textTodoPercentage.text = getString(R.string.todos_percentage, toDoPercentage.toInt())
        binding.textInProgressPercentage.text = getString(R.string.todos_percentage, toDoPercentage.toInt())
        binding.textDonePercentage.text = getString(R.string.todos_percentage, toDoPercentage.toInt())
    }

    private fun calculatePercentage(sumOfToDos: Float, variableOfInterest: Float): Float {

        return (variableOfInterest / sumOfToDos) * 100
    }

    private fun sum(
        personalToDos: Float, personalInProgressToDos: Float,
        personalDoneToDos: Float
    ) = personalToDos + personalInProgressToDos + personalDoneToDos

    private fun buttonBackPressed() {
        binding.icLeftArrow.setOnClickListener {
            replaceFragment(HomeFragment())
        }
    }
}