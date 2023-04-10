package com.example.busybee.ui.home.view.personalTask.view.doneTask

import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.PersonalGetToDoListResponse
import com.example.busybee.data.models.PersonalTodo
import com.example.busybee.databinding.FragmentDonePersonalBinding
import com.example.busybee.ui.home.view.personalTask.view.PersonalTasksViewPresenter
import com.example.busybee.ui.home.view.personalTask.view.presenter.PersonalTasksPresenter
import com.example.busybee.ui.home.view.personalTask.view.presenter.PersonalTasksPresenterInterface


class PersonalDoneFragment : BaseFragment<FragmentDonePersonalBinding>(),
    PersonalTasksViewPresenter {
    private lateinit var adapter: DoneAdapter
    override val TAG = this::class.java.simpleName.toString()

    private val presenter: PersonalTasksPresenterInterface by lazy {
        PersonalTasksPresenter(
            Repository(requireContext())
        )
    }

    override fun getViewBinding(): FragmentDonePersonalBinding {
        return FragmentDonePersonalBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getDoneTasks()
    }

    private fun getDoneTasks() {
        presenter.getPersonalTasks(::onSuccessResponse, ::onFailureResponse)
    }

    override fun onSuccessResponse(response: PersonalGetToDoListResponse) {
        requireActivity().runOnUiThread{
            val personalDoneTasks = getPersonalCountDoneTasks(response.value)
            initAdapter(personalDoneTasks)
            setHeader(personalDoneTasks)
        }
    }

    private fun setHeader(personalDoneTasks: List<PersonalTodo>) {
        binding.headerDone.taskCount.text = getString(R.string.tasks , personalDoneTasks.size)
        binding.headerDone.todoSection.setBackgroundResource(R.drawable.shape_section_orange)
    }
    private fun initAdapter(personalDoneTasks: List<PersonalTodo>) {
        adapter = DoneAdapter(personalDoneTasks)
        binding.recyclerDone.adapter = adapter
    }

    private fun getPersonalCountDoneTasks(personalTaskList : List<PersonalTodo>):List<PersonalTodo> =
        personalTaskList.filter { it.status == 2 }

    override fun onFailureResponse(error: Throwable) {
        log("Failed: ${error.message}")
    }

}