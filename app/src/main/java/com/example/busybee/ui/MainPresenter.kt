package com.example.busybee.ui

import com.example.busybee.data.RepositoryInterface

class MainPresenter(
    private val repository: RepositoryInterface,
    private val mainViewInterface: MainViewInterface
) {
    fun getTokenFromShared() {
        mainViewInterface.getTokenFromShared(repository.getToken())
    }

//    fun getExpirationDateFromShared() {
//        mainViewInterface.getExpirationDateFromShared(repository.getExpirationDate())
//    }
}