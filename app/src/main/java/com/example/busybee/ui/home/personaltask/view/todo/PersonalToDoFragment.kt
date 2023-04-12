package com.example.busybee.ui.home.personaltask.view.todo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.PersonalCreateToDoResponse
import com.example.busybee.data.models.PersonalToDoListResponse
import com.example.busybee.databinding.BottomSheetCreateTaskBinding
import com.example.busybee.databinding.FragmentPersonalToDoBinding
import com.example.busybee.domain.models.PersonalTodos
import com.example.busybee.domain.models.TeamTodos
import com.example.busybee.ui.home.personaltask.presenter.PersonalPresenter
import com.example.busybee.ui.home.personaltask.presenter.PersonalPresenterInterface
import com.google.android.material.bottomsheet.BottomSheetDialog

class PersonalToDoFragment() : BaseFragment<FragmentPersonalToDoBinding>(),PersonalToDoViewInterface{
    private lateinit var adapter: PersonalToDoAdapter
    private lateinit var done: PersonalTodos

    override val TAG = this::class.java.simpleName.toString()
    private val presenter: PersonalPresenterInterface by lazy {
        PersonalPresenter(Repository(requireContext()))
    }
    override fun getViewBinding(): FragmentPersonalToDoBinding {
        return FragmentPersonalToDoBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getDons()
        addCallBacks()
        adapter = PersonalToDoAdapter(done.values)
        binding.recyclerToDo.adapter = adapter
        binding.headerToDo.textTodoStatus.text="ToDo"
        binding.headerToDo.taskCount.text="${done.values.size} Tasks"
    }

    private fun addCallBacks() {
        binding.buttonAddNewPersonalTask.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
         val binding = BottomSheetCreateTaskBinding.inflate(layoutInflater)
        binding.inputLayoutAssignee.visibility = View.GONE
        val bottomSheet = BottomSheetDialog(
            requireContext(),
            com.google.android.material.R.style.Theme_Design_BottomSheetDialog
        )

        binding.buttonCreateTask.setOnClickListener {
            val title = binding.textTaskName.text.toString()
            val description = binding.textContent.text.toString()

            personalCreateToDo(title, description)
        }

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


    override fun personalCreateToDo(title: String, description: String) {
        presenter.personalCreateToDo(title, description,
            ::onSuccessResponse, ::onFailureResponse)
    }

    override fun onSuccessResponse(response: PersonalToDoListResponse) {
        activity?.runOnUiThread {
           // _binding.lottieCreatedSuccessfully.visibility = View.VISIBLE
            Toast.makeText(
                requireContext(),
                "success ${response.isSuccess} ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun onFailureResponse(error: Throwable) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(),
                "Try Again! ${error.message} ",
                Toast.LENGTH_SHORT
            ).show()
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
