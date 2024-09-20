package com.example.psychoremastered.data.local.room.type_converter

import androidx.room.TypeConverter
import com.example.psychoremastered.data.local.room.entity.DegreeEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

object DegreeListConverter {

    @TypeConverter
    fun fromDegreeList(value: List<DegreeEntity>): String {
        return Gson().toJson(value)
    }

    @TypeConverter
    fun toDegreeList(value: String): List<DegreeEntity> {
        return try {
            Gson().fromJson<List<DegreeEntity>>(value)
        } catch (e: Exception) {
            arrayListOf()
        }
    }

    private inline fun <reified T> Gson.fromJson(json: String) =
        fromJson<T>(json, object : TypeToken<T>() {}.type)
}