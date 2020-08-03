package com.companyview.presentation.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.companyview.presentation.ui.company.CompanyViewModel
import com.companyview.presentation.ui.activity.SortAndSearchViewModel

import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(CompanyViewModel::class)
    abstract fun bindCompanyViewModel(viewModel: CompanyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SortAndSearchViewModel::class)
    abstract fun bindSortAndSearchViewModel(viewModel: SortAndSearchViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}
