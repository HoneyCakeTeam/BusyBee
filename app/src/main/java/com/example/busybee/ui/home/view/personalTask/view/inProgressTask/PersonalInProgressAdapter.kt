package com.example.busybee.ui.home.view.personalTask.view.inProgressTask

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.busybee.base.BaseAdapter
import com.example.busybee.data.models.PersonalTodo
import com.example.busybee.databinding.ItemTaskBinding
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class PersonalInProgressAdapter(private var personalToDoList: List<PersonalTodo>) :
    BaseAdapter<PersonalTodo, ItemTaskBinding>(personalToDoList) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemTaskBinding
        get() = ItemTaskBinding::inflate

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemTaskBinding>,
        position: Int,
        currentItem: PersonalTodo
    ) {
        with(holder.binding) {
            val inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS")
            val outputFormat = DateTimeFormatter.ofPattern("hh:mm a")
            val inputTime = currentItem.creationTime
            val dateTime = LocalDateTime.parse(inputTime, inputFormat)
            val formattedTime = dateTime.format(outputFormat)
            val formattedDate = dateTime.format(DateTimeFormatter.ofPattern("dd MMMM", Locale.US))

            textTaskName.text = currentItem.title
            textTaskDescription.text = currentItem.description
            textTaskTime.text = formattedTime
            textTaskDate.text = formattedDate
            textUserName.visibility = View.GONE
        }

    }
}
