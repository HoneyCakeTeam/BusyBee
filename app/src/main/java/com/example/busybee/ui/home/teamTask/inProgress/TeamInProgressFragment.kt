package com.example.busybee.ui.home.teamTask.inProgress

import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentInProgressBinding

class TeamInProgressFragment : BaseFragment<FragmentInProgressBinding>() {
    private lateinit var adapter: TeamInProgressAdapter
    override val TAG = "InProgressFragment"

    override fun getViewBinding(): FragmentInProgressBinding {
        return FragmentInProgressBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        adapter = TeamInProgressAdapter(emptyList())
        binding.recyclerInProgress.adapter = adapter
    }
}