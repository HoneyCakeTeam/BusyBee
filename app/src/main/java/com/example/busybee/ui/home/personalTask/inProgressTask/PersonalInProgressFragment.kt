package com.example.busybee.ui.home.personalTask.inProgressTask

import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentPersonalInProgressBinding
import com.example.busybee.ui.home.personalTask.inProgressTask.PersonalInProgressAdapter

class PersonalInProgressFragment : BaseFragment<FragmentPersonalInProgressBinding>() {
    private lateinit var adapter : PersonalInProgressAdapter
    override val TAG = "InProgressFragment"

    override fun getViewBinding(): FragmentPersonalInProgressBinding {
        return FragmentPersonalInProgressBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        adapter = PersonalInProgressAdapter(emptyList())
        binding.recyclerInProgress.adapter = adapter
    }
}