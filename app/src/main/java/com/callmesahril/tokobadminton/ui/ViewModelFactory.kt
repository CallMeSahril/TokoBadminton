package com.callmesahril.tokobadminton.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.callmesahril.tokobadminton.data.BadmintonRepository
import com.callmesahril.tokobadminton.ui.screen.cart.CartViewModel
import com.callmesahril.tokobadminton.ui.screen.detail.DetailBadmintonViewModel
import com.callmesahril.tokobadminton.ui.screen.home.HomeViewModel

class ViewModelFactory(private val repository: BadmintonRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailBadmintonViewModel::class.java)) {
            return DetailBadmintonViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}