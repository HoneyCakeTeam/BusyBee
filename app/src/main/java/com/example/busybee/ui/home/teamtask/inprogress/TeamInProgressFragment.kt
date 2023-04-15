package com.example.busybee.ui.home.teamtask.inprogress

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.databinding.FragmentTeamInProgressBinding
import com.example.busybee.ui.details.view.DetailsFragment
import com.example.busybee.utils.replaceFragment

class TeamInProgressFragment : BaseFragment<FragmentTeamInProgressBinding>(),
    TeamInProgressAdapter.TeamInProgressTaskInteractionListener {
    private lateinit var adapter: TeamInProgressAdapter
    override val TAG = this::class.java.simpleName.toString()
    private lateinit var inProgress: List<TeamToDo>

    override fun getViewBinding(): FragmentTeamInProgressBinding {
        return FragmentTeamInProgressBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getInProgress()
        adapter = TeamInProgressAdapter(inProgress, this)
        binding.recyclerInProgress.adapter = adapter
        binding.taskHeader.textTodoStatus.text = getString(R.string.in_progress)
        binding.taskHeader.taskCount.text = getString(R.string.tasks, inProgress.size)
        setToDoColorBasedOnTheme()
    }
    private fun setToDoColorBasedOnTheme(){
        val uiManager = requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        when (uiManager.nightMode) {
            UiModeManager.MODE_NIGHT_NO -> {
                binding.taskHeader.textTodoStatus.setBackgroundResource(R.drawable.shape_inprogress)
            }
            UiModeManager.MODE_NIGHT_YES -> {
                binding.taskHeader.textTodoStatus.setBackgroundResource(R.drawable.shape_inprogress_dark)
            }
            else -> {
                binding.taskHeader.textTodoStatus.setBackgroundResource(R.drawable.shape_inprogress)
            }
        }
    }

    private fun getInProgress() {

        arguments?.let {
            inProgress = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelableArrayList(TEAM_IN_PROGRESS_LIST, TeamToDo::class.java)!!
            } else {
                it.getParcelableArrayList(TEAM_IN_PROGRESS_LIST)!!
            }
        }
    }

    companion object {
        const val TEAM_IN_PROGRESS_LIST = "Team_InProgress_List"
        fun newInstance(tasks: ArrayList<TeamToDo>) =
            TeamInProgressFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(TEAM_IN_PROGRESS_LIST, tasks)
                }
            }
    }

    override fun onTasKClicked(flag: Int, teamTodo: TeamToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, teamTodo, null)
        replaceFragment(detailsFragment)
    }
}