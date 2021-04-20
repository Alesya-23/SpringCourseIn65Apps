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
    val photoContact: Int,
    @NonNull
    val telephoneOne: String,
    val telephoneTwo: String,
    @NonNull
    val emailOne: String,
    val emailTwo: String,
    val description: String
) :
    Parcelable