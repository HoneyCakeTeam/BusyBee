package com.example.busybee.ui.home.view.teamTask.view.toDo.view

import android.view.View
import android.widget.Toast
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.TeamToDoListResponse
import com.example.busybee.databinding.BottomSheetCreateTaskBinding
import com.example.busybee.databinding.FragmentTeamToDoBinding
import com.example.busybee.ui.home.view.teamTask.view.toDo.presenter.TeamToDoPresenter
import com.example.busybee.ui.home.view.teamTask.view.toDo.presenter.TeamToDoPresenterInterface
import com.example.busybee.ui.login.presenter.LoginPresenter
import com.example.busybee.ui.login.presenter.LoginPresenterInterface
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class TeamToDoFragment : BaseFragment<FragmentTeamToDoBinding>(), TeamToDoViewInterface {
    private lateinit var adapter: TeamToDoAdapter
    val binding_ = BottomSheetCreateTaskBinding.inflate(layoutInflater)

    private val presenter: TeamToDoPresenterInterface by lazy {
        TeamToDoPresenter(Repository(requireContext()))
    }

    override val TAG = this::class.java.simpleName.toString()
    override fun getViewBinding(): FragmentTeamToDoBinding {
        return FragmentTeamToDoBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        adapter = TeamToDoAdapter(emptyList())
        binding.recyclerToDo.adapter = adapter
        addCallBacks()
    }

    private fun addCallBacks() {
        binding.buttonAddNewTeamTask.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {
        val bottomSheet = BottomSheetDialog(
            requireContext(),
            R.style.Theme_Design_BottomSheetDialog
        )

        binding_.buttonCancel.setOnClickListener {
            bottomSheet.dismiss()
        }
        binding_.buttonCreateTask.setOnClickListener {
            val title = binding_.textTaskName.text.toString()
            val description = binding_.textContent.text.toString()
            val assign = binding_.textAssignee.text.toString()
            teamCreateToDo(title, description, assign)
        }
        bottomSheet.setContentView(binding.root)
        bottomSheet.show()
    }

    override fun teamCreateToDo(title: String, description: String, assignee: String) {
        presenter.teamCreateToDo<TeamToDoListResponse>(
            title, description, assignee,
            ::onSuccessResponse, ::onFailureResponse
        )
    }

    override fun onSuccessResponse(response: TeamToDoListResponse) {
        activity?.runOnUiThread {
            binding_.lottieCreatedSuccessfully.visibility = View.VISIBLE
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
    }