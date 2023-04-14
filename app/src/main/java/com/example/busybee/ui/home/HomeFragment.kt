package com.example.busybee.ui.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.CompositePageTransformer
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.data.models.BaseResponse
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.databinding.FragmentHomeBinding
import com.example.busybee.ui.home.personaltask.presenter.PersonalPresenter
import com.example.busybee.ui.home.personaltask.presenter.PersonalPresenterInterface
import com.example.busybee.ui.home.personaltask.view.done.PersonalDoneFragment
import com.example.busybee.ui.home.personaltask.view.inprogress.PersonalInProgressFragment
import com.example.busybee.ui.home.personaltask.view.todo.PersonalToDoFragment
import com.example.busybee.ui.home.teamtask.presenter.TeamPresenter
import com.example.busybee.ui.home.teamtask.presenter.TeamPresenterInterface
import com.example.busybee.ui.home.teamtask.view.done.TeamDoneFragment
import com.example.busybee.ui.home.teamtask.view.inprogress.TeamInProgressFragment
import com.example.busybee.ui.home.teamtask.view.todo.view.TeamToDoFragment
import com.example.busybee.ui.setting.SettingFragment
import com.example.busybee.utils.onClickBackFromNavigation
import com.example.busybee.utils.replaceFragment
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlin.math.abs

class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnTabSelectedListener, HomeViewInterface {
    private val teamPresenter: TeamPresenterInterface by lazy {
        TeamPresenter(
            RemoteDataSource(
                requireContext()
            )
        )
    }
    private val personalPresenter: PersonalPresenterInterface by lazy {
        PersonalPresenter(
            RemoteDataSource(
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
    private lateinit var teamResponse: BaseResponse<List<TeamToDo>>
    private lateinit var personalResponse: BaseResponse<List<PersonalToDo>>
    private lateinit var homePagerAdapter: HomeViewPagerAdapter

    private val teamToDoFragment by lazy { TeamToDoFragment.newInstance(teamToDos as ArrayList<TeamToDo>) }
    private val teamInProgressFragment by lazy {
        TeamInProgressFragment.newInstance(
            teamInProgressToDos as ArrayList<TeamToDo>
        )
    }
    private val teamDoneFragment by lazy { TeamDoneFragment.newInstance(teamDoneToDos as ArrayList<TeamToDo>) }
    private val personalToDoFragment by lazy { PersonalToDoFragment.newInstance(personalToDos as ArrayList<PersonalToDo>) }
    private val personalInProgressFragment by lazy {
        PersonalInProgressFragment.newInstance(
            personalInProgressToDos as ArrayList<PersonalToDo>
        )
    }
    private val personalDoneFragment by lazy { PersonalDoneFragment.newInstance(personalDoneToDos as ArrayList<PersonalToDo>) }

    private val teamToDos by lazy {
        teamResponse.value.filter { it.status == 0 }
    }
    private val teamInProgressToDos by lazy {
        teamResponse.value.filter { it.status == 1 }
    }
    private val teamDoneToDos by lazy {
        teamResponse.value.filter { it.status == 2 }
    }

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
    override fun getAllTeamTasks() {
        teamPresenter.getAllTeamTasks(
            ::onTeamSuccessResponse,
            ::onTeamFailureResponse
        )
    }

    override fun onTeamSuccessResponse(response: BaseResponse<List<TeamToDo>>) {
        teamResponse = response
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

    override fun onPersonalSuccessResponse(response: BaseResponse<List<PersonalToDo>>) {
        this.personalResponse = response
        activity?.runOnUiThread {
            initViewPager(personalFragments)
        }
    }

    override fun onPersonalFailureResponse(error: Throwable) {
        // Show lottie animation in screen for error
    }


}