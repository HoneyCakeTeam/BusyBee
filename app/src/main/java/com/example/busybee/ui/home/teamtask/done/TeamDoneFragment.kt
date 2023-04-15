package com.example.busybee.ui.home.teamtask.done

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.databinding.FragmentTeamDoneBinding
import com.example.busybee.ui.details.view.DetailsFragment
import com.example.busybee.utils.replaceFragment

class TeamDoneFragment : BaseFragment<FragmentTeamDoneBinding>(),
    TeamDoneAdapter.TeamDoneTaskInteractionListener {
    private lateinit var adapter: TeamDoneAdapter
    override val TAG = this::class.java.simpleName.toString()
    private lateinit var done: List<TeamToDo>

    override fun getViewBinding(): FragmentTeamDoneBinding {
        return FragmentTeamDoneBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getDons()
        adapter = TeamDoneAdapter(done , this)
        Log.e(TAG, "setUp: ${done}")
        binding.recyclerDone.adapter = adapter
        binding.taskHeader.textTodoStatus.text = getString(R.string.done)
        binding.taskHeader.taskCount.text = getString(R.string.tasks, done.size)
        setToDoColorBasedOnTheme()
    }
    private fun setToDoColorBasedOnTheme(){
        val uiManager = requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        when (uiManager.nightMode) {
            UiModeManager.MODE_NIGHT_NO -> {
                binding.taskHeader.textTodoStatus.setBackgroundResource(R.drawable.shape_done)
            }
            UiModeManager.MODE_NIGHT_YES -> {
                binding.taskHeader.textTodoStatus.setBackgroundResource(R.drawable.shape_done_dark)
            }
            else -> {
                binding.taskHeader.textTodoStatus.setBackgroundResource(R.drawable.shape_done)
            }
        }
    }

    private fun getDons() {
        arguments?.let {
            done = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelableArrayList(TEAM_DONE_LIST,TeamToDo::class.java)!!
            }else{
                it.getParcelableArrayList(TEAM_DONE_LIST)!!
            }
        }
    }

    companion object {
        const val TEAM_DONE_LIST = "Team_Done_List"
        fun newInstance(tasks: ArrayList<TeamToDo>) =
            TeamDoneFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(TEAM_DONE_LIST, tasks)
                }
            }
    }

    override fun onTasKClicked(flag: Int, teamTodo: TeamToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, teamTodo, null)
        replaceFragment(detailsFragment)
    }
}