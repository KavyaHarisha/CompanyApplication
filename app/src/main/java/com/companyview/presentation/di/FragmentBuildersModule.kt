package com.companyview.presentation.di


import com.companyview.presentation.ui.company.CompanyListViewFragment
import com.companyview.presentation.ui.member.MemberListViewFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {
    @ContributesAndroidInjector
    abstract fun contributeCompanyListViewFragment(): CompanyListViewFragment

    @ContributesAndroidInjector
    abstract fun contributeMemberListViewFragment(): MemberListViewFragment
}
