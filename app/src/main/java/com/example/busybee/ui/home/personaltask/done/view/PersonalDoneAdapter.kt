package com.example.busybee.ui.home.personaltask.done.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.busybee.base.BaseAdapter
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.databinding.ItemTaskBinding
import com.example.busybee.utils.DateTimeUtils

class PersonalDoneAdapter(
    personalToDoList: List<PersonalToDo>,
    private val listener: PersonalDoneTaskInteractionListener
) :
    BaseAdapter<PersonalToDo, ItemTaskBinding>(personalToDoList) {

    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemTaskBinding
        get() = ItemTaskBinding::inflate

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemTaskBinding>,
        position: Int,
        currentItem: PersonalToDo
    ) {
        with(holder.binding) {
            val (formattedTime, formattedDate) = DateTimeUtils.formatDateTime(
                currentItem.creationTime ?: ""
            )
            textTaskName.text = currentItem.title
            textTaskDescription.text = currentItem.description
            textTaskTime.text = formattedTime
            textTaskDate.text = formattedDate
            textUserName.visibility = View.GONE
            taskCard.setOnClickListener {
                listener.onTasKClicked(1, currentItem)

            }
        }
    }

    interface PersonalDoneTaskInteractionListener {
        fun onTasKClicked(flag: Int, personalToDo: PersonalToDo)
    }
}