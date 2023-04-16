package com.example.busybee.ui.setting.view

import androidx.core.content.ContextCompat
import com.example.busybee.R
import com.example.busybee.ui.base.BaseFragment
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
    private var teamTodos by Delegates.notNull<Float>()
    private var teamInProgressTodos by Delegates.notNull<Float>()
    private var teamDoneTodos by Delegates.notNull<Float>()


    private val presenter by lazy {
        SettingsPresenter(
            Repository(
                RemoteDataSource(requireContext()),
                SharedPreferencesUtils(requireContext())
            ), this
        )
    }

    override fun getViewBinding(): FragmentSettingsBinding =
        FragmentSettingsBinding.inflate(layoutInflater)

    override fun setUp() {
        getTasksCount()
        addDefaultStateForPieChart()
        addDefaultStateForData()
        addCallBacks()
    }

    private fun addDefaultStateForData() {
        showToDosPercentage(personalTodos, personalInProgressTodos, personalDoneTodos)
    }

    private fun addDefaultStateForPieChart() {
        setUpPieChart(personalTodos, personalInProgressTodos, personalDoneTodos)
    }

    private fun setUpPieChart(todos: Float, inProgress: Float, dones: Float) {
        binding.piechart.addPieSlice(
            PieModel(
                todos,
                ContextCompat.getColor(requireContext(), R.color.secondary_500)
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                inProgress,
                ContextCompat.getColor(requireContext(), R.color.primary_500)
            )
        )
        binding.piechart.addPieSlice(
            PieModel(
                dones,
                ContextCompat.getColor(requireContext(), R.color.color_green)
            )
        )

    }

    private fun addCallBacks() {
        onClickLogout()
        onClickButtonBack()
        onClickPersonalButton()
        onClickTeamButton()
    }

    private fun onClickLogout() {
        binding.viewLogoutSettings.setOnClickListener {
            presenter.setToken(null)
            replaceFragment(loginFragment)
        }
    }

    private fun getTasksCount() {
        presenter.getLocalPersonalDones()
        presenter.getLocalPersonalTodos()
        presenter.getLocalPersonalInProgress()
        presenter.getLocalTeamDones()
        presenter.getLocalTeamInProgress()
        presenter.getLocalTeamTodos()

    }


    private fun showToDosPercentage(todos: Float, inProgress: Float, dones: Float) {
        val sumOfToDos = sum(todos, inProgress, dones)
        val toDoPercentage = calculatePercentage(sumOfToDos, todos)
        val inProgressPercentage = calculatePercentage(sumOfToDos, inProgress)
        val donePercentage = calculatePercentage(sumOfToDos, dones)
        binding.textTodoPercentage.text =
            getString(R.string.todos_percentage, toDoPercentage.toInt())
        binding.textInProgressPercentage.text =
            getString(R.string.todos_percentage, inProgressPercentage.toInt())
        binding.textDonePercentage.text =
            getString(R.string.todos_percentage, donePercentage.toInt())
        binding.textTotalTasksNum.text = sumOfToDos.toInt().toString()
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
        teamDoneTodos = dones.toFloat()
    }

    override fun getLocalTeamInProgress(inProgress: Int) {
        teamInProgressTodos = inProgress.toFloat()
    }

    override fun getLocalTeamTodos(todos: Int) {
        teamTodos = todos.toFloat()
    }


    private fun onClickPersonalButton() {
        binding.buttonPersonalTodos.setOnClickListener {
            clearChart()
            setUpPieChart(personalTodos, personalInProgressTodos, personalDoneTodos)
            showToDosPercentage(personalTodos, personalInProgressTodos, personalDoneTodos)
        }
    }

    private fun onClickTeamButton() {
        binding.buttonTeamTodos.setOnClickListener {
            clearChart()
            setUpPieChart(teamTodos, teamInProgressTodos, teamDoneTodos)
            showToDosPercentage(teamTodos, teamInProgressTodos, teamDoneTodos)
        }
    }

    private fun clearChart() {
        binding.piechart.clearChart()
    }

}