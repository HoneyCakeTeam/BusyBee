package com.example.busybee.ui.home.teamTask.done

import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentTeamDoneBinding

class TeamDoneFragment : BaseFragment<FragmentTeamDoneBinding>() {
    private lateinit var adapter: TeamDoneAdapter
    override val TAG = "DoneFragment"

    override fun getViewBinding(): FragmentTeamDoneBinding {
        return FragmentTeamDoneBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        adapter = TeamDoneAdapter(emptyList())
        binding.recyclerDone.adapter = adapter
    }
}