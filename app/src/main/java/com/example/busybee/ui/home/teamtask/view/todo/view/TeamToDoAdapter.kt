package com.example.busybee.ui.home.teamtask.view.todo.view

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.busybee.base.BaseAdapter
import com.example.busybee.data.models.PersonalTodo
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.databinding.ItemTaskBinding
import com.example.busybee.utils.DateTimeUtils

class TeamToDoAdapter(teamToDoList: List<TeamToDo>,
    private val listener:TeamToDoTaskInteractionListener
    ) :
    BaseAdapter<TeamToDo, ItemTaskBinding>(teamToDoList){
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemTaskBinding
        get() = ItemTaskBinding::inflate

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemTaskBinding>,
        position: Int,
        currentItem: TeamToDo,
    ) {
        with(holder.binding) {
            val (formattedTime, formattedDate) = DateTimeUtils.formatDateTime(
                currentItem.creationTime ?: ""
            )
            textTaskName.text = currentItem.title
            textTaskDescription.text = currentItem.description
            textTaskTime.text = currentItem.creationTime
            textUserName.text = currentItem.assignee
            textTaskTime.text = formattedTime
            textTaskDate.text = formattedDate
            taskCard.setOnClickListener{
                listener.onTasKClicked(0,currentItem)
            }
        }

    }

    interface TeamToDoTaskInteractionListener{
        fun onTasKClicked(flag :Int, teamTodo: TeamToDo)
    }

}