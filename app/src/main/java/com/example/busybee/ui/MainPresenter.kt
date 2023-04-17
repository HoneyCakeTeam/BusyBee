package com.example.busybee.ui

import com.example.busybee.data.Repository

class MainPresenter(
    private val repository: Repository,
    private val mainViewInterface: MainView
) {
    fun getTokenFromShared() {
        mainViewInterface.getToken(repository.getToken())
    }

}