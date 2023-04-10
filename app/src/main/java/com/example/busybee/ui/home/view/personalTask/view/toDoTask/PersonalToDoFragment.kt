package com.example.busybee.ui.home.view.personalTask.view.toDoTask


import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.PersonalGetToDoListResponse
import com.example.busybee.data.models.PersonalTodo
import com.example.busybee.databinding.BottomSheetCreateTaskBinding
import com.example.busybee.databinding.FragmentPersonalToDoBinding
import com.example.busybee.ui.home.view.personalTask.view.PersonalTasksViewPresenter
import com.example.busybee.ui.home.view.personalTask.view.presenter.PersonalTasksPresenter
import com.example.busybee.ui.home.view.personalTask.view.presenter.PersonalTasksPresenterInterface
import com.example.busybee.ui.login.presenter.LoginPresenter
import com.example.busybee.ui.login.presenter.LoginPresenterInterface
import com.google.android.material.bottomsheet.BottomSheetDialog

class PersonalToDoFragment() : BaseFragment<FragmentPersonalToDoBinding>(),
    PersonalTasksViewPresenter {
    private lateinit var adapter: PersonalToDoAdapter
    override val TAG = this::class.java.simpleName.toString()

    private val presenter: PersonalTasksPresenterInterface by lazy {
        PersonalTasksPresenter(
            Repository(requireContext())
        )
    }

    override fun getViewBinding(): FragmentPersonalToDoBinding {
        return FragmentPersonalToDoBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        addCallBacks()
        getToDoTasks()
    }

    private fun getToDoTasks() {
        presenter.getPersonalTasks(::onSuccessResponse, ::onFailureResponse)
    }

    private fun addCallBacks() {
        binding.buttonAddNewPersonalTask.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val bottomSheet = BottomSheetDialog(
            requireContext(),
//            R.style.Theme_Design_BottomSheetDialog
        )
        val binding = BottomSheetCreateTaskBinding.inflate(layoutInflater)

        binding.buttonCancel.setOnClickListener {
            bottomSheet.dismiss()
        }
        bottomSheet.setContentView(binding.root)
        bottomSheet.show()
    }

    override fun onSuccessResponse(response: PersonalGetToDoListResponse) {
        log("Success : ${response.isSuccess}")
        requireActivity().runOnUiThread {
            val personalToDoTasks = getPersonalToDoTasks(response.value)
            initAdapter(personalToDoTasks)
            setHeader(personalToDoTasks)
        }
    }

    private fun setHeader(personalTasksList:List<PersonalTodo>) {
        binding.headerToDo.taskCount.text = getString(R.string.tasks , personalTasksList.size)
    }

    private fun getPersonalToDoTasks(personalTasksList:List<PersonalTodo>):List<PersonalTodo> =
        personalTasksList.filter { it.status == 0 }

    private fun initAdapter(personalTasksList:List<PersonalTodo>) {
        adapter = PersonalToDoAdapter(personalTasksList)
        binding.recyclerToDo.adapter = adapter
    }

    override fun onFailureResponse(error: Throwable) {
        log("Failed: ${error.message}")
    }
}
