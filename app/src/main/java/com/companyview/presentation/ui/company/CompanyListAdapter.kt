package com.companyview.presentation.ui.company

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.companyview.binding.bindImageFromUrl
import com.companyview.databinding.CompanyListItemRowBinding
import com.companyview.presentation.data.CompanyData
import kotlinx.android.synthetic.main.company_list_item_row.view.*
import java.util.*
import kotlin.collections.ArrayList


class CompanyListAdapter(private var mCompanyList: List<CompanyData>) :
    ListAdapter<CompanyData, CompanyListAdapter.ViewHolder>(DiffCallback()), Filterable {

    var mFilteredCompanyList: List<CompanyData> = mCompanyList
    private var _listItemClickListener = MutableLiveData<CompanyData>()
    var mListItemClickListener: LiveData<CompanyData> = _listItemClickListener


    class ViewHolder(private val companyListItemRowBinding: CompanyListItemRowBinding) :
        RecyclerView.ViewHolder(companyListItemRowBinding.root) {
        fun bind(data: CompanyData) {
            companyListItemRowBinding.apply {
                bindImageFromUrl(companyListItemRowBinding.companyItemLogo, data.companyLogo)
                companyData = data
            }
        }
    }

    fun updateList(companyList: List<CompanyData>) {
        if(companyList.isNotEmpty()){
            val diffCallback =
                CompanyListDiffCallback(mCompanyList,companyList)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            (mCompanyList as ArrayList<CompanyData>).clear()
            (mCompanyList as ArrayList<CompanyData>).addAll(companyList)
            diffResult.dispatchUpdatesTo(this)
            submitList(mCompanyList)
            notifyDataSetChanged()
        }
    }

    fun listAscendingOrDescending(isAscending:Boolean) {
        val companyList = if(isAscending)
            mCompanyList.sortedBy { company -> company.companyName }
        else
            mCompanyList.sortedByDescending { company -> company.companyName }
        updateList(companyList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CompanyListItemRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val companyData = getItem(position)
        holder.apply { bind(companyData) }
        holder.itemView.company_item_favorite.setOnClickListener {
            val list = mCompanyList.toMutableList()
            companyData.isFavorite = !companyData.isFavorite
            list[holder.absoluteAdapterPosition] = companyData
            mCompanyList = list
            notifyItemChanged(holder.absoluteAdapterPosition)
        }
        holder.itemView.company_item_follow.setOnClickListener {
            val list = mCompanyList.toMutableList()
            companyData.isFollow = !companyData.isFollow
            list[holder.absoluteAdapterPosition] = companyData
            mCompanyList = list
            notifyItemChanged(holder.absoluteAdapterPosition)
        }
        holder.itemView.company_card_view.setOnClickListener {
            _listItemClickListener.postValue(mCompanyList[holder.absoluteAdapterPosition]) }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                mFilteredCompanyList = if (charString.isEmpty()) {
                    mCompanyList
                } else {

                    val filteredList = mCompanyList
                        .filter {
                            it.companyName.toLowerCase(Locale.getDefault()).contains(charString)
                        }
                        .toMutableList()
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = mFilteredCompanyList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults) {
                submitList(filterResults.values as List<CompanyData>)
                notifyDataSetChanged()
            }
        }
    }
}

private class DiffCallback : DiffUtil.ItemCallback<CompanyData>() {

    override fun areItemsTheSame(oldItem: CompanyData, newItem: CompanyData): Boolean {
        return oldItem.companyId == newItem.companyId
    }

    override fun areContentsTheSame(oldItem: CompanyData, newItem: CompanyData): Boolean {
        return oldItem.companyName == newItem.companyName &&
                oldItem.companyAbout == newItem.companyAbout &&
                oldItem.companyWebsite == newItem.companyWebsite
    }

}