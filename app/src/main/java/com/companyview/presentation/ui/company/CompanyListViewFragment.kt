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

    //TODO fake data for testing purpose, use this data if not able to receive the data from server
    fun dumyData():List<CompanyData>{
        val dumyList = arrayListOf<CompanyData>()
        dumyList.add(
            CompanyData("38984398493","XYVH","https://google.com",
                "","jdkjfdksjfkdjfkdjkfjdkfjkdsjfkdsjfkdsjkfjdksjfksddkfjskdjflskajfdklsjafklajfkjfklajfklsjafdkjsaklfjsd" +
                        "dfkslfjdksjfklsdajflkdjslkfjdslkafjldksjfkdsjafkldjslfkjalfjdslkajzfdlkjflkjaslkfjsdlkfds",
                prepareMemberData(),false,false)
        )
        dumyList.add(
            CompanyData("93894839","ABDCS","https://google.com",
                "","AAAlklsldkslkdlslLllllldfklsjafkjdsalkfjslkajfdksajflksjdflkjsaflkjdslkafjlkdjfakjdflka" +
                        "dkslf;djsaklfjdkslajflkdsjflkjdskafjdlksajflkdsajflkjslkafjlksdjflksdjflk",
                prepareMemberData(),false,false)
        )
        dumyList.add(
            CompanyData("83749389","KUEYE","https://google.com",
                "","MMMMMMIIIIIIkldkdflekdlf",
                prepareMemberData(),false,false)
        )
        dumyList.add(
            CompanyData("7938482983","CYSXYY","https://google.com",
                "","PPPPPPPPPPPPPDKJSFJSKFJkjwkJKJEFKJ",
                prepareMemberData(),false,false)
        )
        dumyList.add(
            CompanyData("99938938","NUTYEII","https://google.com",
                "","VVVVVVVVVVVVVJDKFJEKJFKDJFEKkjdksjfeijfekdsad",
                prepareMemberData(),false,false)
        )
        dumyList.add(
            CompanyData("9349389","MMMMDIUREIUREI","https://google.com",
                "","DDDDDkddjskajfeowfkoafjeiasfidosjefiowafdsk",
                prepareMemberData(),false,false)
        )
        return dumyList
    }

    private fun prepareMemberData(): List<MemberData> {
        val memberList = arrayListOf<MemberData>()
        memberList.add(MemberData(
            "5c5bb5ce9ea1ae34c3d4f0c7", 26,
            NameData("Heather", "Russell"),
            "heather.russell@undefined.info",
            "+1 (827) 549-3643",false))
        memberList.add(MemberData(
            "5c5bb5ce591915de62e56bb2", 30,
            NameData("Velma", "Hayden"),
            "velma.hayden@undefined.me",
            "+1 (989) 537-3158",false))
        memberList.add(MemberData(
            "5c5bb5cee24406f77af3c0d1", 30,
            NameData("Solomon", "Griffith"),
            "solomon.griffith@undefined.biz",
            "+1 (856) 511-3589",false))
        memberList.add(MemberData(
            "5c5bb5ceec5d62903407ab9a", 24,
            NameData("Keller", "Rasmussen"),
            "keller.rasmussen@undefined.net",
            "+1 (984) 488-2723",false))
        memberList.add(MemberData(
            "5c5bb5cef7469c98a1e8f320", 24,
            NameData("Stacie", "Cardenas"),
            "stacie.cardenas@undefined.com",
            "+1 (848) 487-2881",false))
        return memberList
    }
}