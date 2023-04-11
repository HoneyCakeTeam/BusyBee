package com.example.busybee.ui.home

import androidx.fragment.app.Fragment
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.PersonalToDoListResponse
import com.example.busybee.data.models.TeamToDoListResponse
import com.example.busybee.data.models.asDomainModel
import com.example.busybee.databinding.FragmentHomeBinding
import com.example.busybee.domain.models.PersonalTodos
import com.example.busybee.domain.models.TeamTodos
import com.example.busybee.ui.home.personaltask.presenter.PersonalPresenter
import com.example.busybee.ui.home.personaltask.presenter.PersonalPresenterInterface
import com.example.busybee.ui.home.personaltask.view.done.PersonalDoneFragment
import com.example.busybee.ui.home.personaltask.view.inProgress.PersonalInProgressFragment
import com.example.busybee.ui.home.personaltask.view.toDo.PersonalToDoFragment
import com.example.busybee.ui.home.teamtask.presenter.TeamPresenter
import com.example.busybee.ui.home.teamtask.presenter.TeamPresenterInterface
import com.example.busybee.ui.home.teamtask.view.done.TeamDoneFragment
import com.example.busybee.ui.home.teamtask.view.inprogress.TeamInProgressFragment
import com.example.busybee.ui.home.teamtask.view.todo.view.TeamToDoFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener

class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnTabSelectedListener, HomeViewInterface {
    private val teamPresenter: TeamPresenterInterface by lazy {
        TeamPresenter(
            Repository(
                requireContext()
            )
        )
    }
    private val personalPresenter: PersonalPresenterInterface by lazy {
        PersonalPresenter(
            Repository(
                requireContext()
            )
        )
    }
    private val teamFragments: List<Fragment> by lazy {
        listOf(teamToDoFragment, teamInProgressFragment, teamDoneFragment)
    }
    private val personalFragments: List<Fragment> by lazy {
        listOf(personalToDoFragment, personalInProgressFragment, personalDoneFragment)
    }
    private lateinit var teamResponse: TeamToDoListResponse
    private lateinit var personalResponse: PersonalToDoListResponse
    private lateinit var homePagerAdapter: HomeViewPagerAdapter

    private val teamToDoFragment by lazy { TeamToDoFragment.newInstance(teamToDos) }
    private val teamInProgressFragment by lazy { TeamInProgressFragment.newInstance(teamInProgressToDos) }
    private val teamDoneFragment by lazy { TeamDoneFragment.newInstance(teamDoneToDos) }
    private val personalToDoFragment by lazy { PersonalToDoFragment.newInstance(personalToDos) }
    private val personalInProgressFragment by lazy {
        PersonalInProgressFragment.newInstance(
            personalInProgressToDos
        )
    }
    private val personalDoneFragment by lazy { PersonalDoneFragment.newInstance(personalDoneToDos) }

    private val teamToDos by lazy {
        TeamTodos(teamResponse.asDomainModel().values.filter { it.status == 0 })
    }
    private val teamInProgressToDos by lazy {
        TeamTodos(teamResponse.asDomainModel().values.filter { it.status == 1 })
    }
    private val teamDoneToDos by lazy {
        TeamTodos(teamResponse.asDomainModel().values.filter { it.status == 2 })
    }

    private val personalToDos by lazy {
        PersonalTodos(personalResponse.asDomainModel().values.filter { it.status == 0 })
    }
    private val personalInProgressToDos by lazy {
        PersonalTodos(personalResponse.asDomainModel().values.filter { it.status == 1 })
    }
    private val personalDoneToDos by lazy {
        PersonalTodos(personalResponse.asDomainModel().values.filter { it.status == 2 })
    }

    override val TAG = this::class.java.simpleName.toString()


    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun setUp() {
        getAllPersonalTasks()
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
        teamPresenter.getAllTeamTasks(
            ::onTeamSuccessResponse,
            ::onTeamFailureResponse
        )
    }

    override fun onTeamSuccessResponse(response: TeamToDoListResponse) {
        teamResponse = response
        activity?.runOnUiThread {
            initTabLayout()
            initViewPager(teamFragments)
        }
    }

    override fun onTeamFailureResponse(error: Throwable) {
        // Show lottie animation in screen for error
    }

    override fun getAllPersonalTasks() {
        personalPresenter.getPersonalTasks(
            ::onPersonalSuccessResponse,
            ::onPersonalFailureResponse
        )
    }

    override fun onPersonalSuccessResponse(response: PersonalToDoListResponse) {
        this.personalResponse = response
        activity?.runOnUiThread {
            initTabLayout()
            initViewPager(personalFragments)
        }
    }

    override fun onPersonalFailureResponse(error: Throwable) {
        // Show lottie animation in screen for error
    }

}