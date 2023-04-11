package com.example.busybee.ui.home.personaltask.view.toDo

import android.os.Bundle
import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.BottomSheetCreateTaskBinding
import com.example.busybee.databinding.FragmentPersonalToDoBinding
import com.example.busybee.domain.models.PersonalTodos
import com.example.busybee.ui.home.personaltask.view.done.PersonalDoneFragment
import com.example.busybee.ui.home.personaltask.view.inProgress.PersonalInProgressFragment
import com.google.android.material.bottomsheet.BottomSheetDialog

class PersonalToDoFragment() : BaseFragment<FragmentPersonalToDoBinding>() {
    private lateinit var adapter: PersonalToDoAdapter
    private lateinit var done: PersonalTodos

    override val TAG = this::class.java.simpleName.toString()

    override fun getViewBinding(): FragmentPersonalToDoBinding {
        return FragmentPersonalToDoBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getDons()
        addCallBacks()
        adapter = PersonalToDoAdapter(done.values)
        binding.recyclerToDo.adapter = adapter
//        binding.headerToDo.textTodoStatus.text="ToDo"
//        binding.headerToDo.taskCount.text="${done.values.size} Tasks"
    }

    private fun addCallBacks() {
        binding.buttonAddNewPersonalTask.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val bottomSheet = BottomSheetDialog(
            requireContext(),
            com.google.android.material.R.style.Theme_Design_BottomSheetDialog
        )
        val binding = BottomSheetCreateTaskBinding.inflate(layoutInflater)
        binding.buttonCancel.setOnClickListener {
            bottomSheet.dismiss()
        }
        bottomSheet.setContentView(binding.root)
        bottomSheet.show()
    }

    private fun getDons() {
        arguments?.let {
            done = it.getParcelable(PERSONAL_TODO_LIST)!!
        }
    }

    companion object {
        const val PERSONAL_TODO_LIST = "Personal_Todo_List"
        fun newInstance(tasks: PersonalTodos) =
            PersonalToDoFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PERSONAL_TODO_LIST, tasks)
                }
            }
    }

}
