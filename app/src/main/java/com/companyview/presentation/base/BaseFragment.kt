package com.companyview.presentation.base

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import com.companyview.presentation.di.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

abstract class BaseFragment : Fragment(), LifecycleObserver {

    /**
     * Override to setup listeners
     */
    protected open fun setupListeners() {}

    /**
     * Override to initialize view models
     */
    protected open fun initViewModels() {}

    /**
     * Override to setup observers for view models
     */
    protected open fun subscribeToViewModels() {}

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var baseActivity: BaseActivity

    override fun onAttach(context: Context) {
        /*
         * This provides every object annotated with @Inject
         */
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
        if (context is BaseActivity) {
            baseActivity = context
        } else {
            throw IllegalStateException("Every fragment has to be attached to BaseActivity")
        }
        lifecycle.addObserver(this)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setupListeners()
        initViewModels()
        subscribeToViewModels()
    }

    /**
     * Returns an existing ViewModel or creates a new one in the activity scope.
     * The created ViewModel is associated with the given scope and will be retained as long as
     * the scope is alive. For instance, Fragments within the same parent activity may share the
     * ViewModel instance.
     */
    inline fun <reified T : ViewModel> getActivityScopeViewModel(): T =
        ViewModelProvider(baseActivity, viewModelFactory)[T::class.java]

    /**
     * Returns an existing ViewModel or creates a new one in the fragment scope.
     * The created ViewModel is associated with the given scope and will be retained as long as
     * the scope is alive. For instance, the returned ViewModel instance would not be shared
     * between Fragments.
     */
    inline fun <reified T : ViewModel> getFragmentScopeViewModel(): T =
        ViewModelProvider(this, viewModelFactory)[T::class.java]


    /**
     * Adds an observer within the [getViewLifecycleOwner] lifespan.
     * @sample [observe]
     */
    inline fun <reified T : Any> LiveData<out T>.observe(crossinline observer: (T) -> Unit) {
        observe(viewLifecycleOwner, Observer<T> { observer(it) })
    }

    protected fun replaceFragment(containerId:Int,fragment: Fragment) {
        activity!!.supportFragmentManager.beginTransaction().replace(containerId, fragment)
            .addToBackStack(fragment.toString())
            .commit()
    }
}