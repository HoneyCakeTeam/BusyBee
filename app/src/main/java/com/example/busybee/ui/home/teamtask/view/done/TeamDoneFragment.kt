package com.example.busybee.ui.home.teamtask.view.done

import android.os.Bundle
import android.util.Log
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.databinding.FragmentTeamDoneBinding
import com.example.busybee.domain.models.TeamTodos
import com.example.busybee.ui.details.view.DetailsFragment
import com.example.busybee.utils.replaceFragment

class TeamDoneFragment : BaseFragment<FragmentTeamDoneBinding>(),
    TeamDoneAdapter.TeamDoneTaskInteractionListener {
    private lateinit var adapter: TeamDoneAdapter
    override val TAG = this::class.java.simpleName.toString()
    private lateinit var done: TeamTodos

    override fun getViewBinding(): FragmentTeamDoneBinding {
        return FragmentTeamDoneBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getDons()
        adapter = TeamDoneAdapter(done.values, this)
        Log.e(TAG, "setUp: ${done.values}")
        binding.recyclerDone.adapter = adapter
        binding.taskHeader.textTodoStatus.text = "Done"
        binding.taskHeader.taskCount.text = "${done.values.size} Tasks"
    }

    private fun getDons() {
        arguments?.let {
            done = it.getParcelable(TEAM_DONE_LIST)!!
        }
    }

    companion object {
        const val TEAM_DONE_LIST = "Team_Done_List"
        fun newInstance(tasks: TeamTodos) =
            TeamDoneFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(TEAM_DONE_LIST, tasks)
                }
            }
    }

    override fun onTasKClicked(flag: Int, teamTodo: TeamToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, teamTodo, null)
        replaceFragment(detailsFragment)
    }
}