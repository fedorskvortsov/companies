package com.fedorskvortsov.companies.data.remote.source

import com.fedorskvortsov.companies.data.remote.api.CompanyService
import com.fedorskvortsov.companies.domain.entity.SimpleCompany
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class CompanyRemoteDataSource @Inject constructor(
    private val service: CompanyService
) {
    val companies: Flow<List<SimpleCompany>> = flow {
        val companies = service.fetchCompanies()
        emit(companies)
    }

    fun getCompany(companyId: Int) = flow {
        val company = service.fetchCompany(companyId)
        emit(company)
    }
}