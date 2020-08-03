package com.companyview.api

import com.companyview.presentation.data.CompanyData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CompanyService {

    companion object {
        const val ENDPOINT = "https://next.json-generator.com/api/"
    }

    @GET("json/get/Vk-LhK44U/")
    suspend fun getCompany(
        @Query("page") page: Int? = null,
        @Query("page_size") pageSize: Int? = null,
        @Query("ordering") order: String? = null): Response<List<CompanyData>>
}
