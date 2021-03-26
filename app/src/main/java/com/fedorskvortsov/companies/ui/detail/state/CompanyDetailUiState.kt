package com.fedorskvortsov.companies.ui.detail.state

import com.fedorskvortsov.companies.domain.entity.Company

sealed class CompanyDetailUiState {
    data class Success(val company: Company): CompanyDetailUiState()
    data class Error(val exception: Throwable): CompanyDetailUiState()
    object Loading: CompanyDetailUiState()
}
