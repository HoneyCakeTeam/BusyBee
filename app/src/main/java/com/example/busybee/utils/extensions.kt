package com.example.busybee.utils

import android.content.Context
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

fun Fragment.onClickBackFromNavigation(context: Context) {
    val callback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            showAlertDialog()
            SharedPreferencesUtils.initPreferencesUtil(context)
            SharedPreferencesUtils.token = null
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