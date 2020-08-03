package com.companyview.presentation.ui.company

import androidx.lifecycle.ViewModel
import com.companyview.presentation.data.CompanyRepository
import javax.inject.Inject

class CompanyViewModel @Inject constructor(repository: CompanyRepository) : ViewModel() {

    val company= repository.company
}
