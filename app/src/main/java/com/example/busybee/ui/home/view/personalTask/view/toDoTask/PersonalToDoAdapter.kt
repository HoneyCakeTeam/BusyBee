package com.example.busybee.ui.home.view.personalTask.view.toDoTask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.busybee.R
import com.example.busybee.base.BaseAdapter
import com.example.busybee.data.models.PersonalTodo
import com.example.busybee.databinding.ItemTaskBinding

class PersonalToDoAdapter(personalToDoList: List<PersonalTodo>) :
    BaseAdapter<PersonalTodo, ItemTaskBinding>(personalToDoList) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemTaskBinding
        get() = ItemTaskBinding::inflate

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemTaskBinding>,
        position: Int,
        currentItem: PersonalTodo
    ) {
        with(holder.binding) {
            textTaskName.text = currentItem.title
            textTaskDescription.text = currentItem.description
            textTaskTime.text = currentItem.creationTime
            textUserName.visibility = View.GONE
        }
        }
    }
