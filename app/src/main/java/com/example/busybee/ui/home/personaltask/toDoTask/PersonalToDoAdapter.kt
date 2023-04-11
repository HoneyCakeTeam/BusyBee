package com.example.busybee.ui.home.personaltask.toDoTask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.busybee.base.BaseAdapter
import com.example.busybee.data.models.PersonalTodo
import com.example.busybee.databinding.ItemTaskBinding
import com.example.busybee.utils.DateTimeUtils
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

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
            val (formattedTime, formattedDate) = DateTimeUtils.formatDateTime(currentItem.creationTime ?: "")
            textTaskName.text = currentItem.title
            textTaskDescription.text = currentItem.description
            textTaskTime.text = formattedTime
            textTaskDate.text = formattedDate
            textUserName.visibility = View.GONE
        }
    }
}
