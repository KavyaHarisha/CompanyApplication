package com.companyview.presentation.ui.company

import androidx.recyclerview.widget.DiffUtil
import com.companyview.presentation.data.CompanyData

class CompanyListDiffCallback(
    private val mOldList: List<CompanyData>,
    private val mNewList: List<CompanyData>
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
        return mOldList[oldItemPosition].companyId === mNewList[newItemPosition].companyId
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