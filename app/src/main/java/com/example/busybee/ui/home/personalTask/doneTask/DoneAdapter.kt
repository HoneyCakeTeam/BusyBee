package com.example.busybee.ui.home.personalTask.doneTask

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.busybee.base.BaseAdapter
import com.example.busybee.data.models.PersonalTodo
import com.example.busybee.databinding.TaskItemBinding

class DoneAdapter(private var personalToDoList: List<PersonalTodo>)
    :BaseAdapter<PersonalTodo , TaskItemBinding>(personalToDoList) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> TaskItemBinding
        get() = TaskItemBinding::inflate

    override fun onBindViewHolder(
        holder: BaseViewHolder<TaskItemBinding>,
        position: Int,
        currentItem: PersonalTodo
    ) {
        holder.binding.apply {
            textTaskName.text = currentItem.title
            textTaskDescription.text = currentItem.description
            textTaskTime.text = currentItem.creationTime
        }
    }
}