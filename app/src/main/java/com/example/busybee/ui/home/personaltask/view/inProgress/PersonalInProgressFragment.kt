package com.example.busybee.ui.home.personaltask.view.inProgress

import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentPersonalInProgressBinding

class PersonalInProgressFragment : BaseFragment<FragmentPersonalInProgressBinding>() {
    private lateinit var adapter : PersonalInProgressAdapter
    override val TAG = this::class.java.simpleName.toString()
    override fun getViewBinding(): FragmentPersonalInProgressBinding {
        return FragmentPersonalInProgressBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        adapter = PersonalInProgressAdapter(emptyList())
        binding.recyclerInProgress.adapter = adapter
    }
}