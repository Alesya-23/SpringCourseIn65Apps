package com.coursein65apps.mynotfirstappkotlin.data

import android.graphics.Bitmap
import android.os.Parcelable
import androidx.annotation.DrawableRes
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
        val id: Int,
        val name: String,
        @DrawableRes
        val photoContact: Bitmap?,
        val telephoneOne: String,
        val telephoneTwo: String,
        val emailOne: String,
        val emailTwo: String,
        val description: String,
        val dateBirthday: String
) : Parcelable
