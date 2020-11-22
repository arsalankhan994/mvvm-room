package com.cocooncreations.topstories.utils

import androidx.room.TypeConverter
import com.cocooncreations.topstories.data.model.MultiMediumEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.*

class CustomTypeConverter {

    /*
    As Room cannot compiler incase of custom object inside entity class
    so here we are defining our converters
    */
    companion object {
        @TypeConverter
        @JvmStatic
        fun multiMediumToObject(data: String?): List<MultiMediumEntity?>? {
            val gson = Gson()
            if (data == null) {
                return Collections.emptyList()
            }
            val listType: Type = object : TypeToken<List<MultiMediumEntity?>?>() {}.type
            return gson.fromJson<List<MultiMediumEntity?>>(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun multiMediumToString(myObjects: List<MultiMediumEntity?>?): String? {
            val gson = Gson()
            return gson.toJson(myObjects)
        }

        @TypeConverter
        @JvmStatic
        fun listToObject(data: String?): List<String?>? {
            val gson = Gson()
            if (data == null) {
                return Collections.emptyList()
            }
            val listType: Type = object : TypeToken<List<String?>?>() {}.type
            return gson.fromJson<List<String?>>(data, listType)
        }

        @TypeConverter
        @JvmStatic
        fun listToString(myObjects: List<String?>?): String? {
            val gson = Gson()
            return gson.toJson(myObjects)
        }
    }
}