package com.fedorskvortsov.companies.ui.list.state

import com.fedorskvortsov.companies.domain.entity.SimpleCompany

sealed class CompanyListUiState {
    data class Success(val companies: List<SimpleCompany>): CompanyListUiState()
    data class Error(val exception: Throwable): CompanyListUiState()
    object Loading: CompanyListUiState()
}
