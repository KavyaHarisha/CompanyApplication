package com.companyview.presentation.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "company")
data class CompanyData(
    @PrimaryKey
    @field:SerializedName("_id")
    val companyId: String,
    @field:SerializedName("company")
    val companyName: String,
    @field:SerializedName("website")
    val companyWebsite: String,
    @field:SerializedName("logo")
    val companyLogo: String,
    @field:SerializedName("about")
    val companyAbout: String,
    @field:SerializedName("members")
    val companyMembersList: List<MemberData>,
    var isFavorite:Boolean,
    var isFollow:Boolean
)