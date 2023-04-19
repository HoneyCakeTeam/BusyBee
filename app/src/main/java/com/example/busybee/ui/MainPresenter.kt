package com.example.busybee.ui

import com.example.busybee.data.Repository

class MainPresenter(
    private val repository: Repository,
    private val view: MainView
) {
    fun getTokenFromShared() {
        view.getToken(repository.getToken())
    }

}