package com.example.busybee.ui.home.teamtask.view.done

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.busybee.base.BaseAdapter
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.databinding.ItemTaskBinding

class TeamDoneAdapter(teamDoneList: List<TeamToDo>) :
    BaseAdapter<TeamToDo, ItemTaskBinding>(teamDoneList) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> ItemTaskBinding
        get() = ItemTaskBinding::inflate

    override fun onBindViewHolder(
        holder: BaseViewHolder<ItemTaskBinding>,
        position: Int,
        currentItem: TeamToDo,
    ) {
        with(holder.binding) {
            {
                textTaskName.text = currentItem.title
                textTaskDescription.text = currentItem.description
                textTaskTime.text = currentItem.creationTime
            }

        }
    }
}