package com.companyview.presentation.ui.activity

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class SortAndSearchViewModel  @Inject constructor():ViewModel() {

    private val _isAscending = MutableLiveData<Boolean>()
    val isAscending: LiveData<Boolean>
        get() = _isAscending

    fun setAscending(isAscending: Boolean) {
        _isAscending.value = isAscending
    }

    private val _searchFilter = MutableLiveData<String>()
    val searchFilter: LiveData<String>
        get() = _searchFilter

    fun setSearchFilter(filterText:String){
        _searchFilter.value = filterText
    }

    private val _enableMemberSortSpinner = MutableLiveData<Boolean>()
    val enableMemberSortSpinner: LiveData<Boolean>
        get() = _enableMemberSortSpinner

    fun setSpinnerEnable(spinnerEnable:Boolean){
        _enableMemberSortSpinner.value = spinnerEnable
    }

    private val _memberSortType = MutableLiveData<Int>()
    val memberSortType: LiveData<Int>
        get() = _memberSortType

    fun setSortType(sortType:Int){
        _memberSortType.value = sortType
    }
}