package com.companyview

import android.app.Activity
import android.app.Application
import com.companyview.util.CrashReportingTree
import com.companyview.presentation.di.AppInjector
import dagger.android.DispatchingAndroidInjector
import javax.inject.Inject
import dagger.android.HasActivityInjector
import timber.log.Timber

class App : Application(), HasActivityInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) Timber.plant(Timber.DebugTree())
        else Timber.plant(CrashReportingTree())
        AppInjector.init(this)
    }

    override fun activityInjector() = dispatchingAndroidInjector
}