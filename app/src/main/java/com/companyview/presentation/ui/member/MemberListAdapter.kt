package com.companyview.presentation.ui.member

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.companyview.databinding.MemberListItemRowBinding
import com.companyview.presentation.data.MemberData
import kotlinx.android.synthetic.main.member_list_item_row.view.*
import java.util.*
import kotlin.collections.ArrayList

class MemberListAdapter(private var mMemberList: List<MemberData>) :
    ListAdapter<MemberData, MemberListAdapter.ViewHolder>(MemberUtils()), Filterable {
    var mFilteredMemberList: List<MemberData> = mMemberList
    private var _listItemClickListener = MutableLiveData<MemberData>()
    var mListItemClickListener: LiveData<MemberData> = _listItemClickListener

    class ViewHolder(private val memberListItemRowBinding: MemberListItemRowBinding):
        RecyclerView.ViewHolder(memberListItemRowBinding.root) {
            fun  bind(data: MemberData){ memberListItemRowBinding.apply {
                    memberData = data } }
    }

    fun updateList(memberList: List<MemberData>) {
        if(memberList.isNotEmpty()){
            val diffCallback =
                MemberListDiffCallback(mMemberList,memberList)
            val diffResult = DiffUtil.calculateDiff(diffCallback)
            (mMemberList as ArrayList<MemberData>).clear()
            (mMemberList as ArrayList<MemberData>).addAll(memberList)
            diffResult.dispatchUpdatesTo(this)
            submitList(mMemberList)
            notifyDataSetChanged()
        }
    }

    fun memberListAscendingOrDescending(isAscending:Boolean,type:Int) {
        val memberList: List<MemberData>
        memberList = if(type == 0){
            if(isAscending)
                mMemberList.sortedBy { member -> member.memberNameData.memberFirstName }
            else
                mMemberList.sortedByDescending {  member -> member.memberNameData.memberFirstName }
        }else {
            if(isAscending)
                mMemberList.sortedBy { member -> member.memberAge }
            else
                mMemberList.sortedByDescending {  member -> member.memberAge }
        }
        updateList(memberList)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            MemberListItemRowBinding.inflate(
                LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val memberData = getItem(position)
        holder.apply { bind(memberData) }
        holder.itemView.member_favorite.setOnClickListener {
            _listItemClickListener.postValue(mMemberList[holder.absoluteAdapterPosition])
            val list = mMemberList.toMutableList()
            memberData.isFavorite = !memberData.isFavorite
            list[holder.absoluteAdapterPosition] = memberData
            mMemberList = list
            notifyItemChanged(holder.absoluteAdapterPosition)
        }
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(charSequence: CharSequence): FilterResults {
                val charString = charSequence.toString()
                mFilteredMemberList = if (charString.isEmpty()) {
                    mMemberList
                } else {
                    val filteredList = mMemberList
                        .filter {
                            it.memberNameData.memberFirstName.
                            toLowerCase(Locale.getDefault()).contains(charString)
                        }
                        .toMutableList()
                    filteredList
                }

                val filterResults = FilterResults()
                filterResults.values = mFilteredMemberList
                return filterResults
            }

            override fun publishResults(
                charSequence: CharSequence,
                filterResults: FilterResults
            ) {
                submitList(filterResults.values as List<MemberData>)
                notifyDataSetChanged()
            }
        }
    }
}

private class MemberUtils:DiffUtil.ItemCallback<MemberData>(){
    override fun areItemsTheSame(oldItem: MemberData, newItem: MemberData): Boolean {
        return oldItem.memberId == newItem.memberId
    }

    override fun areContentsTheSame(oldItem: MemberData, newItem: MemberData): Boolean {
        return oldItem.memberEmail == newItem.memberEmail
    }

}