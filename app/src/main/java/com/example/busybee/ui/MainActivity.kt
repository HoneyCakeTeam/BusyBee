package com.example.busybee.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.fragment.app.Fragment
import com.example.busybee.R
import com.example.busybee.data.RepositoryImp
import com.example.busybee.data.source.RemoteDataSource
import com.example.busybee.data.source.RemoteDataSourceImp
import com.example.busybee.ui.home.HomeFragment
import com.example.busybee.ui.login.LoginFragment
import com.example.busybee.utils.Constant
import com.example.busybee.utils.sharedpreference.SharedPreferencesInterface
import com.example.busybee.utils.sharedpreference.SharedPreferencesUtils

class MainActivity : AppCompatActivity(), MainView {
    private val sharedPreferences: SharedPreferencesInterface by lazy {
        SharedPreferencesUtils(
            this
        )
    }
    private val remoteDataSource: RemoteDataSource by lazy {
        RemoteDataSourceImp(this)
    }
    private val presenter by lazy {
        MainPresenter(
            RepositoryImp(
                remoteDataSource,
                sharedPreferences
            ), this
        )
    }
    private val fragmentHome = HomeFragment()
    private val fragmentLogin = LoginFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContentView(R.layout.activity_main)

        val bundle = intent.getBundleExtra(Constant.BUNDLE_TASK)
        val fragmentTag = bundle?.getString(Constant.FRAGMENT_KEY)

        if (fragmentTag != null) {
            replaceFragment(fragmentLogin)
        } else {
            presenter.getTokenFromShared()
        }
    }


    private fun replaceFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.fragment_container, fragment)
        transaction.commit()
    }

    override fun getToken(token: String?) {
        if (token.isNullOrEmpty()) {
            replaceFragment(fragmentLogin)
        } else {
            replaceFragment(fragmentHome)
        }
    }
}