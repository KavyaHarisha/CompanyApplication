package com.companyview.presentation.data

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class MemberData(
    @field:SerializedName("_id")
    val memberId: String,
    @field:SerializedName("age")
    val memberAge: Int,
    @field:SerializedName("name")
    val memberNameData: NameData,
    @field:SerializedName("email")
    val memberEmail: String,
    @field:SerializedName("phone")
    val memberPhone: String,
    var isFavorite:Boolean
) : Parcelable