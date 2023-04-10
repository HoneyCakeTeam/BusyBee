package com.example.busybee.ui.home.view

import androidx.fragment.app.Fragment
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentHomeBinding
import com.example.busybee.ui.home.view.personalTask.view.doneTask.PersonalDoneFragment
import com.example.busybee.ui.home.view.personalTask.view.inProgressTask.PersonalInProgressFragment
import com.example.busybee.ui.home.view.personalTask.view.toDoTask.PersonalToDoFragment
import com.example.busybee.ui.home.view.teamTask.view.done.TeamDoneFragment
import com.example.busybee.ui.home.view.teamTask.view.inProgress.TeamInProgressFragment
import com.example.busybee.ui.home.view.teamTask.view.toDo.TeamToDoFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener


class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnTabSelectedListener {
    private lateinit var teamFragments: List<Fragment>
    private lateinit var personalFragments: List<Fragment>
    private val teamToDoFragment = TeamToDoFragment()
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
        teamFragments = listOf(teamToDoFragment, teamInProgressFragment, teamDoneFragment)
        personalFragments =
            listOf(personalToDoFragment, personalInProgressFragment, personalDoneFragment)
        initTabLayout()
        initViewPager(personalFragments)
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


}