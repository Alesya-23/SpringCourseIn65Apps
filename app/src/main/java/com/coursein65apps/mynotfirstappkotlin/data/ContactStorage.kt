package com.coursein65apps.mynotfirstappkotlin.data

import com.coursein65apps.mynotfirstappkotlin.R

object ContactStorage {
    fun getContactList(): List<Contact> {
        return listOf(
            Contact(
                id = 0,
                name = "Masha Derevina",
                photoContact = R.drawable.donut,
                telephoneOne = "89603456788",
                telephoneTwo = "89603456348",
                emailOne = "morscaya@mail.ru",
                emailTwo = "masha_der@rambler.com",
                description = "dentist"
            ),
            Contact(
                id = 1,
                name = "Alexey Ivanov",
                photoContact = R.drawable.donut,
                telephoneOne = "89605678988",
                telephoneTwo = "89605678984",
                emailOne = "ivanov@mail.ru",
                emailTwo = "al_iv@rambler.com",
                description = "engineer"
            )
        )
    }

    fun getContactId(contactId: Int): Contact {
        var contact = getContactList().find { it.id == contactId }
        if (contact != null) {
            return contact
        }
        return Contact(
            id = -1,
            name = "Not found",
            photoContact = R.drawable.donut,
            telephoneOne = "89999999999",
            telephoneTwo = "89999999999",
            emailOne = "none",
            emailTwo = "none",
            description = "none"
        )
    }
}