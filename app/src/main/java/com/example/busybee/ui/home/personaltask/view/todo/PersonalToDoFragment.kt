package com.example.busybee.ui.home.personaltask.view.todo

import android.os.Build
import android.os.Bundle
import android.view.View
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.databinding.BottomSheetCreateTaskBinding
import com.example.busybee.databinding.FragmentPersonalToDoBinding
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
    private lateinit var todos: List<PersonalToDo>
    private lateinit var bottomSheet: BottomSheetDialog
    private lateinit var sheetCreateTaskBinding: BottomSheetCreateTaskBinding
    override val TAG = this::class.java.simpleName.toString()
    private val presenter: PersonalPresenterInterface by lazy {
        PersonalPresenter(RemoteDataSource(requireContext()))
    }

    override fun getViewBinding(): FragmentPersonalToDoBinding {
        return FragmentPersonalToDoBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getDons()
        addCallBacks()
        adapter = PersonalToDoAdapter(todos, this)
        binding.recyclerToDo.adapter = adapter
        binding.headerToDo.textTodoStatus.text = getString(R.string.to_do)
        binding.headerToDo.taskCount.text = getString(R.string.tasks, todos.size)
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
            todos = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelableArrayList(PERSONAL_TODO_LIST, PersonalToDo::class.java)!!
            } else {
                it.getParcelableArrayList(PERSONAL_TODO_LIST)!!
            }
        }
    }


    override fun personalCreateToDo(title: String, description: String) {
        presenter.personalCreateToDo(
            title, description,
            ::onSuccessResponse, ::onFailureResponse
        )
    }

    override fun onSuccessResponse(response: BaseResponse<PersonalToDo>) {
        activity?.runOnUiThread {
            setListAndUpdateUi(response)
            hideFieldsAndShowDone()

        }
    }

    private fun setListAndUpdateUi(response: BaseResponse<PersonalToDo>) {
        val newTask = response.value
        todos = todos.toMutableList().apply { add(newTask!!) }
        adapter.setItems(todos)
        binding.headerToDo.taskCount.text = getString(R.string.tasks, todos.size)
        binding.headerToDo.textTodoStatus.setBackgroundResource(R.drawable.shape_todo)
    }

    private fun hideFieldsAndShowDone() {
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

    override fun onTasKClicked(flag: Int, personalToDo: PersonalToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, null, personalToDo)
        replaceFragment(detailsFragment)
    }

    companion object {
        const val PERSONAL_TODO_LIST = "Personal_Todo_List"
        fun newInstance(tasks: ArrayList<PersonalToDo>) =
            PersonalToDoFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(PERSONAL_TODO_LIST, tasks)
                }
            }
    }


}
