package com.example.busybee.ui.home.teamtask.inprogress.view

import android.app.UiModeManager
import android.content.Context
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.databinding.FragmentTeamInProgressBinding
import com.example.busybee.ui.details.view.DetailsFragment
import com.example.busybee.ui.home.teamtask.inprogress.presenter.TeamInProgressPresenter
import com.example.busybee.utils.SharedPreferencesUtils
import com.example.busybee.utils.TaskType
import com.example.busybee.utils.replaceFragment

class TeamInProgressFragment : BaseFragment<FragmentTeamInProgressBinding>(),
    TeamInProgressAdapter.TeamInProgressTaskInteractionListener, TeamInprogressViewInterface {
    private lateinit var adapter: TeamInProgressAdapter
    override val TAG = this::class.java.simpleName.toString()
    private lateinit var inProgress: List<TeamToDo>

    private val presenter by lazy {
        TeamInProgressPresenter(
            Repository(
                RemoteDataSource(requireContext()),
                SharedPreferencesUtils(requireContext())
            ), this
        )
    }

    override fun getViewBinding(): FragmentTeamInProgressBinding {
        return FragmentTeamInProgressBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        presenter.getLocalTeamInProgress()
        adapter = TeamInProgressAdapter(inProgress, this)
        binding.recyclerInProgress.adapter = adapter
        binding.taskHeader.textTodoStatus.text = getString(R.string.in_progress)
        binding.taskHeader.taskCount.text = getString(R.string.tasks, inProgress.size)
        setToDoColorBasedOnTheme()
    }

    private fun setToDoColorBasedOnTheme() {
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

    override fun onTasKClicked(flag: TaskType, teamTodo: TeamToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, teamTodo, null)
        replaceFragment(detailsFragment)
    }

    override fun getLocalTeamInProgress(inProgress: List<TeamToDo>) {
        this.inProgress = inProgress
    }
}