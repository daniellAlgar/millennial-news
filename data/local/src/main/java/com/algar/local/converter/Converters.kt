package com.algar.local.converter

import androidx.room.TypeConverter
import com.algar.local.util.toDate
import com.algar.local.util.toIsoString
import com.algar.model.Source
import com.google.gson.Gson
import org.joda.time.DateTime
import org.koin.java.KoinJavaComponent.inject

/**
 * Use this class to convert [DateTime]s into/out of a database.
 */
class Converters {

    private val gson: Gson by inject(Gson::class.java)

    @TypeConverter
    fun dateTimeFromString(date: String?): DateTime? = date?.toDate()

    @TypeConverter
    fun dateTimeToString(date: DateTime?): String? = date?.toIsoString()

    @TypeConverter
    fun sourceToString(source: Source): String = gson.toJson(source)

    @TypeConverter
    fun sourceFromString(source: String): Source = gson.fromJson(source, Source::class.java)
}