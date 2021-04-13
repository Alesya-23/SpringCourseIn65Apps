package com.coursein65apps.mynotfirstappkotlin.data

import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.annotation.NonNull
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Contact(
    @NonNull
    val id: Int,
    @NonNull
    val name: String,
    @DrawableRes
    val photo_contact: Int,
    @NonNull
    val telephone_one: String,
    val telephone_two: String,
    @NonNull
    val email_one: String,
    val email_two: String,
    val description: String
) :
    Parcelable