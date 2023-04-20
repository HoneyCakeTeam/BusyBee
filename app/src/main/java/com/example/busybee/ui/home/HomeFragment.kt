package com.example.busybee.ui.home

import android.app.UiModeManager
import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.CompositePageTransformer
import com.example.busybee.R
import com.example.busybee.data.RepositoryImp
import com.example.busybee.data.models.PersonalToDo
import com.example.busybee.data.models.TeamToDo
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.data.source.RemoteDataSourceImp
import com.example.busybee.databinding.FragmentHomeBinding
import com.example.busybee.ui.base.BaseFragment
import com.example.busybee.ui.home.personaltask.done.PersonalDoneFragment
import com.example.busybee.ui.home.personaltask.inprogress.PersonalInProgressFragment
import com.example.busybee.ui.home.personaltask.todo.PersonalToDoFragment
import com.example.busybee.ui.home.teamtask.done.TeamDoneFragment
import com.example.busybee.ui.home.teamtask.inprogress.TeamInProgressFragment
import com.example.busybee.ui.home.teamtask.todo.TeamToDoFragment
import com.example.busybee.ui.setting.SettingFragment
import com.example.busybee.utils.TaskType
import com.example.busybee.utils.isOnline
import com.example.busybee.utils.onClickBackFromNavigation
import com.example.busybee.utils.replaceFragment
import com.example.busybee.utils.setStatusBarBackgroundColor
import com.example.busybee.utils.sharedpreference.SharedPreferencesInterface
import com.example.busybee.utils.sharedpreference.SharedPreferencesUtils
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import kotlin.math.abs


class HomeFragment : BaseFragment<FragmentHomeBinding>(), OnTabSelectedListener, HomeView {
    private val sharedPreferences: SharedPreferencesInterface by lazy {
        SharedPreferencesUtils(
            requireContext()
        )
    }
    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSourceImp(requireContext())
    }
    private val homePresenter by lazy {
        HomePresenter(
            RepositoryImp(
                remoteDataSource,
                sharedPreferences
            ), this
        )
    }

    private val teamFragments: List<Fragment> by lazy {
        listOf(teamToDoFragment, teamInProgressFragment, teamDoneFragment)
    }
    private val personalFragments: List<Fragment> by lazy {
        listOf(personalToDoFragment, personalInProgressFragment, personalDoneFragment)
    }
    private lateinit var teamResponse: List<TeamToDo>
    private lateinit var personalResponse: List<PersonalToDo>
    private lateinit var homePagerAdapter: HomeViewPagerAdapter

    private val teamToDoFragment by lazy { TeamToDoFragment() }
    private val teamInProgressFragment by lazy { TeamInProgressFragment() }
    private val teamDoneFragment by lazy { TeamDoneFragment() }
    private val personalToDoFragment by lazy { PersonalToDoFragment() }
    private val personalInProgressFragment by lazy {
        PersonalInProgressFragment()
    }
    private val personalDoneFragment by lazy { PersonalDoneFragment() }

    private val settingsFragment by lazy { SettingFragment() }

    override val TAG = this::class.java.simpleName.toString()

    private lateinit var taskType: TaskType

    override fun getViewBinding(): FragmentHomeBinding = FragmentHomeBinding.inflate(layoutInflater)

    override fun setUp() {
        setupStatusBarColor()


        binding.lottieLoading.visibility = View.VISIBLE
        binding.homeViewPager.visibility = View.GONE

        getTaskType()
        checkTaskType()
        initTabLayout()
        addCallBacks()
        onClickBackFromNavigation()
    }

    private fun setupStatusBarColor(){
        val uiManager = requireContext().getSystemService(Context.UI_MODE_SERVICE) as UiModeManager
        when (uiManager.nightMode) {
            UiModeManager.MODE_NIGHT_NO -> {
                setStatusBarBackgroundColor(resources.getColor(R.color.white_100))
            }

            UiModeManager.MODE_NIGHT_YES -> {
                setStatusBarBackgroundColor(resources.getColor(R.color.theme_dark_shape))
            }

            else -> {
                setStatusBarBackgroundColor(resources.getColor(R.color.white_100))
            }
        }
    }

    private fun checkTaskType() {
        if (::taskType.isInitialized) {
            when (taskType) {
                TaskType.PERSONAL -> {
                    initViewPager(personalFragments)
                    selectTab(0)
                }

                TaskType.TEAM -> {
                    initViewPager(teamFragments)
                    selectTab(1)
                }
            }
        } else {
            getAllPersonalTasks()
            getAllTeamTasks()
        }
    }

    private fun getTaskType() {
        arguments?.let {
            taskType = it.getParcelable(FLAG)!!
        }
    }

    private fun addCallBacks() {
        binding.settings.setOnClickListener {
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
        if (isOnline(requireContext())) {
            homePresenter.getAllTeamTasks()
            showViewsAndHideAnimation()
        } else {
            hideViewsAndShowAnimation()
        }
    }

    override fun showDataOnTeamScreen(response: List<TeamToDo>) {


        homePresenter.setLocalTeamTasks(response)
        teamResponse = response
    }

    override fun showErrorMsgOnTeamScreen(error: Throwable) {
        // Show lottie animation in screen for error
    }

    private fun getAllPersonalTasks() {
        if (isOnline(requireContext())) {
            homePresenter.getPersonalTasks()
            showViewsAndHideAnimation()
        } else {
            hideViewsAndShowAnimation()
        }
    }

    private fun showViewsAndHideAnimation() {
        with(binding) {
            homeViewPager.visibility = View.VISIBLE
            lottieNoInternet.visibility = View.GONE
        }
    }

    private fun hideViewsAndShowAnimation() {
        with(binding) {
            homeViewPager.visibility = View.GONE
            lottieNoInternet.visibility = View.VISIBLE
        }
    }


    override fun showDataOnPersonalScreen(response: List<PersonalToDo>) {
        requireActivity().runOnUiThread {
            binding.lottieLoading.visibility = View.GONE
            binding.homeViewPager.visibility = View.VISIBLE
        }
        personalResponse = response
        homePresenter.setLocalPersonalTasks(response)
        activity?.runOnUiThread {
            initViewPager(personalFragments)
        }
    }

    private fun selectTab(index: Int) {
        val tabLayout = binding.tabLayout
        val tab = tabLayout.getTabAt(index)
        tab!!.select()
    }

    override fun showErrorMsgOnPersonalScreen(error: Throwable) {
        // Show lottie animation in screen for error
    }

    companion object {
        const val FLAG = "flag"

        fun newInstance(taskType: TaskType) =
            HomeFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(FLAG, taskType)
                }
            }
    }
}