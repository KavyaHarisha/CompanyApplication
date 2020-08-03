package com.companyview.presentation.ui.activity

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.companyview.R
import com.companyview.databinding.ActivityMainBinding
import com.companyview.presentation.base.BaseActivity
import com.companyview.presentation.ui.company.CompanyListViewFragment
import com.companyview.util.isOnline
import com.google.android.material.snackbar.Snackbar


class MainActivity : BaseActivity() {

    private lateinit var sortAndSearchViewModel: SortAndSearchViewModel
    private var isAscending = true
    lateinit var binding: ActivityMainBinding

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        if(isOnline(applicationContext)){
            supportFragmentManager.beginTransaction().replace(
                R.id.main_frame, CompanyListViewFragment()).commit()
        }else{
            Snackbar.make(binding.root, getString(R.string.network_connections_txt), Snackbar.LENGTH_LONG).show()
        }
        sortAndSearchViewModel = getActivityScopeViewModel()
        binding.dataSearchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                println("onQueryTextChange $newText")
                sortAndSearchViewModel.setSearchFilter(newText!!)
                return true
            }

        })
        binding.dataSortView.setOnClickListener {
            clearSearchView()
            isAscending = !isAscending
            sortAndSearchViewModel.setAscending(!isAscending)
        }
    }

    override fun onResume() {
        super.onResume()
        sortAndSearchViewModel.enableMemberSortSpinner.observe(this, Observer {
            clearSearchView()
            binding.memberSortSpinner.visibility = if(it)View.VISIBLE else View.INVISIBLE
        })
        val languages = resources.getStringArray(R.array.member_sor_options)
        val adapter = ArrayAdapter(this,
            android.R.layout.simple_spinner_item, languages)
            binding.memberSortSpinner.adapter = adapter
            binding.memberSortSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(p0: AdapterView<*>?) {

            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                clearSearchView()
                sortAndSearchViewModel.setSortType(p2)
            }
        }
    }

    private fun clearSearchView() {
        binding.dataSearchView.clearFocus()
        sortAndSearchViewModel.setSearchFilter("")
        binding.dataSearchView.onActionViewCollapsed()
    }
}