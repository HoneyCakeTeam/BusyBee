package com.example.busybee.ui.home.personaltask.view.todo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.PersonalCreateToDoResponse
import com.example.busybee.data.models.PersonalTodo
import com.example.busybee.databinding.BottomSheetCreateTaskBinding
import com.example.busybee.databinding.FragmentPersonalToDoBinding
import com.example.busybee.domain.models.PersonalTodos
import com.example.busybee.ui.details.view.DetailsFragment
import com.example.busybee.ui.home.personaltask.presenter.PersonalPresenter
import com.example.busybee.ui.home.personaltask.presenter.PersonalPresenterInterface
import com.example.busybee.utils.replaceFragment
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class PersonalToDoFragment() : BaseFragment<FragmentPersonalToDoBinding>(),
    PersonalToDoViewInterface,
    PersonalToDoAdapter.PersonalToDoTaskInteractionListener {
    private lateinit var adapter: PersonalToDoAdapter
    private lateinit var done: PersonalTodos
    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var sheetCreateTaskBinding: BottomSheetCreateTaskBinding
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
        adapter = PersonalToDoAdapter(done.values, this)
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
        sheetCreateTaskBinding = BottomSheetCreateTaskBinding.inflate(layoutInflater)
        sheetCreateTaskBinding.inputLayoutAssignee.visibility = View.GONE
        bottomSheet = BottomSheetDialog(
            requireContext(),
            com.google.android.material.R.style.Theme_Design_BottomSheetDialog
        )

        sheetCreateTaskBinding.buttonCreateTask.setOnClickListener {
            val title = sheetCreateTaskBinding.textTaskName.text.toString()
            val description = sheetCreateTaskBinding.textContent.text.toString()

            personalCreateToDo(title, description)
        }

        sheetCreateTaskBinding.buttonCancel.setOnClickListener {
            bottomSheet.dismiss()
        }
        bottomSheet.setContentView(sheetCreateTaskBinding.root)
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

    override fun onSuccessResponse(response: PersonalCreateToDoResponse) {
        activity?.runOnUiThread {

            val newTask = response.value
            done.values = done.values.toMutableList().apply { add(newTask!!) }
            adapter.setItems(done.values)
            binding.headerToDo.taskCount.text = "${done.values.size} Tasks"

            with(sheetCreateTaskBinding) {
                buttonCreateTask.text = getString(R.string.ok)
                textCreateTask.visibility = View.GONE
                inputLayoutAssignee.visibility = View.GONE
                inputLayoutContent.visibility = View.GONE
                inputLayoutTaskName.visibility = View.GONE
                textCreatedSuccessfully.visibility = View.VISIBLE
                buttonCreateTask.setOnClickListener {
                    bottomSheet.dismiss()
                }
            }
            sheetCreateTaskBinding.lottieCreatedSuccessfully.visibility = View.VISIBLE

            Toast.makeText(
                requireContext(),
                "success ${response.isSuccess} ",
                Toast.LENGTH_SHORT
            ).show()

        }
    }


    override fun onFailureResponse(error: Throwable) {
        activity?.runOnUiThread {
            Snackbar.make(
                binding.root,
                "Try Again! ${error.message} ",
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onTasKClicked(flag: Int, personalToDo: PersonalTodo) {
        val detailsFragment = DetailsFragment.newInstance(flag, null, personalToDo)
        replaceFragment(detailsFragment)
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
