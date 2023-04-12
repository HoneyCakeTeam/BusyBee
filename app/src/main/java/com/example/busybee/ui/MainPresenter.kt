package com.example.busybee.ui

import com.example.busybee.data.RepositoryInterface

class MainPresenter(
    private val repository: RepositoryInterface,
    private val mainViewInterface: MainViewInterface
) : MainPresenterInterface {
    override fun getTokenFromShared() {
        mainViewInterface.getTokenFromShared(repository.getTokenFromShared())
    }

    override fun getExpirationDateFromShared() {
        mainViewInterface.getExpirationDateFromShared(repository.getExpirationDateFromShared())
    }
}