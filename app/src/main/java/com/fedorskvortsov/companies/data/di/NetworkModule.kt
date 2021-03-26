package com.fedorskvortsov.companies.data.di

import com.fedorskvortsov.companies.data.remote.api.CompanyService
import com.fedorskvortsov.companies.data.remote.api.CompanyServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindCompanyService(
        companyServiceImpl: CompanyServiceImpl
    ): CompanyService
}