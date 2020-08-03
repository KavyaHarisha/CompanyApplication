package com.companyview.data

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.companyview.presentation.data.MemberData

class Converters {

    @TypeConverter
    fun listToJson(value: List<MemberData>?) = Gson().toJson(value)

    @TypeConverter
    fun jsonToList(value: String) = Gson().fromJson(value, Array<MemberData>::class.java).toList()
}