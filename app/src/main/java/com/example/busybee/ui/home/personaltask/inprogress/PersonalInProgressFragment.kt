package com.example.busybee.ui.home.personaltask.inprogress

import android.app.UiModeManager
import android.content.Context
import android.view.View
import com.example.busybee.R
import com.example.busybee.ui.base.BaseFragment
import com.example.busybee.data.RepositoryImp
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.data.source.RemoteDataSourceImp
import com.example.busybee.databinding.FragmentPersonalInProgressBinding
import com.example.busybee.ui.details.DetailsFragment
import com.example.busybee.ui.home.teamtask.inprogress.TeamInProgressPresenter
import com.example.busybee.utils.sharedpreference.SharedPreferencesUtils
import com.example.busybee.utils.TaskType
import com.example.busybee.utils.replaceFragment
import com.example.busybee.utils.sharedpreference.SharedPreferencesInterface

class PersonalInProgressFragment : BaseFragment<FragmentPersonalInProgressBinding>(),
    PersonalInProgressAdapter.PersonalInProgressTaskInteractionListener,
    PersonalInProgressView {
    private lateinit var adapter: PersonalInProgressAdapter
    private lateinit var inProgress: List<PersonalToDo>
    private val sharedPreferences: SharedPreferencesInterface by lazy {
        SharedPreferencesUtils(
            requireContext()
        )
    }
    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSourceImp(requireContext())
    }
    private val presenter by lazy {
        PersonalInProgressPresenter(
            RepositoryImp(
                remoteDataSource,
                sharedPreferences
            ), this
        )
    }

    override val TAG = this::class.java.simpleName.toString()

    override fun getViewBinding(): FragmentPersonalInProgressBinding {
        return FragmentPersonalInProgressBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        presenter.getLocalPersonalInProgress()
        adapter = PersonalInProgressAdapter(inProgress, this)
        binding.recyclerInProgress.adapter = adapter
        binding.headerInProgress.textTodoStatus.text = getString(R.string.in_progress)
        binding.headerInProgress.taskCount.text = getString(R.string.tasks, inProgress.size)
        setToDoColorBasedOnTheme()
        showPlaceHolder(inProgress)
    }

    private fun showPlaceHolder(todo: List<PersonalToDo>) {
        if (todo.isEmpty()) {
            binding.textNoTasksPersonalInProgress.visibility = View.VISIBLE
            binding.recyclerInProgress.visibility = View.GONE
            binding.imagePlaceholderPersonalInProgress.visibility = View.VISIBLE
        } else {
            binding.textNoTasksPersonalInProgress.visibility = View.GONE
            binding.recyclerInProgress.visibility = View.VISIBLE
            binding.imagePlaceholderPersonalInProgress.visibility = View.GONE
        }
    }

    private fun setToDoColorBasedOnTheme() {
        val uiManager = requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        when (uiManager.nightMode) {
            UiModeManager.MODE_NIGHT_NO -> {
                binding.headerInProgress.textTodoStatus.setBackgroundResource(R.drawable.shape_inprogress)
            }
            UiModeManager.MODE_NIGHT_YES -> {
                binding.headerInProgress.textTodoStatus.setBackgroundResource(R.drawable.shape_inprogress_dark)
            }
            else -> {
                binding.headerInProgress.textTodoStatus.setBackgroundResource(R.drawable.shape_inprogress)
            }
        }
    }

    override fun onTasKClicked(flag: TaskType, personalToDo: PersonalToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, null, personalToDo)
        replaceFragment(detailsFragment)
    }

    override fun getLocalPersonalInProgress(inProgress: List<PersonalToDo>) {
        this.inProgress = inProgress
    }

}
