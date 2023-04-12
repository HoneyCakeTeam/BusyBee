package com.example.busybee.ui.details.view

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.busybee.R
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
            handlePersonalTodo()
        } else if (flag == 0) {
            handleTeamTodo()
        }
    }

    private fun handlePersonalTodo() {
        personalTodo = getTask().second

        binding.textTaskName.text = personalTodo?.title

        binding.btnMove.setOnClickListener {

            when (personalTodo?.status) {
                0 -> {
                    binding.btnMove.text = "Move to in progress"
                }
                1 -> {
                    binding.btnMove.text = "Move to done"
                }
                2 -> {
                    binding.btnMove.visibility = View.GONE
                }
            }
        }
    }

    private fun handleTeamTodo() {
        teamTodo = getTask().third

        binding.textTaskName.text = teamTodo?.title
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

    private fun bindingPersonalToDosViews(personalToDo: PersonalTodo?){
        with(binding) {
            textTaskName.text = personalTodo?.title
            textTaskDate.text = personalTodo?.creationTime
            textDescription.text = personalTodo?.description
            textTaskTime.text = personalTodo?.creationTime
            textTaskMemberAssign.visibility = View.GONE
        }
    }
}