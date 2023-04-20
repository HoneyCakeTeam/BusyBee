package com.example.busybee.ui

import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.busybee.R
import com.example.busybee.data.RepositoryImp
import com.example.busybee.data.source.RemoteDataSourceImp
import com.example.busybee.ui.home.HomeFragment
import com.example.busybee.ui.login.LoginFragment
import com.example.busybee.utils.Constant
import com.example.busybee.utils.sharedpreference.SharedPreferencesUtils
import java.util.*

class MainActivity : AppCompatActivity(), MainView {

    private val presenter by lazy {
        MainPresenter(
            RepositoryImp(
                RemoteDataSourceImp(this),
                SharedPreferencesUtils(this)
            ), this
        )
    }
    private val fragmentHome = HomeFragment()
    private val fragmentLogin = LoginFragment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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