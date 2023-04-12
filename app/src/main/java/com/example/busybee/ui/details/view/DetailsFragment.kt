package com.example.busybee.ui.details.view

import android.os.Bundle
import android.widget.Toast
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.PersonalTodo
import com.example.busybee.data.models.PersonalUpdateStatusResponse
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.data.models.TeamUpdateStatusResponse
import com.example.busybee.databinding.FragmentDetailsBinding
import com.example.busybee.ui.details.presenter.DetailsPresenter
import com.example.busybee.ui.details.presenter.DetailsPresenterInterface


class DetailsFragment : BaseFragment<FragmentDetailsBinding>(), DetailsViewInterface {
    override val TAG = this::class.java.simpleName.toString()
    private var flag: Int = 0
    private var personalTodo: PersonalTodo? = null
    private var teamTodo: TeamToDo? = null
    private val presenter: DetailsPresenterInterface by lazy {
        DetailsPresenter(
            this, Repository(requireContext())
        )
    }

    override fun getViewBinding(): FragmentDetailsBinding {
        return FragmentDetailsBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        checkFlagFromHome()
    }

    private fun checkFlagFromHome() {
        flag = getTask().first
        if (flag == 1) {
            personalTodo = getTask().second
            binding.textTaskName.text = personalTodo?.title
            updateTasksPersonalStatus(personalTodo?.id!!, personalTodo?.status!! + 1)
        }
        if (flag == 0) {
            teamTodo = getTask().third
            binding.textTaskName.text = teamTodo?.title
            updateTasksTeamStatus(teamTodo?.id!!, teamTodo?.status!! + 1)
        }

    }

    override fun updateTasksPersonalStatus(idTask: String, status: Int) {
        presenter.updateTasksPersonalStatus<PersonalUpdateStatusResponse>(idTask,
            status,
            onSuccessCallback = { response ->
                onSuccessPersonalResponse(response)
            },
            onFailureCallback = { error ->
                onFailureResponse(error)
            })
    }

    override fun onSuccessPersonalResponse(response: PersonalUpdateStatusResponse) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(), "update success! ${response.isSuccess}", Toast.LENGTH_SHORT
            ).show()
        }
    }


    override fun onFailureResponse(error: Throwable) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(), "update fail ! ${error.message} ", Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onSuccessTeamResponse(response: TeamUpdateStatusResponse) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(), "update success ${response.isSuccess}", Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun updateTasksTeamStatus(idTask: String, status: Int) {
        presenter.updateTasksTeamStatus<TeamUpdateStatusResponse>(idTask,
            status,
            onSuccessCallback = { response ->
                onSuccessTeamResponse(response)
            },
            onFailureCallback = { error ->
                onFailureResponse(error)
            }
        )
    }

    private fun getTask(): Triple<Int, PersonalTodo?, TeamToDo?> {
        arguments?.let {
            flag = it.getInt(FLAG)
            if (flag == 1) {
                personalTodo = it.getParcelable<PersonalTodo>(PERSONAL_TASK)
                teamTodo = null
            } else {
                personalTodo = null
                teamTodo = it.getParcelable<TeamToDo>(TEAM_TASK)

            }
        }
        return Triple(flag, personalTodo, teamTodo)
    }

    private fun getTeamTask(): Pair<Int, TeamToDo> {
        arguments?.let {
            flag = it.getInt(FLAG)
            teamTodo = it.getParcelable<TeamToDo>(TEAM_TASK)!!
        }
        return Pair(flag, teamTodo!!)
    }

    companion object {
        const val FLAG = "flag"
        const val PERSONAL_TASK = "personalTask"
        const val TEAM_TASK = "teamTask"

        fun newInstance(flag: Int, teamTodo: TeamToDo?, personalToDo: PersonalTodo?) =
            DetailsFragment().apply {
                arguments = Bundle().apply {
                    putInt(FLAG, flag)
                    putParcelable(TEAM_TASK, teamTodo)
                    putParcelable(PERSONAL_TASK, personalToDo)
                }
            }
    }
}