package com.example.busybee.ui.home.view.teamTask.view.done

import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentTeamDoneBinding

class TeamDoneFragment : BaseFragment<FragmentTeamDoneBinding>() {
    private lateinit var adapter: TeamDoneAdapter
    override val TAG = this::class.java.simpleName.toString()
    override fun getViewBinding(): FragmentTeamDoneBinding {
        return FragmentTeamDoneBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        adapter = TeamDoneAdapter(emptyList())
        binding.recyclerDone.adapter = adapter
    }
}