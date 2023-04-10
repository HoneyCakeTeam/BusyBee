package com.example.busybee.ui.home.view.personalTask.view.doneTask

import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.PersonalGetToDoListResponse
import com.example.busybee.databinding.FragmentDonePersonalBinding
import com.example.busybee.ui.home.view.personalTask.view.PersonalTasksViewPresenter
import com.example.busybee.ui.home.view.personalTask.view.presenter.PersonalTasksPresenter
import com.example.busybee.ui.home.view.personalTask.view.presenter.PersonalTasksPresenterInterface


class PersonalDoneFragment : BaseFragment<FragmentDonePersonalBinding>() ,PersonalTasksViewPresenter{
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
        adapter = DoneAdapter(emptyList())
        binding.recyclerDone.adapter = adapter
    }

    private fun getDoneTasks() {
        presenter.getPersonalTasks(::onSuccessResponse,::onFailureResponse)
    }

    override fun onSuccessResponse(response: PersonalGetToDoListResponse) {

    }

    override fun onFailureResponse(error: Throwable) {

    }

}