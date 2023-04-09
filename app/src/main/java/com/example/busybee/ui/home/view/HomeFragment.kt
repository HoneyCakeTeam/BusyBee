package com.example.busybee.ui.home.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.busybee.R
import com.example.busybee.base.BaseFragment
import com.example.busybee.databinding.FragmentHomeBinding
import com.example.busybee.ui.home.teamTask.done.TeamDoneFragment
import com.example.busybee.ui.home.teamTask.inProgress.TeamInProgressFragment
import com.example.busybee.ui.home.teamTask.toDo.TeamToDoFragment


class HomeFragment : BaseFragment<FragmentHomeBinding>() {
    private lateinit var fragmentList: List<Fragment>
    private val teamToDoFragment = TeamToDoFragment()
    private val teamDoneFragment = TeamDoneFragment()
    private val teamInProgressFragment = TeamInProgressFragment()
    override val TAG = "HomeFragment"

    override fun getViewBinding(): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(layoutInflater)
    }


    override fun setUp() {
        fragmentList = listOf(teamToDoFragment ,teamInProgressFragment,teamDoneFragment)
    }


}