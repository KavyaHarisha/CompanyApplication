package com.companyview.presentation.ui.member

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import com.companyview.R
import com.companyview.databinding.FragmentMemberListViewBinding
import com.companyview.presentation.base.BaseFragment
import com.companyview.presentation.data.MemberData
import com.companyview.presentation.ui.activity.SortAndSearchViewModel
import com.companyview.util.VerticalItemDecoration
import dagger.android.support.AndroidSupportInjection

class MemberListViewFragment: BaseFragment() {
    private lateinit var sortAndSearchViewModel: SortAndSearchViewModel
    lateinit var adapter: MemberListAdapter
    lateinit var binding: FragmentMemberListViewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        AndroidSupportInjection.inject(this)
        binding = FragmentMemberListViewBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun initViewModels() {
        sortAndSearchViewModel = getActivityScopeViewModel()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = MemberListAdapter(arrayListOf())
        binding.memberRecyclerView.addItemDecoration(
            VerticalItemDecoration(
                resources.getDimension(R.dimen.margin_normal).toInt(), true)
        )
        binding.memberRecyclerView.adapter = adapter
        adapter.updateList(
            arguments!!.getParcelableArrayList<MemberData>("member_data") as List<MemberData>)
    }

    override fun subscribeToViewModels() {
        super.subscribeToViewModels()
        sortAndSearchViewModel.searchFilter.observe(viewLifecycleOwner,
            Observer { adapter.filter.filter(it) })
        sortAndSearchViewModel.isAscending.observe(viewLifecycleOwner, Observer {
            adapter.memberListAscendingOrDescending(it,sortAndSearchViewModel.memberSortType.value!!)
        })
        adapter.mListItemClickListener.observe(viewLifecycleOwner, Observer {

        })
    }
}