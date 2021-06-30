package com.coursein65apps.mynotfirstappkotlin.data

import com.coursein65apps.mynotfirstappkotlin.R

object ContactStorage {
    fun getContactList(): List<Contact> {
        return listOf(
            Contact(
                0,
                "Masha Derevina",
                R.drawable.donut,
                "89603456788",
                "89603456348",
                "morscaya@mail.ru",
                "masha_der@rambler.com",
                "dentist"
            ),
            Contact(
                1,
                "Alexey Ivanov",
                R.drawable.donut,
                "89605678988",
                "89605678984",
                "ivanov@mail.ru",
                "al_iv@rambler.com",
                "engineer"
            )
        )
    }
}