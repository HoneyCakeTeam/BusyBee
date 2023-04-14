package com.example.busybee.ui

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.busybee.R
import com.example.busybee.data.Repository
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.ui.home.HomeFragment
import com.example.busybee.ui.login.view.LoginFragment
import com.example.busybee.utils.SharedPreferencesUtils
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), MainViewInterface {

    private val presenter: MainPresenterInterface by lazy {
        MainPresenter(Repository(RemoteDataSource(this), SharedPreferencesUtils,this)
            , this)
    }
    private val fragmentHome = HomeFragment()
    private val fragmentLogin = LoginFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        statusBarTheme()
        presenter.getTokenFromShared()
    }
    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    @Suppress("DEPRECATION")
    private fun statusBarTheme() {
        window.decorView.systemUiVisibility =
            View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
        window.statusBarColor = Color.TRANSPARENT

        if (isDarkTheme()) {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility and View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR.inv()
        } else {
            window.decorView.systemUiVisibility =
                window.decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val decorView = window.decorView
            decorView.systemUiVisibility =
                decorView.systemUiVisibility or View.SYSTEM_UI_FLAG_LIGHT_NAVIGATION_BAR
        }
    }

    private fun isDarkTheme(): Boolean {
        val currentNightMode = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK
        return currentNightMode == Configuration.UI_MODE_NIGHT_YES
    }

    override fun getTokenFromShared(token: String?) {
        if (!token.isNullOrEmpty()) {
            presenter.getExpirationDateFromShared()
        }else{
            replaceFragment(fragmentLogin)
        }
    }

    override fun getExpirationDateFromShared(expirationDate: String?) {
        if (isTokenExpired(expirationDate)) {
            replaceFragment(fragmentLogin)
        }else{
            replaceFragment(fragmentHome)
        }
    }

    private fun isTokenExpired(expirationDateString: String?): Boolean {
        SharedPreferencesUtils.expirationDate ?: return false
        val dateFormat = SimpleDateFormat("EEE MMM d HH:mm:ss z yyyy", Locale.US)
        val expireDate = dateFormat.parse(expirationDateString!!) ?: return true

        val currentTime = Calendar.getInstance(TimeZone.getTimeZone("UTC")).time
        return currentTime.after(expireDate)
    }
}