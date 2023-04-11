package com.example.busybee.ui.home.teamtask.view.done

import android.os.Bundle
import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentTeamDoneBinding
import com.example.busybee.domain.models.TeamTodos

class TeamDoneFragment : BaseFragment<FragmentTeamDoneBinding>() {
    private lateinit var adapter: TeamDoneAdapter
    override val TAG = this::class.java.simpleName.toString()
    private lateinit var done: TeamTodos

    override fun getViewBinding(): FragmentTeamDoneBinding {
        return FragmentTeamDoneBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getDons()
        adapter = TeamDoneAdapter(done.values)
        binding.recyclerDone.adapter = adapter
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
}