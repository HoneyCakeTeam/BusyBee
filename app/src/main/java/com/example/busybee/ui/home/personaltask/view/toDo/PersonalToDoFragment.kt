package com.example.busybee.ui.home.personaltask.view.toDo

import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.BottomSheetCreateTaskBinding
import com.example.busybee.databinding.FragmentPersonalToDoBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class PersonalToDoFragment() : BaseFragment<FragmentPersonalToDoBinding>() {
    private lateinit var adapter: PersonalToDoAdapter
    override val TAG = this::class.java.simpleName.toString()

    override fun getViewBinding(): FragmentPersonalToDoBinding {
        return FragmentPersonalToDoBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        addCallBacks()
        adapter = PersonalToDoAdapter(emptyList())
        binding.recyclerToDo.adapter = adapter
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

}
