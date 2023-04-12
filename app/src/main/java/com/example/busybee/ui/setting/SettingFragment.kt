package com.example.busybee.ui.setting

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentSettingsBinding
import com.example.busybee.ui.home.HomeFragment
import com.example.busybee.ui.login.view.LoginFragment
import com.example.busybee.utils.SharedPreferencesUtils
import com.example.busybee.utils.replaceFragment
import org.eazegraph.lib.models.PieModel
import kotlin.properties.Delegates

class SettingFragment : BaseFragment<FragmentSettingsBinding>() {
    override val TAG: String
        get() = this::class.simpleName.toString()
    private val loginFragment = LoginFragment()
    private var personalTodos by Delegates.notNull<Float>()
    private var personalInProgressTodos by Delegates.notNull<Float>()
    private var personalDoneTodos by Delegates.notNull<Float>()

    override fun getViewBinding(): FragmentSettingsBinding =
        FragmentSettingsBinding.inflate(layoutInflater)

    override fun setUp() {
        getTasksCount()
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
        onClickBackButton()
    }

    private fun onClickBackButton() {
        binding.buttonBack.setOnClickListener {
            val homeFragment = HomeFragment()
            replaceFragment(homeFragment)
        }
    }

    private fun onClickLogout() {
        binding.viewLogoutSettings.setOnClickListener {
            SharedPreferencesUtils.initPreferencesUtil(requireContext())
            SharedPreferencesUtils.token = null
            replaceFragment(loginFragment)
        }
    }

    companion object {
        const val PERSONAL_TO_DO_COUNT = "Personal_To_Do_Count"
        const val PERSONAL_IN_PROGRESS_COUNT = "Personal_In_Progress_Count"
        const val PERSONAL_DONE_COUNT = "Personal_Done_Count"
        fun newInstance(personalTodos: Int, personalInProgressTodos: Int, personalDoneTodos: Int) =
            SettingFragment().apply {
                arguments = Bundle().apply {
                    putInt(PERSONAL_TO_DO_COUNT, personalTodos)
                    putInt(PERSONAL_IN_PROGRESS_COUNT, personalInProgressTodos)
                    putInt(PERSONAL_DONE_COUNT, personalDoneTodos)
                }
            }
    }

    private fun getTasksCount() {
        arguments?.let {
            personalTodos = it.getInt(PERSONAL_TO_DO_COUNT).toFloat()
            personalInProgressTodos = it.getInt(PERSONAL_IN_PROGRESS_COUNT).toFloat()
            personalDoneTodos = it.getInt(PERSONAL_DONE_COUNT).toFloat()
        }
    }


    @SuppressLint("SetTextI18n")
    private fun showToDosPercentage() {
        val sumOfPersonalToDos = sum(personalTodos, personalInProgressTodos, personalDoneTodos)
        val toDoPercentage = calculatePercentage(sumOfPersonalToDos, personalTodos)
        val inProgressPercentage = calculatePercentage(sumOfPersonalToDos, personalInProgressTodos)
        val donePercentage = calculatePercentage(sumOfPersonalToDos, personalDoneTodos)

        binding.textTodoPercentage.text = toDoPercentage.toInt().toString() + " %"
        binding.textInProgressPercentage.text = inProgressPercentage.toInt().toString()  + " %"
        binding.textDonePercentage.text = donePercentage.toInt().toString() + " %"
        binding.textTotalTasksNum.text = sumOfPersonalToDos.toInt().toString()
    }

    private fun calculatePercentage(sumOfToDos: Float, variableOfInterest: Float): Float {
        return (variableOfInterest / sumOfToDos) * 100
    }

    private fun sum(
        personalToDos: Float, personalInProgressToDos: Float,
        personalDoneToDos: Float
    ) = personalToDos + personalInProgressToDos + personalDoneToDos

}