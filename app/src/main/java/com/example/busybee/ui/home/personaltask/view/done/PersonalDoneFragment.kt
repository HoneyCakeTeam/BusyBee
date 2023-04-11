package com.example.busybee.ui.home.personaltask.view.done

import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentDonePersonalBinding
import com.example.busybee.domain.models.TeamTodos

class PersonalDoneFragment : BaseFragment<FragmentDonePersonalBinding>() {
    private lateinit var adapter: DoneAdapter
    override val TAG = this::class.java.simpleName.toString()

    override fun getViewBinding(): FragmentDonePersonalBinding {
        return FragmentDonePersonalBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        adapter = DoneAdapter(emptyList())
        binding.recyclerDone.adapter = adapter
    }

}