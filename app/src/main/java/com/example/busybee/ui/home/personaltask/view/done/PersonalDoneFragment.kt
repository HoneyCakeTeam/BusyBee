package com.example.busybee.ui.home.personaltask.view.done

import android.os.Bundle
import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentDonePersonalBinding
import com.example.busybee.domain.models.PersonalTodos

class PersonalDoneFragment : BaseFragment<FragmentDonePersonalBinding>() {
    private lateinit var adapter: DoneAdapter
    private lateinit var done: PersonalTodos

    override val TAG = this::class.java.simpleName.toString()

    override fun getViewBinding(): FragmentDonePersonalBinding {
        return FragmentDonePersonalBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getDons()
        adapter = DoneAdapter(done.values)
        binding.recyclerDone.adapter = adapter
        binding.headerDone.textTodoStatus.text="Done"
        binding.headerDone.taskCount.text="${done.values.size} Tasks"

    }

    private fun getDons() {
        arguments?.let {
            done = it.getParcelable(PERSONAL_DONE_LIST)!!
        }
    }

    companion object {
        const val PERSONAL_DONE_LIST = "Personal_Done_List"
        fun newInstance(tasks: PersonalTodos) =
            PersonalDoneFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PERSONAL_DONE_LIST, tasks)
                }
            }
    }

}