package com.example.busybee.utils

import androidx.fragment.app.Fragment
import com.example.busybee.R

fun Fragment.replaceFragment(fragment: Fragment) {
    val fragmentManager = requireActivity().supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null)
    fragmentTransaction.commit()
}