package com.companyview.presentation.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class NameData(
    @field:SerializedName("first")
    val memberFirstName: String,
    @field:SerializedName("last")
    val memberLastName: String
): Parcelable