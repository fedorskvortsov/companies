package com.fedorskvortsov.companies.ui.list.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorskvortsov.companies.domain.repository.CompanyRepository
import com.fedorskvortsov.companies.ui.list.state.CompanyListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListViewModel @Inject constructor(
    private val repository: CompanyRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<CompanyListUiState>(CompanyListUiState.Loading)
    val uiState: StateFlow<CompanyListUiState> = _uiState

    init {
        getCompanies()
    }

    fun getCompanies() {
        _uiState.value = CompanyListUiState.Loading
        viewModelScope.launch {
            repository.companies
                .catch { exception ->
                    _uiState.value = CompanyListUiState.Error(exception)
                }
                .collect { companies ->
                    _uiState.value = CompanyListUiState.Success(companies)
                }
        }
    }
}