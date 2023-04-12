package com.example.busybee.ui.home.teamtask.view.todo.view

import android.os.Bundle
import android.widget.Toast
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.data.models.TeamToDoListResponse
import com.example.busybee.databinding.BottomSheetCreateTaskBinding
import com.example.busybee.databinding.FragmentTeamToDoBinding
import com.example.busybee.domain.models.TeamTodos
import com.example.busybee.ui.details.view.DetailsFragment
import com.example.busybee.ui.home.teamtask.view.todo.presenter.TeamToDoPresenter
import com.example.busybee.ui.home.teamtask.view.todo.presenter.TeamToDoPresenterInterface
import com.example.busybee.utils.replaceFragment
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class TeamToDoFragment : BaseFragment<FragmentTeamToDoBinding>(), TeamToDoViewInterface,
    TeamToDoAdapter.TeamToDoTaskInteractionListener {
    private lateinit var adapter: TeamToDoAdapter
    override val TAG = this::class.java.simpleName.toString()
    private lateinit var todos: TeamTodos
    private val presenter: TeamToDoPresenterInterface by lazy {
        TeamToDoPresenter(Repository(requireContext()))
    }

    override fun getViewBinding(): FragmentTeamToDoBinding {
        return FragmentTeamToDoBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getTodos()
        addCallBacks()
        adapter = TeamToDoAdapter(todos.values, this)
        binding.recyclerToDo.adapter = adapter
        binding.taskHeader.textTodoStatus.text = "ToDo"
        binding.taskHeader.taskCount.text = "${todos.values.size} Tasks"
    }

    private fun addCallBacks() {
        binding.buttonAddNewTeamTask.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val bottomSheet = BottomSheetDialog(
            requireContext(),
            R.style.Theme_Design_BottomSheetDialog
        )
        val binding = BottomSheetCreateTaskBinding.inflate(layoutInflater)

        binding.buttonCancel.setOnClickListener {
            bottomSheet.dismiss()
        }
        binding.buttonCreateTask.setOnClickListener {
            val title = binding.textTaskName.text.toString()
            val description = binding.textContent.text.toString()
            val assign = binding.textAssignee.text.toString()
            teamCreateToDo(title, description, assign)
        }
        bottomSheet.setContentView(binding.root)
        bottomSheet.show()
    }

    private fun getTodos() {
        arguments?.let {
            todos = it.getParcelable(TEAM_TODO_LIST)!!
        }
    }

    override fun teamCreateToDo(title: String, description: String, assignee: String) {
        presenter.teamCreateToDo<TeamToDoListResponse>(
            title, description, assignee,
            ::onSuccessResponse, ::onFailureResponse
        )
    }

    override fun onSuccessResponse(response: TeamToDoListResponse) {
        activity?.runOnUiThread {

            // binding.lottieCreatedSuccessfully.visibility = View.VISIBLE
        }
    }

    override fun onFailureResponse(error: Throwable) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(),
                "Try Again! ${error.message} ",
                Toast.LENGTH_SHORT
            ).show()
        }

    }

    companion object {
        const val TEAM_TODO_LIST = "Team_Todo_List"
        fun newInstance(tasks: TeamTodos) =
            TeamToDoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TEAM_TODO_LIST, tasks)
                }
            }
    }


    override fun onTasKClicked(flag: Int, teamTodo: TeamToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, teamTodo , null)
        replaceFragment(detailsFragment)
    }
}