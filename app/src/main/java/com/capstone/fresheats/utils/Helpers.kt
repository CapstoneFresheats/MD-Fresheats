package com.capstone.fresheats.utils

import android.widget.TextView
import com.google.gson.*
import java.text.DecimalFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

object Helpers {

    fun TextView.formatDate(value: String?) {
        this.text = convertISOTimeToDate(value.toString())
    }

    fun TextView.formatPrice(value: String) {
        try {
            val doubleValue = java.lang.Double.parseDouble(value)
            this.text = getCurrencyIDR(doubleValue)
        } catch (e: NumberFormatException) {
           
            this.text = "Invalid Price"
        }
    }

    private fun getCurrencyIDR(price: Double): String {
        val format = DecimalFormat("#,###,###")
        return "IDR " + format.format(price).replace(",".toRegex(), ".")
    }

    fun getDefaultGson(): Gson {
        return GsonBuilder()
            .excludeFieldsWithoutExposeAnnotation()
            .setDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            .registerTypeAdapter(Date::class.java, JsonDeserializer { json, _, _ ->
                val formatServer = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                formatServer.timeZone = TimeZone.getTimeZone("UTC")
                formatServer.parse(json.asString)
            })
            .registerTypeAdapter(Date::class.java, JsonSerializer<Date> { src, _, _ ->
                val format = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                format.timeZone = TimeZone.getTimeZone("UTC")
                if (src != null) {
                    JsonPrimitive(format.format(src))
                } else {
                    null
                }
            }).create()
    }

    private fun convertISOTimeToDate(isoTime: String?): String? {
        if (isoTime.isNullOrEmpty()) return null

        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'", Locale.ENGLISH)
        val convertedDate: Date?
        var formattedDate: String? = null

        try {
            convertedDate = sdf.parse(isoTime)
            formattedDate = SimpleDateFormat("MMM dd, HH:mm", Locale.ENGLISH).format(convertedDate!!)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return formattedDate
    }

}

