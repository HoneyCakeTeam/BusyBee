package com.example.busybee.ui.home.personaltask.inprogress

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.busybee.ui.base.BaseAdapter
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.databinding.ItemTaskBinding
import com.example.busybee.utils.DateTimeUtils
import com.example.busybee.utils.TaskType

class PersonalInProgressAdapter(
    private var personalToDoList: List<PersonalToDo>,
    private val listener: PersonalInProgressTaskInteractionListener
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
                listener.onTasKClicked(TaskType.PERSONAL, currentItem)
            }
        }

    }

    interface PersonalInProgressTaskInteractionListener {
        fun onTasKClicked(flag: TaskType, personalToDo: PersonalToDo)
    }
}
