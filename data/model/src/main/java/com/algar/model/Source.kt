package com.algar.model

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Source(
    val id: String?,
    val name: String
): Parcelable