package com.example.busybee.ui.setting.view

import android.annotation.SuppressLint
import androidx.core.content.ContextCompat
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.databinding.FragmentSettingsBinding
import com.example.busybee.ui.home.HomeFragment
import com.example.busybee.ui.login.view.LoginFragment
import com.example.busybee.ui.setting.presenter.SettingsPresenter
import com.example.busybee.utils.SharedPreferencesUtils
import com.example.busybee.utils.replaceFragment
import org.eazegraph.lib.models.PieModel
import kotlin.properties.Delegates

class SettingFragment : BaseFragment<FragmentSettingsBinding>(), SettingsViewInterface {
    override val TAG: String
        get() = this::class.simpleName.toString()
    private val loginFragment = LoginFragment()
    private var personalTodos by Delegates.notNull<Float>()
    private var personalInProgressTodos by Delegates.notNull<Float>()
    private var personalDoneTodos by Delegates.notNull<Float>()

    private val presenter by lazy {
        SettingsPresenter(
            Repository(
                RemoteDataSource(requireContext()),
                SharedPreferencesUtils, requireContext()
            ), this
        )
    }

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
        onClickButtonBack()
    }

    private fun onClickLogout() {
        binding.viewLogoutSettings.setOnClickListener {
            SharedPreferencesUtils.initPreferencesUtil(requireContext())
            SharedPreferencesUtils.token = null
            replaceFragment(loginFragment)
        }
    }

    private fun getTasksCount() {
        presenter.getLocalPersonalDones()
        presenter.getLocalPersonalTodos()
        presenter.getLocalPersonalInProgress()
    }


    @SuppressLint("SetTextI18n")
    private fun showToDosPercentage() {
        val sumOfPersonalToDos = sum(personalTodos, personalInProgressTodos, personalDoneTodos)
        val toDoPercentage = calculatePercentage(sumOfPersonalToDos, personalTodos)
        val inProgressPercentage = calculatePercentage(sumOfPersonalToDos, personalInProgressTodos)
        val donePercentage = calculatePercentage(sumOfPersonalToDos, personalDoneTodos)

        binding.textTodoPercentage.text = toDoPercentage.toInt().toString() + " %"
        binding.textInProgressPercentage.text = inProgressPercentage.toInt().toString() + " %"
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

    private fun onClickButtonBack() {
        binding.icLeftArrow.setOnClickListener {
            replaceFragment(HomeFragment())
        }
    }

    override fun getLocalPersonalDones(dones: Int) {
        personalDoneTodos = dones.toFloat()
    }

    override fun getLocalPersonalInProgress(inProgress: Int) {
        personalInProgressTodos = inProgress.toFloat()
    }

    override fun getLocalPersonalTodos(todos: Int) {
        personalTodos = todos.toFloat()
    }

    override fun getLocalTeamDones(dones: Int) {
        TODO("Not yet implemented")
    }

    override fun getLocalTeamInProgress(inProgress: Int) {
        TODO("Not yet implemented")
    }

    override fun getLocalTeamTodos(todos: Int) {
        TODO("Not yet implemented")
    }

}