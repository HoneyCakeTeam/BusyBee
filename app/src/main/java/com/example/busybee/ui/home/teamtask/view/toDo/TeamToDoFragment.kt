package com.example.busybee.ui.home.teamtask.view.toDo

import android.os.Bundle
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.models.TeamToDoListResponse
import com.example.busybee.databinding.FragmentTeamToDoBinding
import com.example.busybee.domain.models.TeamTodos

class TeamToDoFragment : BaseFragment<FragmentTeamToDoBinding>() {
    private lateinit var adapter: TeamToDoAdapter
    override val TAG = this::class.java.simpleName.toString()
    private lateinit var todos: TeamTodos
    override fun getViewBinding(): FragmentTeamToDoBinding {
        return FragmentTeamToDoBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getTodos()
        adapter = TeamToDoAdapter(todos.values)
        binding.recyclerToDo.adapter = adapter
    }

    private fun getTodos() {
        arguments?.let {
            todos = it.getParcelable(TEAM_TODO_LIST)!!
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
}