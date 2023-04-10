package com.example.busybee.ui.home.view.personalTask.view.inProgressTask

import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.PersonalGetToDoListResponse
import com.example.busybee.data.models.PersonalTodo
import com.example.busybee.databinding.FragmentPersonalInProgressBinding
import com.example.busybee.ui.home.view.personalTask.view.PersonalTasksViewPresenter
import com.example.busybee.ui.home.view.personalTask.view.presenter.PersonalTasksPresenter
import com.example.busybee.ui.home.view.personalTask.view.presenter.PersonalTasksPresenterInterface


class PersonalInProgressFragment : BaseFragment<FragmentPersonalInProgressBinding>(),
PersonalTasksViewPresenter {
    private lateinit var adapter: PersonalInProgressAdapter
    override val TAG = this::class.java.simpleName.toString()

    private val presenter: PersonalTasksPresenterInterface by lazy {
        PersonalTasksPresenter(
            Repository(requireContext())
        )
    }

    override fun getViewBinding(): FragmentPersonalInProgressBinding {
        return FragmentPersonalInProgressBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getInProgressTasks()
    }

    private fun getInProgressTasks() {
        presenter.getPersonalTasks(::onSuccessResponse, ::onFailureResponse)
    }

    override fun onSuccessResponse(response: PersonalGetToDoListResponse) {
        requireActivity().runOnUiThread {
            val personalInProgressTasks = getPersonalInProgressTasks(response.value)
            initiateAdapter(personalInProgressTasks)
            setHeader(personalInProgressTasks)
        }
    }

    private fun setHeader(personalInProgressTasks: List<PersonalTodo>) {
        binding.headerInProgress.taskCount.text =
            getString(R.string.tasks, personalInProgressTasks.size)
        binding.headerInProgress.todoSection.setBackgroundResource(R.drawable.shape_section_orange)

    }
    private fun getPersonalInProgressTasks(personalTasksList: List<PersonalTodo>): List<PersonalTodo>
        = personalTasksList.filter { it.status == 1 }


    private fun initiateAdapter(personalTasksList: List<PersonalTodo>) {
        adapter = PersonalInProgressAdapter(personalTasksList)
        binding.recyclerInProgress.adapter = adapter
    }

    override fun onFailureResponse(error: Throwable) {
        log("Failed: ${error.message}")
    }
}
