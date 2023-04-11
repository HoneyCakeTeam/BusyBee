package com.example.busybee.ui.home.teamtask.view.inProgress

import android.os.Bundle
import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentTeamInProgressBinding
import com.example.busybee.domain.models.TeamTodos
import com.example.busybee.ui.home.teamtask.view.done.TeamDoneFragment
import com.example.busybee.ui.home.teamtask.view.toDo.TeamToDoFragment

class TeamInProgressFragment : BaseFragment<FragmentTeamInProgressBinding>() {
    private lateinit var adapter: TeamInProgressAdapter
    override val TAG = this::class.java.simpleName.toString()
    private lateinit var inProgress: TeamTodos

    override fun getViewBinding(): FragmentTeamInProgressBinding {
        return FragmentTeamInProgressBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getInProgress()
        adapter = TeamInProgressAdapter(inProgress.values)
        binding.recyclerInProgress.adapter = adapter
    }
    private fun getInProgress() {
        arguments?.let {
            inProgress = it.getParcelable(TEAM_INPREOGRESS_LIST)!!
        }
    }
    companion object {
        const val TEAM_INPREOGRESS_LIST = "Team_InProgress_List"
        fun newInstance(tasks: TeamTodos) =
            TeamInProgressFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TEAM_INPREOGRESS_LIST, tasks)
                }
            }
    }
}