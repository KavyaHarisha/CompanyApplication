package com.companyview.presentation.data

import com.companyview.data.resultLiveData
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CompanyRepository @Inject constructor(private val dao: CompanyDataDao,
                                            private val remoteSource: CompanyRemoteDataSource
) {

    val company = resultLiveData(
            databaseQuery = { dao.getCompanyData() },
            networkCall = { remoteSource.fetchData() },
            saveCallResult = { dao.insertAll(it) })

}
