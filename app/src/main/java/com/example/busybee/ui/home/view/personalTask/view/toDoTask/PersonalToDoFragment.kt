package com.example.busybee.ui.home.view.personalTask.view.toDoTask


import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentPersonalToDoBinding
import com.example.busybee.ui.createTask.CreateNewTaskFragment

class PersonalToDoFragment() : BaseFragment<FragmentPersonalToDoBinding>() {
    private lateinit var adapter: PersonalToDoAdapter
    private val createNewTaskFragment = CreateNewTaskFragment()
    override val TAG = this::class.java.simpleName.toString()
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
            createNewTaskFragment.show(
                requireActivity().supportFragmentManager,
                createNewTaskFragment.tag
            )
        }
    }
}
