package com.example.busybee.ui.home.teamTask.done

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.busybee.base.BaseAdapter
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.databinding.TaskItemBinding

class TeamDoneAdapter(private var teamDoneList: List<TeamToDo>) :
    BaseAdapter<TeamToDo, TaskItemBinding>(teamDoneList) {
    override val bindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> TaskItemBinding
        get() = TaskItemBinding::inflate

    override fun onBindViewHolder(
        holder: BaseViewHolder<TaskItemBinding>,
        position: Int,
        currentItem: TeamToDo,
    ) {
        holder.binding.apply {
            textTaskName.text = currentItem.title
            textTaskDescription.text = currentItem.description
            textTaskTime.text = currentItem.creationTime
        }

    }
}