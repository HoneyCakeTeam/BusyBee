package com.example.busybee.ui.home.personalTask.toDoTask


import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentPersonalToDoBinding

class PersonalToDoFragment (): BaseFragment<FragmentPersonalToDoBinding>() {
        private lateinit var adapter: PersonalToDoAdapter
        override val TAG = "ToDoFragment"

        override fun getViewBinding(): FragmentPersonalToDoBinding {
            return FragmentPersonalToDoBinding.inflate(layoutInflater)
        }

        override fun setUp() {
            adapter = PersonalToDoAdapter(emptyList())
            binding.recyclerToDo.adapter = adapter
        }
    }
