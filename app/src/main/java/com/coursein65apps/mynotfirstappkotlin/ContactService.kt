package com.coursein65apps.mynotfirstappkotlin

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import com.coursein65apps.mynotfirstappkotlin.data.Contact
import com.coursein65apps.mynotfirstappkotlin.data.ContactStorage

class ContactService : Service(), IContactService {
    private val binder = ContactBinder()

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    inner class ContactBinder : Binder() {
        fun getService(): ContactService = this@ContactService
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return super.onStartCommand(intent, flags, startId) // do work
    }

    override suspend fun getListContact(): List<Contact> {
        return ContactStorage.getContactList()
    }

    override suspend fun getContact(id: Int): Contact {
        return ContactStorage.getContactId(id)
    }
}