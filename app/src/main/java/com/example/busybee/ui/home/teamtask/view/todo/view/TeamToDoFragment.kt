package com.example.busybee.ui.home.teamtask.view.todo.view

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.TeamCreateToDoResponse
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.databinding.BottomSheetCreateTaskBinding
import com.example.busybee.databinding.FragmentTeamToDoBinding
import com.example.busybee.domain.models.TeamTodos
import com.example.busybee.ui.details.view.DetailsFragment
import com.example.busybee.ui.home.teamtask.view.todo.presenter.TeamToDoPresenter
import com.example.busybee.ui.home.teamtask.view.todo.presenter.TeamToDoPresenterInterface
import com.example.busybee.utils.replaceFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class TeamToDoFragment : BaseFragment<FragmentTeamToDoBinding>(), TeamToDoViewInterface,
    TeamToDoAdapter.TeamToDoTaskInteractionListener {
    private lateinit var adapter: TeamToDoAdapter
    override val TAG = this::class.java.simpleName.toString()
    private lateinit var todos: TeamTodos
    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var sheetCreateTaskBinding: BottomSheetCreateTaskBinding
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
        binding.taskHeader.textTodoStatus.apply {
            text = "ToDo"
            background= ContextCompat.getDrawable(requireContext(), R.drawable.shape_done)
        }
        binding.taskHeader.taskCount.text = "${todos.values.size} Tasks"
    }

    private fun addCallBacks() {
        binding.buttonAddNewTeamTask.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        bottomSheet = BottomSheetDialog(
            requireContext(),
           com.google.android.material.R.style.Theme_Design_BottomSheetDialog
        )
        sheetCreateTaskBinding = BottomSheetCreateTaskBinding.inflate(layoutInflater)

        sheetCreateTaskBinding.buttonCancel.setOnClickListener {
            bottomSheet.dismiss()
        }
        sheetCreateTaskBinding.buttonCreateTask.setOnClickListener {
            val title = sheetCreateTaskBinding.textTaskName.text.toString()
            val description = sheetCreateTaskBinding.textContent.text.toString()
            val assign = sheetCreateTaskBinding.textAssignee.text.toString()
            teamCreateToDo(title, description, assign)
        }
        bottomSheet.setContentView(sheetCreateTaskBinding.root)
        bottomSheet.show()
    }

    private fun getTodos() {
        arguments?.let {
            todos = it.getParcelable(TEAM_TODO_LIST)!!
        }
    }

    override fun teamCreateToDo(title: String, description: String, assignee: String) {
        presenter.teamCreateToDo( title, description, assignee,
            ::onSuccessResponse, ::onFailureResponse
        )
    }

    override fun onSuccessResponse(response: TeamCreateToDoResponse) {
        activity?.runOnUiThread {

            setListAndUpdateUi(response)

            hideFieldsAndShowDone()

        }
    }

    private fun hideFieldsAndShowDone() {
        with(sheetCreateTaskBinding) {
            buttonCreateTask.text = getString(R.string.ok)
            textCreateTask.visibility = View.GONE
            inputLayoutAssignee.visibility = View.GONE
            inputLayoutContent.visibility = View.GONE
            inputLayoutTaskName.visibility = View.GONE
            textCreatedSuccessfully.visibility = View.VISIBLE
            buttonCreateTask.setOnClickListener {
                bottomSheet.dismiss()
            }
        }
        sheetCreateTaskBinding.lottieCreatedSuccessfully.visibility = View.VISIBLE
    }

    private fun setListAndUpdateUi(response: TeamCreateToDoResponse) {
        val newTask = response.value
        todos.values = todos.values.toMutableList().apply { add(newTask) }
        adapter.setItems(todos.values)
        binding.taskHeader.taskCount.text = "${todos.values.size} Tasks"
    }

    override fun onFailureResponse(error: Throwable) {
        activity?.runOnUiThread {
            Snackbar.make(
                binding.root,
                "Try Again! ${error.message} ",
                Snackbar.LENGTH_SHORT
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
        val detailsFragment = DetailsFragment.newInstance(flag, teamTodo, null)
        replaceFragment(detailsFragment)
    }
}