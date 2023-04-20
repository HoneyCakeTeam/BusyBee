package com.example.busybee.ui.details

import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import com.example.busybee.R
import com.example.busybee.data.RepositoryImp
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.data.source.RemoteDataSourceImp
import com.example.busybee.databinding.FragmentDetailsBinding
import com.example.busybee.ui.base.BaseFragment
import com.example.busybee.ui.home.HomeFragment
import com.example.busybee.utils.*
import com.example.busybee.utils.DateTimeUtils
import com.example.busybee.utils.sharedpreference.SharedPreferencesUtils
import com.example.busybee.utils.TaskType
import com.example.busybee.utils.replaceFragment
import com.example.busybee.utils.sharedpreference.SharedPreferencesInterface

class DetailsFragment : BaseFragment<FragmentDetailsBinding>(), DetailsView {
    override val TAG = this::class.java.simpleName.toString()
    private var flag: TaskType = TaskType.PERSONAL
    private var personalTodo: PersonalToDo? = null
    private var teamTodo: TeamToDo? = null
    private val sharedPreferences: SharedPreferencesInterface by lazy {
        SharedPreferencesUtils(
            requireContext()
        )
    }
    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSourceImp(requireContext())
    }
    private val presenter by lazy {
        DetailsPresenter(
            RepositoryImp(
                remoteDataSource,
                sharedPreferences
            ), this
        )
    }


    override fun getViewBinding(): FragmentDetailsBinding {
        return FragmentDetailsBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        setStatusBarBackgroundColor(Color.TRANSPARENT)
        checkFlagFromHome()
        addCallBacks()
    }

    private fun checkFlagFromHome() {
        flag = getTask().first

        when (flag) {
            TaskType.TEAM -> {
                handleTeamTodo()
            }

            TaskType.PERSONAL -> {
                handlePersonalTodo()
            }
        }
    }

    private fun addCallBacks() {
        binding.buttonBack.setOnClickListener {
            navigateToHomeScreen(flag)
        }
        onBackButtonClicked()
    }

    private fun handlePersonalTodo() {
        personalTodo = getTask().second

        bindingPersonalToDosViews(personalTodo)

        binding.btnMove.setOnClickListener {
            updateTasksPersonalStatus(personalTodo?.id!!, personalTodo?.status!! + 1)
        }

        when (personalTodo?.status) {
            TYPE_PERSONAL_TODO -> initiatePersonalTodo()
            TYP_PERSONAL_INPROGRESS -> initiatePersonalInProgress()
            TYPE_PERSONAL_DONE -> initiatePersonalDone()
        }
    }

    private fun handleTeamTodo() {
        teamTodo = getTask().third

        bindingTeamToDosViews(teamTodo)

        binding.btnMove.setOnClickListener {
            updateTasksTeamStatus(teamTodo?.id!!, teamTodo?.status!! + 1)
        }

        when (teamTodo?.status) {
            TYPE_TEAM_TODO -> initiateTeamTodo()
            TYPE_TEAM_INPROGRESS -> initiateTeamInProgress()
            TYPE_TEAM_DONE -> initiateTeamDone()
        }
    }

    private fun initiatePersonalTodo() {
        with(binding) {
            btnMove.text = getString(R.string.move_to_in_progress)
            btnMove.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.primary_500
                )
            )
            shapeStatus.setBackgroundResource(R.drawable.shape_circle_todo)
        }
    }

    private fun initiatePersonalInProgress() {
        with(binding) {
            btnMove.text = getString(R.string.move_to_done)
            btnMove.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color_green
                )
            )
            shapeStatus.setBackgroundResource(R.drawable.shape_circle_inprogress)
            btnMove.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_done)
        }
    }

    private fun initiatePersonalDone() {
        with(binding) {
            btnMove.visibility = View.GONE
            shapeStatus.setBackgroundResource(R.drawable.shape_circle_done)
        }
    }

    private fun initiateTeamTodo() {
        with(binding) {
            btnMove.text = getString(R.string.move_to_in_progress)
            btnMove.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.primary_500
                )
            )
            shapeStatus.setBackgroundResource(R.drawable.shape_circle_todo)
        }
    }

    private fun initiateTeamInProgress() {
        with(binding) {
            btnMove.text = getString(R.string.move_to_done)
            btnMove.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.color_green
                )
            )
            shapeStatus.setBackgroundResource(R.drawable.shape_circle_inprogress)
            btnMove.icon = ContextCompat.getDrawable(requireContext(), R.drawable.ic_done)
        }
    }

    private fun initiateTeamDone() {
        with(binding) {
            btnMove.visibility = View.GONE
            shapeStatus.setBackgroundResource(R.drawable.shape_circle_done)
        }
    }

    private fun updateTasksPersonalStatus(idTask: String, status: Int) {
        presenter.updateTasksPersonalStatus(idTask, status)
    }

    override fun goToPersonalToDo() {
        presenter.updateLocalTasksPersonalStatus(
            personalTodo?.id!!,
            personalTodo?.status!! + 1
        )
        activity?.runOnUiThread {

            navigateToHomeScreen(flag)
        }
    }

    override fun showPersonalErrorMsg(error: Throwable) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(), "Personal update fail ! ${error.message} ", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateTasksTeamStatus(idTask: String, status: Int) {
        presenter.updateTasksTeamStatus(idTask, status)
    }

    override fun goToTeamToDo() {
        presenter.updateLocalTasksTeamStatus(
            teamTodo?.id!!,
            teamTodo?.status!! + 1
        )
        activity?.runOnUiThread {

            navigateToHomeScreen(flag)
        }
    }

    override fun showTeamErrorMsg(error: Throwable) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(), "Team update fail ! ${error.message} ", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getTask(): Triple<TaskType, PersonalToDo?, TeamToDo?> {
        arguments?.let {
            flag = it.getParcelable(FLAG)!!
            when (flag) {
                TaskType.PERSONAL -> {
                    personalTodo = it.getParcelable<PersonalToDo>(PERSONAL_TASK)
                    teamTodo = null
                }

                TaskType.TEAM -> {
                    personalTodo = null
                    teamTodo = it.getParcelable<TeamToDo>(TEAM_TASK)
                }
            }
        }
        return Triple(flag, personalTodo, teamTodo)
    }


    private fun bindingPersonalToDosViews(personalToDo: PersonalToDo?) {
        val (formattedTime, formattedDate) = DateTimeUtils.formatDateTime(
            personalToDo?.creationTime ?: ""
        )
        with(binding) {
            textTaskName.text = personalToDo?.title
            textTaskDate.text = formattedDate
            textDescription.text = personalToDo?.description
            textTaskTime.text = formattedTime
            textTaskMemberAssign.visibility = View.GONE
        }
    }

    private fun bindingTeamToDosViews(teamToDo: TeamToDo?) {
        val (formattedTime, formattedDate) = DateTimeUtils.formatDateTime(
            teamToDo?.creationTime ?: ""
        )
        with(binding) {
            textTaskName.text = teamToDo?.title
            textTaskDate.text = formattedDate
            textDescription.text = teamToDo?.description
            textTaskTime.text = formattedTime
            textTaskMemberAssign.text = teamToDo?.assignee
        }
    }

    private fun navigateToHomeScreen(type: TaskType) {
        val homeFragment = HomeFragment.newInstance(type)
        replaceFragment(homeFragment)
    }

    private fun onBackButtonClicked() {
        val callback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                navigateToHomeScreen(type = flag)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
        callback.isEnabled = true
    }

    companion object {
        const val FLAG = "flag"
        const val PERSONAL_TASK = "personalTask"
        const val TEAM_TASK = "teamTask"
        private const val TYPE_PERSONAL_TODO = 0
        private const val TYP_PERSONAL_INPROGRESS = 1
        private const val TYPE_PERSONAL_DONE = 2
        private const val TYPE_TEAM_TODO = 0
        private const val TYPE_TEAM_INPROGRESS = 1
        private const val TYPE_TEAM_DONE = 2

        fun newInstance(taskType: TaskType, teamTodo: TeamToDo?, personalToDo: PersonalToDo?) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(FLAG, taskType)
                    putParcelable(TEAM_TASK, teamTodo)
                    putParcelable(PERSONAL_TASK, personalToDo)
                }
            }
    }
}