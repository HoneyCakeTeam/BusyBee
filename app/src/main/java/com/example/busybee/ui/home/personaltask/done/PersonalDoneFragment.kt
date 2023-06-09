package com.example.busybee.ui.home.personaltask.done

import android.app.UiModeManager
import android.content.Context
import android.view.View
import com.example.busybee.R
import com.example.busybee.ui.base.BaseFragment
import com.example.busybee.data.RepositoryImp
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.data.source.RemoteDataSourceImp
import com.example.busybee.databinding.FragmentPersonalDoneBinding
import com.example.busybee.ui.details.DetailsFragment
import com.example.busybee.ui.home.teamtask.inprogress.TeamInProgressPresenter
import com.example.busybee.utils.sharedpreference.SharedPreferencesUtils
import com.example.busybee.utils.TaskType
import com.example.busybee.utils.replaceFragment
import com.example.busybee.utils.sharedpreference.SharedPreferencesInterface

class PersonalDoneFragment : BaseFragment<FragmentPersonalDoneBinding>(),
    PersonalDoneAdapter.PersonalDoneTaskInteractionListener, PersonalDoneView {
    private lateinit var adapter: PersonalDoneAdapter
    private lateinit var dones: List<PersonalToDo>
    private val sharedPreferences: SharedPreferencesInterface by lazy {
        SharedPreferencesUtils(
            requireContext()
        )
    }
    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSourceImp(requireContext())
    }
    private val presenter by lazy {
        PersonalDonePresenter(
            RepositoryImp(
                remoteDataSource,
                sharedPreferences
            ), this
        )
    }

    override val TAG = this::class.java.simpleName.toString()

    override fun getViewBinding(): FragmentPersonalDoneBinding {
        return FragmentPersonalDoneBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        presenter.getLocalPersonalDones()
        adapter = PersonalDoneAdapter(dones, this)
        binding.recyclerDone.adapter = adapter
        binding.headerDone.textTodoStatus.text = getString(R.string.done)
        binding.headerDone.taskCount.text = getString(R.string.tasks, dones.size)
        setToDoColorBasedOnTheme()
        showPlaceHolder(dones)
    }

    private fun showPlaceHolder(todo: List<PersonalToDo>) {
        if (todo.isEmpty()) {
            binding.textNoTasksPersonalDone.visibility = View.VISIBLE
            binding.recyclerDone.visibility = View.GONE
            binding.imagePlaceholderPersonalDone.visibility = View.VISIBLE
        } else {
            binding.textNoTasksPersonalDone.visibility = View.GONE
            binding.recyclerDone.visibility = View.VISIBLE
            binding.imagePlaceholderPersonalDone.visibility = View.GONE
        }
    }

    private fun setToDoColorBasedOnTheme() {
        val uiManager = requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        when (uiManager.nightMode) {
            UiModeManager.MODE_NIGHT_NO -> {
                binding.headerDone.textTodoStatus.setBackgroundResource(R.drawable.shape_done)
            }
            UiModeManager.MODE_NIGHT_YES -> {
                binding.headerDone.textTodoStatus.setBackgroundResource(R.drawable.shape_done_dark)
            }
            else -> {
                binding.headerDone.textTodoStatus.setBackgroundResource(R.drawable.shape_done)
            }
        }
    }

    override fun onTasKClicked(flag: TaskType, personalToDo: PersonalToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, null, personalToDo)
        replaceFragment(detailsFragment)
    }

    override fun getLocalPersonalDones(dones: List<PersonalToDo>) {
        this.dones = dones
    }

}