package com.cocooncreations.topstories.utils

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

class DateUtil {

    /*
    As the publish date is not human readable
    so here we are updating the date format to human readable
    */
    companion object {
        fun changeDateFormat(time: String?): String? {
            val currentFormat = "yyyy-MM-dd'T'HH:mm:ss"
            val newDateFormat = "EEE MMM dd HH:mm:ss yyyy"
            val inputFormat = SimpleDateFormat(currentFormat)
            val outputFormat = SimpleDateFormat(newDateFormat)
            var date: Date? = null
            var str: String? = null
            try {
                date = inputFormat.parse(time)
                str = outputFormat.format(date)
            } catch (e: ParseException) {
                e.printStackTrace()
            }
            return str
        }
    }
}