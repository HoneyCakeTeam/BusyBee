package com.example.busybee.ui.home.personaltask.view.inprogress

import android.os.Bundle
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.models.PersonalTodo
import com.example.busybee.databinding.FragmentPersonalInProgressBinding
import com.example.busybee.domain.models.PersonalTodos
import com.example.busybee.ui.details.view.DetailsFragment
import com.example.busybee.utils.replaceFragment

class PersonalInProgressFragment : BaseFragment<FragmentPersonalInProgressBinding>(),
    PersonalInProgressAdapter.PersonalInProgressTaskInteractionListener {
    private lateinit var adapter: PersonalInProgressAdapter
    private lateinit var inProgress: PersonalTodos
    override val TAG = this::class.java.simpleName.toString()

    override fun getViewBinding(): FragmentPersonalInProgressBinding {
        return FragmentPersonalInProgressBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getDons()
        adapter = PersonalInProgressAdapter(inProgress.values, this)
        binding.recyclerInProgress.adapter = adapter
        binding.headerInProgress.textTodoStatus.text = getString(R.string.in_progress)
        binding.headerInProgress.taskCount.text = getString( R.string.tasks , inProgress.values.size )
        binding.headerInProgress.textTodoStatus.setBackgroundResource(R.drawable.shape_inprogress)
    }

    private fun getDons() {
        arguments?.let {
            inProgress = it.getParcelable(PERSONAL_IN_PROGRESS_LIST)!!
        }
    }

    companion object {
        const val PERSONAL_IN_PROGRESS_LIST = "Personal_In_Progress_List"
        fun newInstance(tasks: PersonalTodos) =
            PersonalInProgressFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PERSONAL_IN_PROGRESS_LIST, tasks)
                }
            }
    }

    override fun onTasKClicked(flag: Int, personalToDo: PersonalTodo) {
        val detailsFragment = DetailsFragment.newInstance(flag, null, personalToDo)
        replaceFragment(detailsFragment)
    }

}
