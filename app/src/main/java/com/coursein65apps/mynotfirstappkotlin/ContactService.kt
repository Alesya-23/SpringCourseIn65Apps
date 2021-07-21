package com.coursein65apps.mynotfirstappkotlin

import android.app.Service
import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Binder
import android.os.IBinder
import com.coursein65apps.mynotfirstappkotlin.data.Contact
import com.coursein65apps.mynotfirstappkotlin.data.ContactStorage

class ContactService : Service(), IContactService {
    private var binder = ContactBinder()
    private var mBound: Boolean = false

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            binder = service as ContactService.ContactBinder
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    fun getConnection(): ServiceConnection {
        return connection
    }

    fun getBinder(): ContactBinder {
        return binder
    }

    override fun onBind(intent: Intent?): IBinder? {
        return binder
    }

    fun unBind() {
        mBound = false
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
