package com.companyview.presentation.ui.company

import android.os.Bundle
import android.os.Parcelable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.companyview.R
import com.companyview.databinding.FragmentCompanyListViewBinding
import com.companyview.presentation.base.BaseFragment
import com.companyview.presentation.data.CompanyData
import com.companyview.presentation.data.MemberData
import com.companyview.presentation.data.NameData
import com.companyview.util.VerticalItemDecoration
import com.google.android.material.snackbar.Snackbar
import com.companyview.presentation.ui.activity.SortAndSearchViewModel
import com.companyview.presentation.ui.member.MemberListViewFragment
import dagger.android.support.AndroidSupportInjection
import com.companyview.data.Result
import java.util.ArrayList


class CompanyListViewFragment : BaseFragment() {

    private lateinit var viewModel: CompanyViewModel
    private lateinit var sortAndSearchViewModel: SortAndSearchViewModel
    lateinit var adapter: CompanyListAdapter
    lateinit var binding: FragmentCompanyListViewBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        AndroidSupportInjection.inject(this)
         binding = FragmentCompanyListViewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun initViewModels() {
        viewModel = getFragmentScopeViewModel()
        sortAndSearchViewModel = getActivityScopeViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = CompanyListAdapter(arrayListOf())
        binding.companyRecyclerView.addItemDecoration(
            VerticalItemDecoration(resources.getDimension(R.dimen.margin_normal).toInt(),
                true)
        )
        binding.companyRecyclerView.adapter = adapter
    }

    override fun subscribeToViewModels() {
        subscribeUi(binding,adapter)
        sortAndSearchViewModel.setSpinnerEnable(false)
        sortAndSearchViewModel.isAscending.observe(viewLifecycleOwner,
            Observer { adapter.listAscendingOrDescending(it) })
        sortAndSearchViewModel.searchFilter.observe(viewLifecycleOwner,
            Observer { adapter.filter.filter(it) })

        adapter.mListItemClickListener.observe(viewLifecycleOwner, Observer {
            val memberFragment = MemberListViewFragment()
            sortAndSearchViewModel.setSpinnerEnable(true)
            val bundle = Bundle()
            bundle.putParcelableArrayList("member_data",
                it.companyMembersList as ArrayList<out Parcelable>)
            memberFragment.arguments = bundle
            replaceFragment(R.id.main_frame,memberFragment) })
    }

    private fun subscribeUi(binding: FragmentCompanyListViewBinding, adapter: CompanyListAdapter) {
        viewModel.company.observe(viewLifecycleOwner, Observer { result ->
            when (result.status) {
                Result.Status.SUCCESS -> {
                    binding.companyProgressBar.hide()
                    result.data?.let {
                        adapter.updateList(it)
                    }
                }
                Result.Status.LOADING -> binding.companyProgressBar.show()
                Result.Status.ERROR -> {
                    binding.companyProgressBar.hide()
                    Snackbar.make(binding.root, result.message!!, Snackbar.LENGTH_LONG).show()
                }
            }
        })
    }
}