package com.example.busybee.ui.setting

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.Fragment
import com.example.busybee.R
import com.example.busybee.databinding.FragmentSettingsBinding

class SettingFragment : Fragment() {
    lateinit var binding: FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        binding.switchTheme.isChecked= lightState
        callBacks()
    }


    private fun callBacks() {
        binding.switchTheme.setOnCheckedChangeListener { _, _ ->
            if (isCurrentUiDarkTheme())
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            else
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            lightState=!lightState
        }

    }

    private fun isCurrentUiDarkTheme() =
        AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES


    companion object {
        var lightState = true
    }
}