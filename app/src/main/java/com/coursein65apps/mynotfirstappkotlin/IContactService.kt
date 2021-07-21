package com.coursein65apps.mynotfirstappkotlin

import com.coursein65apps.mynotfirstappkotlin.data.Contact

interface IContactService {
    suspend fun getListContact(): List<Contact> {
        return emptyList()
    }

    suspend fun getContact(id: Int): Contact? {
        return null
    }
}