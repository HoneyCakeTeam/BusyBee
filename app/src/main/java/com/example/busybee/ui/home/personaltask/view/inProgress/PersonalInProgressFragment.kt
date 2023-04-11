package com.example.busybee.ui.home.personaltask.view.inProgress

import android.os.Bundle
import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentPersonalInProgressBinding
import com.example.busybee.domain.models.PersonalTodos
import com.example.busybee.ui.home.personaltask.view.done.PersonalDoneFragment

class PersonalInProgressFragment : BaseFragment<FragmentPersonalInProgressBinding>() {
    private lateinit var adapter: PersonalInProgressAdapter
    private lateinit var done: PersonalTodos
    override val TAG = this::class.java.simpleName.toString()

    override fun getViewBinding(): FragmentPersonalInProgressBinding {
        return FragmentPersonalInProgressBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getDons()
        adapter = PersonalInProgressAdapter(done.values)
        binding.recyclerInProgress.adapter = adapter
        binding.headerInProgress.textTodoStatus.text="InProgress"
        binding.headerInProgress.taskCount.text="${done.values.size} Tasks"

    }

    private fun getDons() {
        arguments?.let {
            done = it.getParcelable(PERSONAL_IN_PROGRESS_LIST)!!
        }
    }

    companion object {
        const val PERSONAL_IN_PROGRESS_LIST = "Personal_In_Progress_List"
        fun newInstance(tasks: PersonalTodos) =
            PersonalInProgressFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PERSONAL_IN_PROGRESS_LIST, tasks)
                }
            }
    }

}
