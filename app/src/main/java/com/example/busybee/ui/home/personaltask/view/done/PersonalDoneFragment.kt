package com.example.busybee.ui.home.personaltask.view.done

import android.app.UiModeManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.databinding.FragmentPersonalDoneBinding
import com.example.busybee.ui.details.view.DetailsFragment
import com.example.busybee.utils.replaceFragment

class PersonalDoneFragment : BaseFragment<FragmentPersonalDoneBinding>(),
    PersonalDoneAdapter.PersonalDoneTaskInteractionListener {
    private lateinit var adapter: PersonalDoneAdapter
    private lateinit var done: List<PersonalToDo>

    override val TAG = this::class.java.simpleName.toString()

    override fun getViewBinding(): FragmentPersonalDoneBinding {
        return FragmentPersonalDoneBinding.inflate(layoutInflater)
    }

    override fun setUp() {
        getDons()
        adapter = PersonalDoneAdapter(done, this)
        binding.recyclerDone.adapter = adapter
        binding.headerDone.textTodoStatus.text = getString(R.string.done)
        binding.headerDone.taskCount.text = getString(R.string.tasks, done.size)
        setToDoColorBasedOnTheme()
    }
    private fun setToDoColorBasedOnTheme(){
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
    private fun getDons() {

        arguments?.let {
            done = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                it.getParcelableArrayList(PERSONAL_DONE_LIST, PersonalToDo::class.java)!!
            } else {
                it.getParcelableArrayList(PERSONAL_DONE_LIST)!!
            }
        }
    }

    companion object {
        const val PERSONAL_DONE_LIST = "Personal_Done_List"
        fun newInstance(tasks: ArrayList<PersonalToDo>) =
            PersonalDoneFragment().apply {
                arguments = Bundle().apply {
                    putParcelableArrayList(PERSONAL_DONE_LIST, tasks)
                }
            }
    }

    override fun onTasKClicked(flag: Int, personalToDo: PersonalToDo) {
        val detailsFragment = DetailsFragment.newInstance(flag, null, personalToDo)
        replaceFragment(detailsFragment)
    }

}