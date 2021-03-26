package com.fedorskvortsov.companies.data.di

import com.fedorskvortsov.companies.data.repository.CompanyRepositoryImpl
import com.fedorskvortsov.companies.domain.repository.CompanyRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindCompanyRepository(
        companyRepositoryImpl: CompanyRepositoryImpl
    ): CompanyRepository
}