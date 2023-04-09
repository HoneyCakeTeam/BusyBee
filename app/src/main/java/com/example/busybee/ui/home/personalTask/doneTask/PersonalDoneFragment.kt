package com.example.busybee.ui.home.personalTask.doneTask

import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentDonePersonalBinding
import com.example.busybee.ui.home.personalTask.done.DoneAdapter


class PersonalDoneFragment : BaseFragment<FragmentDonePersonalBinding>() {
    private lateinit var adapter : DoneAdapter
    override val TAG = "DoneFragment"

    override fun getViewBinding(): FragmentDonePersonalBinding {
        return FragmentDonePersonalBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        adapter= DoneAdapter(emptyList())
        binding.recyclerDone.adapter = adapter
    }

}