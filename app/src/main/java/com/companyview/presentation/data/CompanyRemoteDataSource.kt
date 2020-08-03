package com.companyview.presentation.data

import com.companyview.api.BaseDataSource
import com.companyview.api.CompanyService
import javax.inject.Inject

class CompanyRemoteDataSource @Inject constructor(private val service: CompanyService) : BaseDataSource() {

    suspend fun fetchData() = getResult { service.getCompany(1, 10, "company") }

}
