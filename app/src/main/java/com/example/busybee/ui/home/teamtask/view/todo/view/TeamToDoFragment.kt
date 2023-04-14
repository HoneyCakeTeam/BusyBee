package com.example.busybee.ui.home.teamtask.view.todo.view

import android.os.Build
import android.os.Bundle
import android.view.View
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.databinding.BottomSheetCreateTaskBinding
import com.example.busybee.databinding.FragmentTeamToDoBinding
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
    private lateinit var todos: List<TeamToDo>
    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var sheetCreateTaskBinding: BottomSheetCreateTaskBinding
    private val presenter: TeamToDoPresenterInterface by lazy {
        TeamToDoPresenter(RemoteDataSource(requireContext()))
    }

    override fun getViewBinding(): FragmentTeamToDoBinding {
        return FragmentTeamToDoBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getTodos()
        addCallBacks()
        adapter = TeamToDoAdapter(todos, this)

        binding.recyclerToDo.adapter = adapter
        binding.taskHeader.textTodoStatus.text = getString(R.string.to_do)
        binding.taskHeader.taskCount.text = getString(R.string.tasks, todos.size)
        binding.taskHeader.textTodoStatus.setBackgroundResource(R.drawable.shape_todo)
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
            todos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelableArrayList(TEAM_TODO_LIST, TeamToDo::class.java)!!
            } else {
                it.getParcelableArrayList(TEAM_TODO_LIST)!!
            }
        }
    }

    override fun teamCreateToDo(title: String, description: String, assignee: String) {
        presenter.teamCreateToDo( title, description, assignee,
            ::onSuccessResponse, ::onFailureResponse
        )
    }

    override fun onSuccessResponse(response: BaseResponse<TeamToDo>) {
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
    }

    private fun setListAndUpdateUi(response: BaseResponse<TeamToDo>) {
        val newTask = response.value
        todos = todos.toMutableList().apply { add(newTask) }
        adapter.setItems(todos)
        binding.taskHeader.taskCount.text = "${todos.size} Tasks"
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
        fun newInstance(tasks: ArrayList<TeamToDo>) =
            TeamToDoFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(TEAM_TODO_LIST, tasks)
                }
            }
    }


    override fun onTasKClicked(flag: Int, teamTodo: TeamToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, teamTodo, null)
        replaceFragment(detailsFragment)
    }
}