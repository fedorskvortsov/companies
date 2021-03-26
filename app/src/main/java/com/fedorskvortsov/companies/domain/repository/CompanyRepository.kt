package com.fedorskvortsov.companies.domain.repository

import com.fedorskvortsov.companies.domain.entity.Company
import com.fedorskvortsov.companies.domain.entity.SimpleCompany
import kotlinx.coroutines.flow.Flow

interface CompanyRepository {
    val companies: Flow<List<SimpleCompany>>

    fun getCompany(companyId: Int): Flow<Company>
}