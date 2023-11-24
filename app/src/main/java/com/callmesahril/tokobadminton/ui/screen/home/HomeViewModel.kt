package com.callmesahril.tokobadminton.ui.screen.home

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.callmesahril.tokobadminton.data.BadmintonRepository
import com.callmesahril.tokobadminton.model.OrderBadminton
import com.callmesahril.tokobadminton.ui.common.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: BadmintonRepository,
) : ViewModel() {
    private val _uiState: MutableStateFlow<UiState<List<OrderBadminton>>> =
        MutableStateFlow(UiState.Loading)
    val uiState: StateFlow<UiState<List<OrderBadminton>>>
        get() = _uiState

    private val _query = mutableStateOf("")
    val query: State<String> get() = _query


    fun onQueryChanged(newQuery: String) {
        _query.value = newQuery
        if (newQuery.isBlank()) {
            getAllBadminton()
        } else {
            searchBadminton(newQuery)
        }
    }

    private fun getAllBadminton() {
        viewModelScope.launch {
            repository.getAllRewards()
                .catch { _uiState.value = UiState.Error(it.message.toString()) }
                .collect { _uiState.value = UiState.Success(it) }
        }
    }

    private fun searchBadminton(query: String) {
        viewModelScope.launch {
            repository.searchHeroes(query)
                .catch { _uiState.value = UiState.Error(it.message.toString()) }
                .collect { _uiState.value = UiState.Success(it) }
        }
    }
}
