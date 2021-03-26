package com.fedorskvortsov.companies.data.remote.api

import com.fedorskvortsov.companies.domain.entity.Company
import com.fedorskvortsov.companies.domain.entity.SimpleCompany

interface CompanyService {
    suspend fun fetchCompanies(): List<SimpleCompany>

    suspend fun fetchCompany(companyId: Int): Company

    companion object {
        const val BASE_IMG_PATH = "https://lifehack.studio/test_task/"
        const val BASE_URL = "https://lifehack.studio/test_task/test.php"
    }
}