package com.example.busybee.ui.home

import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.PersonalGetToDoListResponse
import com.example.busybee.data.models.TeamToDoListResponse
import com.example.busybee.data.models.asDomainModel
import com.example.busybee.databinding.FragmentHomeBinding
import com.example.busybee.ui.home.personalTask.doneTask.PersonalDoneFragment
import com.example.busybee.ui.home.personalTask.inProgressTask.PersonalInProgressFragment
import com.example.busybee.ui.home.personalTask.toDoTask.PersonalToDoFragment
import com.example.busybee.ui.home.teamtask.presenter.TeamPresenter
import com.example.busybee.ui.home.teamtask.presenter.TeamPresenterInterface
import com.example.busybee.ui.home.teamtask.view.done.TeamDoneFragment
import com.example.busybee.ui.home.teamtask.view.inProgress.TeamInProgressFragment
import com.example.busybee.ui.home.teamtask.view.toDo.TeamToDoFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnTabSelectedListener, HomeViewInterface {
    private val presenter: TeamPresenterInterface by lazy { TeamPresenter(Repository(requireContext())) }
    private val teamFragments: List<Fragment> by lazy {
        listOf(teamToDoFragment, teamInProgressFragment, teamDoneFragment)
    }
    private val personalFragments: List<Fragment> by lazy {
        listOf(personalToDoFragment, personalInProgressFragment, personalDoneFragment)
    }
    private lateinit var response: TeamToDoListResponse
    private val teamToDoFragment by lazy { TeamToDoFragment.newInstance(response.asDomainModel()) }
    private val teamDoneFragment = TeamDoneFragment()
    private val teamInProgressFragment = TeamInProgressFragment()
    private val personalToDoFragment = PersonalToDoFragment()
    private val personalInProgressFragment = PersonalInProgressFragment()
    private val personalDoneFragment = PersonalDoneFragment()
    private lateinit var homePagerAdapter: HomeViewPagerAdapter
    override val TAG = this::class.java.simpleName.toString()


    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }


    override fun setUp() {
        getAllTeamTasks()
    }

    private fun initTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(this)
    }

    private fun initViewPager(fragmentList: List<Fragment>) {
        homePagerAdapter =
            HomeViewPagerAdapter(parentFragmentManager, this.lifecycle, fragmentList)
        binding.homeViewPager.adapter = homePagerAdapter
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.text) {
            getString(R.string.my_tasks) -> initViewPager(personalFragments)
            getString(R.string.team_tasks) -> initViewPager(teamFragments)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}
    override fun getAllTeamTasks() {
        presenter.getAllTeamTasks(
            ::onTeamSuccessResponse,
            ::onTeamFailureResponse
        )
    }

    override fun onTeamSuccessResponse(response: TeamToDoListResponse) {
        // here we will update the ui
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(),
                "Get successful! ${response.value[0].title}",
                Toast.LENGTH_SHORT
            ).show()
        }
        this.response = response
        activity?.runOnUiThread {
            initTabLayout()
            initViewPager(personalFragments)
        }
    }

    override fun onTeamFailureResponse(error: Throwable) {
        activity?.runOnUiThread {
            Toast.makeText(
                requireContext(),
                "Get faillll! ${error.message} ",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    override fun getAllPersonalTasks() {
        TODO("Not yet implemented")
    }

    override fun onPersonalSuccessResponse(response: PersonalGetToDoListResponse) {
        TODO("Not yet implemented")
    }

    override fun onPersonalFailureResponse(error: Throwable) {
        TODO("Not yet implemented")
    }


}