package com.example.busybee.ui.home.view.teamTask.view.inProgress

import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentTeamInProgressBinding

class TeamInProgressFragment : BaseFragment<FragmentTeamInProgressBinding>() {
    private lateinit var adapter: TeamInProgressAdapter
    override val TAG = this::class.java.simpleName.toString()
    override fun getViewBinding(): FragmentTeamInProgressBinding {
        return FragmentTeamInProgressBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        adapter = TeamInProgressAdapter(emptyList())
        binding.recyclerInProgress.adapter = adapter
    }
}