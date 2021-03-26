package com.fedorskvortsov.companies.data.repository

import com.fedorskvortsov.companies.data.remote.source.CompanyRemoteDataSource
import com.fedorskvortsov.companies.domain.repository.CompanyRepository
import javax.inject.Inject

class CompanyRepositoryImpl @Inject constructor(
    private val companyRemoteDataSource: CompanyRemoteDataSource
) : CompanyRepository {
    override val companies = companyRemoteDataSource.companies

    override fun getCompany(companyId: Int) = companyRemoteDataSource.getCompany(companyId)
}