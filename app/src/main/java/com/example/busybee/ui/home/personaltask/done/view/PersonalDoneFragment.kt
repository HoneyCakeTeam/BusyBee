package com.example.busybee.ui.home.personaltask.done.view

import android.app.UiModeManager
import android.content.Context
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.databinding.FragmentPersonalDoneBinding
import com.example.busybee.ui.details.view.DetailsFragment
import com.example.busybee.ui.home.personaltask.done.presenter.PersonalDonePresenter
import com.example.busybee.utils.SharedPreferencesUtils
import com.example.busybee.utils.TaskType
import com.example.busybee.utils.replaceFragment

class PersonalDoneFragment : BaseFragment<FragmentPersonalDoneBinding>(),
    PersonalDoneAdapter.PersonalDoneTaskInteractionListener, PersonalDoneViewInterface {
    private lateinit var adapter: PersonalDoneAdapter
    private lateinit var dones: List<PersonalToDo>

    private val presenter by lazy {
        PersonalDonePresenter(
            Repository(
                RemoteDataSource(requireContext()),
                SharedPreferencesUtils, requireContext()
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