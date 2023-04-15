package com.example.busybee.ui.home.personaltask.view.inprogress

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.databinding.FragmentPersonalInProgressBinding
import com.example.busybee.ui.details.view.DetailsFragment
import com.example.busybee.utils.replaceFragment

class PersonalInProgressFragment : BaseFragment<FragmentPersonalInProgressBinding>(),
    PersonalInProgressAdapter.PersonalInProgressTaskInteractionListener {
    private lateinit var adapter: PersonalInProgressAdapter
    private lateinit var inProgress: List<PersonalToDo>
    override val TAG = this::class.java.simpleName.toString()

    override fun getViewBinding(): FragmentPersonalInProgressBinding {
        return FragmentPersonalInProgressBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getDons()
        adapter = PersonalInProgressAdapter(inProgress, this)
        binding.recyclerInProgress.adapter = adapter
        binding.headerInProgress.textTodoStatus.text = getString(R.string.in_progress)
        binding.headerInProgress.taskCount.text = getString(R.string.tasks, inProgress.size)
        setToDoColorBasedOnTheme()
    }
    private fun setToDoColorBasedOnTheme(){
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

    private fun getDons() {

        arguments?.let {
            inProgress = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelableArrayList(PERSONAL_IN_PROGRESS_LIST,PersonalToDo::class.java)!!
            }else{
                it.getParcelableArrayList(PERSONAL_IN_PROGRESS_LIST)!!
            }
        }
    }

    companion object {
        const val PERSONAL_IN_PROGRESS_LIST = "Personal_In_Progress_List"
        fun newInstance(tasks: ArrayList<PersonalToDo>) =
            PersonalInProgressFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(PERSONAL_IN_PROGRESS_LIST, tasks)
                }
            }
    }

    override fun onTasKClicked(flag: Int, personalToDo: PersonalToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, null, personalToDo)
        replaceFragment(detailsFragment)
    }

}
