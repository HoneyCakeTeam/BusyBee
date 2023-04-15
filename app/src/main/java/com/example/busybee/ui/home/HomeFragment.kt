package com.example.busybee.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.CompositePageTransformer
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.Repository
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.databinding.FragmentHomeBinding
import com.example.busybee.ui.home.personaltask.done.view.PersonalDoneFragment
import com.example.busybee.ui.home.personaltask.inprogress.view.PersonalInProgressFragment
import com.example.busybee.ui.home.personaltask.todo.view.PersonalToDoFragment
import com.example.busybee.ui.home.teamtask.done.view.TeamDoneFragment
import com.example.busybee.ui.home.teamtask.inprogress.view.TeamInProgressFragment
import com.example.busybee.ui.home.teamtask.todo.view.TeamToDoFragment
import com.example.busybee.ui.setting.SettingFragment
import com.example.busybee.utils.SharedPreferencesUtils
import com.example.busybee.utils.onClickBackFromNavigation
import com.example.busybee.utils.replaceFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlin.math.abs

class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnTabSelectedListener, HomeViewInterface {
    private val homePresenter by lazy {
        HomePresenter(
            Repository(
                RemoteDataSource(requireContext()),
                SharedPreferencesUtils, requireContext()
            ), this
        )
    }

    private val teamFragments: List<Fragment> by lazy {
        listOf(teamToDoFragment, teamInProgressFragment, teamDoneFragment)
    }
    private val personalFragments: List<Fragment> by lazy {
        listOf(personalToDoFragment, personalInProgressFragment, personalDoneFragment)
    }
    private lateinit var teamResponse: BaseResponse<List<TeamToDo>>
    private lateinit var personalResponse: BaseResponse<List<PersonalToDo>>
    private lateinit var homePagerAdapter: HomeViewPagerAdapter

    private val teamToDoFragment by lazy { TeamToDoFragment() }
    private val teamInProgressFragment by lazy { TeamInProgressFragment() }
    private val teamDoneFragment by lazy { TeamDoneFragment() }
    private val personalToDoFragment by lazy { PersonalToDoFragment() }
    private val personalInProgressFragment by lazy {
        PersonalInProgressFragment()
    }
    private val personalDoneFragment by lazy { PersonalDoneFragment() }


    private val personalToDos by lazy {
        personalResponse.value.filter { it.status == 0 }
    }
    private val personalInProgressToDos by lazy {
        personalResponse.value.filter { it.status == 1 }
    }
    private val personalDoneToDos by lazy {
        personalResponse.value.filter { it.status == 2 }
    }

    override val TAG = this::class.java.simpleName.toString()


    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun setUp() {
        getAllPersonalTasks()
        getAllTeamTasks()
        initTabLayout()
        addCallBacks()
        onClickBackFromNavigation()
    }

    private fun addCallBacks() {
        onClickSettings()
    }

    private fun onClickSettings() {
        binding.settings.setOnClickListener {
            val personalTodos = personalToDos.size
            val personalInProgressTodos = personalInProgressToDos.size
            val personalDoneTodos = personalDoneToDos.size
            val settingsFragment = SettingFragment.newInstance(
                personalTodos,
                personalInProgressTodos,
                personalDoneTodos
            )
            replaceFragment(settingsFragment)
        }
    }

    private fun initTabLayout() {
        binding.tabLayout.addOnTabSelectedListener(this)
    }

    private fun initViewPager(fragmentList: List<Fragment>) {
        homePagerAdapter =
            HomeViewPagerAdapter(parentFragmentManager, this.lifecycle, fragmentList)
        binding.homeViewPager.apply {
            adapter = homePagerAdapter
            offscreenPageLimit = 3
            setUpTransformer()
        }
    }

    private fun setUpTransformer() {
        val transformer = CompositePageTransformer()
        transformer.addTransformer { page, position ->
            val r = 1 - abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }
        binding.homeViewPager.setPageTransformer(transformer)
    }

    override fun onTabSelected(tab: TabLayout.Tab?) {
        when (tab?.text) {
            getString(R.string.my_tasks) -> initViewPager(personalFragments)
            getString(R.string.team_tasks) -> initViewPager(teamFragments)
        }
    }

    override fun onTabUnselected(tab: TabLayout.Tab?) {}

    override fun onTabReselected(tab: TabLayout.Tab?) {}
    private fun getAllTeamTasks() {
        homePresenter.getAllTeamTasks()
    }

    override fun onTeamSuccessResponse(response: BaseResponse<List<TeamToDo>>) {
        homePresenter.setLocalTeamTasks(response.value)
        teamResponse = response
    }

    override fun onTeamFailureResponse(error: Throwable) {
        // Show lottie animation in screen for error
    }

    private fun getAllPersonalTasks() {
        homePresenter.getPersonalTasks()
    }

    override fun onPersonalSuccessResponse(response: BaseResponse<List<PersonalToDo>>) {
        personalResponse = response
        homePresenter.setLocalPersonalTasks(response.value)
        activity?.runOnUiThread {
            initViewPager(personalFragments)
        }
    }

    override fun onPersonalFailureResponse(error: Throwable) {
        // Show lottie animation in screen for error
    }


}