package com.example.busybee.ui.home.teamtask.view.toDo

import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentTeamToDoBinding

class TeamToDoFragment : BaseFragment<FragmentTeamToDoBinding>() {
    private lateinit var adapter: TeamToDoAdapter
    override val TAG = this::class.java.simpleName.toString()
    override fun getViewBinding(): FragmentTeamToDoBinding {
        return FragmentTeamToDoBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        adapter = TeamToDoAdapter(emptyList())
        binding.recyclerToDo.adapter = adapter
    }
}