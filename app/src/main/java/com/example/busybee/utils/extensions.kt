package com.example.busybee.utils

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.busybee.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.snackbar.Snackbar

fun Fragment.replaceFragment(fragment: Fragment) {
    val fragmentManager = requireActivity().supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()
    fragmentTransaction.replace(R.id.fragment_container, fragment).addToBackStack(null)
    fragmentTransaction.commit()
}

fun Fragment.onClickBackFromNavigation() {
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showAlertDialog()
        }
    }
    requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, callback)
    callback.isEnabled = true
}

fun Fragment.showAlertDialog() {
    MaterialAlertDialogBuilder(requireContext(), R.style.alertDialogCustomStyle)
        .setTitle(getString(R.string.dialog_title))
        .setMessage(getString(R.string.confirm))
        .setPositiveButton(
            getString(R.string.exit)
        ) { _, _ ->
            activity?.moveTaskToBack(true)
            activity?.finish()
        }
        .setNegativeButton(
            getString(R.string.stay)
        ) { _, _ ->
            Snackbar.make(requireView(), getString(R.string.canceled), Snackbar.LENGTH_SHORT)
                .show()
        }.show()
}

fun Fragment.setStatusBarBackgroundColor(resourceColor: Int) {
    requireActivity().window.decorView.systemUiVisibility =
        View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
    requireActivity().window.statusBarColor = resourceColor

    if (requireActivity().isDarkTheme()) {
        requireActivity().window.decorView.systemUiVisibility =
            requireActivity().window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
    } else {
        requireActivity().window.decorView.systemUiVisibility =
            requireActivity().window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
    }
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val decorView = requireActivity().window.decorView
        decorView.systemUiVisibility =
            decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
    }
}

fun Activity.isDarkTheme(): Boolean {
    val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
    return currentNightMode == Configuration.UI_MODE_NIGHT_YES
}
