package com.example.busybee.ui.home.teamtask.done

import android.app.UiModeManager
import android.content.Context
import android.view.View
import com.example.busybee.R
import com.example.busybee.ui.base.BaseFragment
import com.example.busybee.data.RepositoryImp
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.data.source.RemoteDataSourceImp
import com.example.busybee.databinding.FragmentTeamDoneBinding
import com.example.busybee.ui.details.DetailsFragment
import com.example.busybee.utils.sharedpreference.SharedPreferencesUtils
import com.example.busybee.utils.TaskType
import com.example.busybee.utils.replaceFragment

class TeamDoneFragment : BaseFragment<FragmentTeamDoneBinding>(),
    TeamDoneAdapter.TeamDoneTaskInteractionListener, TeamDoneView {
    private lateinit var adapter: TeamDoneAdapter
    override val TAG = this::class.java.simpleName.toString()
    private lateinit var done: List<TeamToDo>

    private val presenter by lazy {
        TeamDonePresenter(
            RepositoryImp(
                RemoteDataSourceImp(requireContext()),
                SharedPreferencesUtils(requireContext())
            ), this
        )
    }

    override fun getViewBinding(): FragmentTeamDoneBinding {
        return FragmentTeamDoneBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        presenter.getLocalTeamDones()
        adapter = TeamDoneAdapter(done, this)
        binding.recyclerDone.adapter = adapter
        binding.taskHeader.textTodoStatus.text = getString(R.string.done)
        binding.taskHeader.taskCount.text = getString(R.string.tasks, done.size)
        setToDoColorBasedOnTheme()
        showPlaceHolder(done)
    }

    private fun showPlaceHolder(done: List<TeamToDo>) {
        if (done.isEmpty()) {
            binding.textNoTasksTeamDone.visibility = View.VISIBLE
            binding.recyclerDone.visibility = View.GONE
            binding.imagePlaceholderTeamDone.visibility = View.VISIBLE
        } else {
            binding.textNoTasksTeamDone.visibility = View.GONE
            binding.recyclerDone.visibility = View.VISIBLE
            binding.imagePlaceholderTeamDone.visibility = View.GONE
        }
    }

    private fun setToDoColorBasedOnTheme() {
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

    override fun onTasKClicked(flag: TaskType, teamTodo: TeamToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, teamTodo, null)
        replaceFragment(detailsFragment)
    }

    override fun getLocalTeamDones(dones: List<TeamToDo>) {
        this.done = dones
    }

}