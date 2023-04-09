package com.example.busybee.ui.home.teamTask.toDo

import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentToDoBinding

class TeamToDoFragment : BaseFragment<FragmentToDoBinding>() {
    private lateinit var adapter: TeamToDoAdapter
    override val TAG = "ToDoFragment"

    override fun getViewBinding(): FragmentToDoBinding {
        return FragmentToDoBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        adapter = TeamToDoAdapter(emptyList())
        binding.recyclerToDo.adapter = adapter
    }
}