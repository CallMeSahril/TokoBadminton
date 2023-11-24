package com.callmesahril.tokobadminton.ui.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callmesahril.tokobadminton.data.BadmintonRepository
import com.callmesahril.tokobadminton.model.OrderBadminton
import com.callmesahril.tokobadminton.model.Badminton
import com.callmesahril.tokobadminton.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailBadmintonViewModel(
    private val repository: BadmintonRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<OrderBadminton>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<OrderBadminton>>
        get() = _uiState

    fun getBadmintonById(rewardId: Long) {
        viewModelScope.launch {
            _uiState.value = UiState.Loading
            _uiState.value = UiState.Success(repository.getOrderRewardById(rewardId))
        }
    }

    fun addToCart(badminton: Badminton, count: Int) {
        viewModelScope.launch {
            repository.updateOrderReward(badminton.id, count)
        }
    }
}