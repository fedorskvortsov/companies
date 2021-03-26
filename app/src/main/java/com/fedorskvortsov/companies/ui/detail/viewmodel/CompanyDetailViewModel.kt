package com.fedorskvortsov.companies.ui.detail.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fedorskvortsov.companies.domain.repository.CompanyRepository
import com.fedorskvortsov.companies.ui.detail.state.CompanyDetailUiState
import com.fedorskvortsov.companies.ui.list.state.CompanyListUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: CompanyRepository
): ViewModel() {

    private val _uiState = MutableStateFlow<CompanyDetailUiState>(CompanyDetailUiState.Loading)
    val uiState: StateFlow<CompanyDetailUiState> = _uiState

    private val companyId = savedStateHandle.get<Int>("companyId")
            ?: throw IllegalArgumentException("Company ID required")
    init {
        viewModelScope.launch {
            repository.getCompany(companyId)
                    .catch { exception ->
                        _uiState.value = CompanyDetailUiState.Error(exception)
                    }
                    .collect { company ->
                        _uiState.value = CompanyDetailUiState.Success(company)
                    }
        }
    }
}