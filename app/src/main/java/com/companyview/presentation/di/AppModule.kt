package com.companyview.presentation.di

import android.app.Application
import com.companyview.api.CompanyService
import com.companyview.data.AppDatabase
import com.mvvmretrofitroomdagger2coroutinekotlin.di.CoroutineScropeIO
import com.mvvmretrofitroomdagger2coroutinekotlin.di.LegoAPI
import com.companyview.presentation.data.CompanyRemoteDataSource
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class, CoreDataModule::class])
class AppModule {

    @Singleton
    @Provides
    fun provideCompanyService(@LegoAPI okhttpClient: OkHttpClient,
                              converterFactory: GsonConverterFactory
    ) = provideService(okhttpClient, converterFactory, CompanyService::class.java)

    @Singleton
    @Provides
    fun provideCompanyRemoteDataSource(companyService: CompanyService)
            = CompanyRemoteDataSource(companyService)

    @LegoAPI
    @Provides
    fun providePrivateOkHttpClient(
            upstreamClient: OkHttpClient
    ): OkHttpClient {
        return upstreamClient.newBuilder().build()
    }

    @Singleton
    @Provides
    fun provideDb(app: Application) = AppDatabase.getInstance(app)

    @Singleton
    @Provides
    fun provideLegoThemeDao(db: AppDatabase) = db.companyDataDao()

    @CoroutineScropeIO
    @Provides
    fun provideCoroutineScopeIO() = CoroutineScope(Dispatchers.IO)


    private fun createRetrofit(
            okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
                .baseUrl(CompanyService.ENDPOINT)
                .client(okhttpClient)
                .addConverterFactory(converterFactory)
                .build()
    }

    private fun <T> provideService(okhttpClient: OkHttpClient,
            converterFactory: GsonConverterFactory, clazz: Class<T>): T {
        return createRetrofit(okhttpClient, converterFactory).create(clazz)
    }
}
