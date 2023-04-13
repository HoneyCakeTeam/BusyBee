package com.example.busybee.ui.home.personaltask.view.done

import android.os.Bundle
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.models.PersonalTodo
import com.example.busybee.databinding.FragmentPersonalDoneBinding
import com.example.busybee.domain.models.PersonalTodos
import com.example.busybee.ui.details.view.DetailsFragment
import com.example.busybee.utils.replaceFragment

class PersonalDoneFragment : BaseFragment<FragmentPersonalDoneBinding>(),
    PersonalDoneAdapter.PersonalDoneTaskInteractionListener {
    private lateinit var adapter: PersonalDoneAdapter
    private lateinit var done: PersonalTodos

    override val TAG = this::class.java.simpleName.toString()

    override fun getViewBinding(): FragmentPersonalDoneBinding {
        return FragmentPersonalDoneBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getDons()
        adapter = PersonalDoneAdapter(done.values, this)
        binding.recyclerDone.adapter = adapter
        binding.headerDone.textTodoStatus.text = getString(R.string.done)
        binding.headerDone.taskCount.text = getString( R.string.tasks , done.values.size )
        binding.headerDone.textTodoStatus.setBackgroundResource(R.drawable.shape_done)
    }

    private fun getDons() {
        arguments?.let {
            done = it.getParcelable(PERSONAL_DONE_LIST)!!
        }
    }

    companion object {
        const val PERSONAL_DONE_LIST = "Personal_Done_List"
        fun newInstance(tasks: PersonalTodos) =
            PersonalDoneFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(PERSONAL_DONE_LIST, tasks)
                }
            }
    }

    override fun onTasKClicked(flag: Int, personalToDo: PersonalTodo) {
        val detailsFragment = DetailsFragment.newInstance(flag, null, personalToDo)
        replaceFragment(detailsFragment)
    }

}