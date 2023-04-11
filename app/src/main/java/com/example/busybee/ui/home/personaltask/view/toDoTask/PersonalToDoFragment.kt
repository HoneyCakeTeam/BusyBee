package com.example.busybee.ui.home.personaltask.view.toDoTask


import android.view.View
import android.widget.Toast
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.PersonalCreateToDoResponse
import com.example.busybee.databinding.BottomSheetCreateTaskBinding
import com.example.busybee.databinding.FragmentPersonalToDoBinding
import com.example.busybee.ui.home.personaltask.presenter.PersonalPresenter
import com.example.busybee.ui.home.personaltask.presenter.PersonalPresenterInterface
import com.google.android.material.R
import com.google.android.material.bottomsheet.BottomSheetDialog

class PersonalToDoFragment : BaseFragment<FragmentPersonalToDoBinding>(),
    PersonalToDoViewInterface {

    private lateinit var adapter: PersonalToDoAdapter
    override val TAG = this::class.java.simpleName.toString()
    private val _binding = BottomSheetCreateTaskBinding.inflate(layoutInflater)

    private val presenter: PersonalPresenterInterface by lazy {
        PersonalPresenter(Repository(requireContext()))
    }


    override fun getViewBinding(): FragmentPersonalToDoBinding {
        return FragmentPersonalToDoBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        adapter = PersonalToDoAdapter(emptyList())
        binding.recyclerToDo.adapter = adapter

        addCallBacks()
    }

    private fun addCallBacks() {
        binding.buttonAddNewPersonalTask.setOnClickListener {
            showBottomSheet()
        }
    }

    private fun showBottomSheet() {

        _binding.inputLayoutAssignee.visibility = View.GONE
        val bottomSheet = BottomSheetDialog(
            requireContext(),
            R.style.Theme_Design_BottomSheetDialog
        )

        _binding.buttonCreateTask.setOnClickListener {
            val title = _binding.textTaskName.text.toString()
            val description = _binding.textContent.text.toString()

            personalCreateToDo(title, description)
        }

        _binding.buttonCancel.setOnClickListener {
            bottomSheet.dismiss()
        }
        bottomSheet.setContentView(binding.root)
        bottomSheet.show()
    }

    override fun personalCreateToDo(title: String, description: String) {
        presenter.personalCreateToDo(title, description, ::onSuccessResponse, ::onFailureResponse)
    }

    override fun onSuccessResponse(response: PersonalCreateToDoResponse) {
        activity?.runOnUiThread {
            _binding.lottieCreatedSuccessfully.visibility = View.VISIBLE
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
