package com.companyview.presentation.ui.member

import androidx.recyclerview.widget.DiffUtil
import com.companyview.presentation.data.MemberData

class MemberListDiffCallback(
    private val mOldList: List<MemberData>,
    private val mNewList: List<MemberData>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldList.size
    }

    override fun getNewListSize(): Int {
        return mNewList.size
    }

    override fun areItemsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        return mOldList[oldItemPosition].memberId === mNewList[newItemPosition].memberId
    }

    override fun areContentsTheSame(
        oldItemPosition: Int,
        newItemPosition: Int
    ): Boolean {
        val (_, companyName) = mOldList[oldItemPosition]
        val (_, companyName1) = mNewList[newItemPosition]
        return companyName == companyName1
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {
        // Implement method if you're going to use ItemAnimator
        return super.getChangePayload(oldItemPosition, newItemPosition)
    }

}