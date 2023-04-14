package com.example.busybee.ui

import com.example.busybee.data.source.RemoteDataSourceInterface

class MainPresenter(
    private val repository: RemoteDataSourceInterface,
    private val mainViewInterface: MainViewInterface
) : MainPresenterInterface {
    override fun getTokenFromShared() {
        mainViewInterface.getTokenFromShared(repository.getTokenFromShared())
    }

    override fun getExpirationDateFromShared() {
        mainViewInterface.getExpirationDateFromShared(repository.getExpirationDateFromShared())
    }
}