package com.example.busybee.ui

import com.example.busybee.data.Repository

class MainPresenter(
    private val repository: Repository,
    private val mainViewInterface: MainViewInterface
) {
    fun getTokenFromShared() {
        mainViewInterface.getToken(repository.getToken())
    }

}