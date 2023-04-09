package com.example.busybee.ui.home.teamTask.done

import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentDoneBinding

class TeamDoneFragment : BaseFragment<FragmentDoneBinding>() {
    private lateinit var adapter: TeamDoneAdapter
    override val TAG = "DoneFragment"

    override fun getViewBinding(): FragmentDoneBinding {
        return FragmentDoneBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        adapter = TeamDoneAdapter(emptyList())
        binding.recyclerDone.adapter = adapter
    }
}