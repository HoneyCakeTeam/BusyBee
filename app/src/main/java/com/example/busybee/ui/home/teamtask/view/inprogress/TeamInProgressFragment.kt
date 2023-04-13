package com.example.busybee.ui.home.teamtask.view.inprogress

import android.os.Bundle
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.databinding.FragmentTeamInProgressBinding
import com.example.busybee.domain.models.TeamTodos
import com.example.busybee.ui.details.view.DetailsFragment
import com.example.busybee.utils.replaceFragment

class TeamInProgressFragment : BaseFragment<FragmentTeamInProgressBinding>(),
    TeamInProgressAdapter.TeamInProgressTaskInteractionListener {
    private lateinit var adapter: TeamInProgressAdapter
    override val TAG = this::class.java.simpleName.toString()
    private lateinit var inProgress: TeamTodos

    override fun getViewBinding(): FragmentTeamInProgressBinding {
        return FragmentTeamInProgressBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getInProgress()
        adapter = TeamInProgressAdapter(inProgress.values, this)
        binding.recyclerInProgress.adapter = adapter
        binding.taskHeader.textTodoStatus.text = getString(R.string.in_progress)
        binding.taskHeader.taskCount.text = getString(R.string.tasks, inProgress.values.size)
        binding.taskHeader.textTodoStatus.setBackgroundResource(R.drawable.shape_inprogress)
    }

    private fun getInProgress() {
        arguments?.let {
            inProgress = it.getParcelable(TEAM_IN_PROGRESS_LIST)!!
        }
    }

    companion object {
        const val TEAM_IN_PROGRESS_LIST = "Team_InProgress_List"
        fun newInstance(tasks: TeamTodos) =
            TeamInProgressFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TEAM_IN_PROGRESS_LIST, tasks)
                }
            }
    }

    override fun onTasKClicked(flag: Int, teamTodo: TeamToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, teamTodo, null)
        replaceFragment(detailsFragment)
    }
}