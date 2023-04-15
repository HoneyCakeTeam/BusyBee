package com.example.busybee.ui.details.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.databinding.FragmentDetailsBinding
import com.example.busybee.ui.details.presenter.DetailsPresenter
import com.example.busybee.ui.home.HomeFragment
import com.example.busybee.ui.home.personaltask.done.view.PersonalDoneFragment
import com.example.busybee.ui.home.personaltask.inprogress.view.PersonalInProgressFragment
import com.example.busybee.utils.DateTimeUtils
import com.example.busybee.utils.SharedPreferencesUtils
import com.example.busybee.utils.replaceFragment
class DetailsFragment : BaseFragment<FragmentDetailsBinding>(), DetailsViewInterface {
    override val TAG = this::class.java.simpleName.toString()
    private var flag: Int = 0
    private var personalTodo: PersonalToDo? = null
    private var teamTodo: TeamToDo? = null
    private val presenter by lazy {
        DetailsPresenter(
            Repository(
                RemoteDataSource(requireContext()),
                SharedPreferencesUtils, requireContext()
            ), this
        )
    }

    override fun getViewBinding(): FragmentDetailsBinding {
        return FragmentDetailsBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        checkFlagFromHome()
        addCallBacks()
    }

    private fun checkFlagFromHome() {
        flag = getTask().first
        if (flag == 1) {
            handlePersonalTodo()
        } else if (flag == 0) {
            handleTeamTodo()
        }
    }

    private fun addCallBacks() {
        binding.buttonBack.setOnClickListener {
            replaceFragment(HomeFragment())
        }
    }

    private fun handlePersonalTodo() {
        personalTodo = getTask().second

        bindingPersonalToDosViews(personalTodo)

        binding.btnMove.setOnClickListener {
            updateTasksPersonalStatus(personalTodo?.id!!, personalTodo?.status!! + 1)
        }

        when (personalTodo?.status) {
            0 -> {
                binding.btnMove.text = getString(R.string.move_to_in_progress)
            }
            1 -> {
                binding.btnMove.text = getString(R.string.move_to_done)
            }
            2 -> {
                binding.btnMove.visibility = View.GONE
            }
        }
    }

    private fun handleTeamTodo() {
        teamTodo = getTask().third

        bindingTeamToDosViews(teamTodo)

        binding.btnMove.setOnClickListener {
            updateTasksTeamStatus(teamTodo?.id!!, teamTodo?.status!! + 1)
        }

        when (teamTodo?.status) {
            0 -> {
                binding.btnMove.text = getString(R.string.move_to_in_progress)
            }
            1 -> {
                binding.btnMove.text = getString(R.string.move_to_done)
            }
            2 -> {
                binding.btnMove.visibility = View.GONE
            }
        }
    }

    private fun updateTasksPersonalStatus(idTask: String, status: Int) {
        presenter.updateTasksPersonalStatus<BaseResponse<String>>(idTask, status)
    }

    override fun onUpdatePersonalStatusSuccess(response: BaseResponse<String>) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(),
                "Personal update success ${response.isSuccess}",
                Toast.LENGTH_SHORT
            ).show()

            replaceFragment(HomeFragment())

        }
    }

    override fun onUpdatePersonalStatusFailed(error: Throwable) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(), "Personal update fail ! ${error.message} ", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun updateTasksTeamStatus(idTask: String, status: Int) {
        presenter.updateTasksTeamStatus<BaseResponse<String>>(idTask, status)
    }

    override fun onUpdateTeamStatusSuccess(response: BaseResponse<String>) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(), "Team update success ${response.isSuccess}", Toast.LENGTH_SHORT
            ).show()
            replaceFragment(HomeFragment())
        }
    }

    override fun onUpdateTeamStatusFailed(error: Throwable) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(), "Team update fail ! ${error.message} ", Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun getTask(): Triple<Int, PersonalToDo?, TeamToDo?> {
        arguments?.let {
            flag = it.getInt(FLAG)
            if (flag == 1) {
                personalTodo = it.getParcelable<PersonalToDo>(PERSONAL_TASK)
                teamTodo = null
            } else {
                personalTodo = null
                teamTodo = it.getParcelable<TeamToDo>(TEAM_TASK)

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
    companion object {
        const val FLAG = "flag"
        const val PERSONAL_TASK = "personalTask"
        const val TEAM_TASK = "teamTask"

        fun newInstance(flag: Int, teamTodo: TeamToDo?, personalToDo: PersonalToDo?) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(FLAG, flag)
                    putParcelable(TEAM_TASK, teamTodo)
                    putParcelable(PERSONAL_TASK, personalToDo)
                }
            }
    }
}