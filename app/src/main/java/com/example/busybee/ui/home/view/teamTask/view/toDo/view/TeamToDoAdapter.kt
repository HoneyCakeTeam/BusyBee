package com.example.busybee.ui.home.view.teamTask.view.toDo.view

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.busybee.base.BaseAdapter
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.databinding.ItemTaskBinding

class TeamToDoAdapter(private var teamToDoList: List<TeamToDo>) :
    BaseAdapter<TeamToDo, ItemTaskBinding>(teamToDoList) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemTaskBinding
        get() = ItemTaskBinding::inflate

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemTaskBinding>,
        position: Int,
        currentItem: TeamToDo,
    ) {
        with(holder.binding) {
            textTaskName.text = currentItem.title
            textTaskDescription.text = currentItem.description
            textTaskTime.text = currentItem.creationTime
        }


    }

}