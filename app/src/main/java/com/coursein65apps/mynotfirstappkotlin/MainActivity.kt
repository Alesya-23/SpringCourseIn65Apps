package com.coursein65apps.mynotfirstappkotlin

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import android.widget.Toast
import com.coursein65apps.mynotfirstappkotlin.data.Contact

class MainActivity : AppCompatActivity() {
    private var mService: ContactService = ContactService()
    private var mBound: Boolean = false

    private val connection = object : ServiceConnection {

        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            val binder = service as ContactService.ContactBinder
            mService = binder.getService()
            mBound = true
        }

        override fun onServiceDisconnected(arg0: ComponentName) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val list = ContactListFragment.newInstance()
            supportFragmentManager.beginTransaction().addToBackStack(null)
                .replace(R.id.container, list).commit()
        }
    }

    override fun onStart() {
        super.onStart()
        // Bind to Service
        Intent(this, ContactService::class.java).also { intent ->
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }
        startService(Intent(this, ContactService::class.java))
    }

    override fun onStop() {
        super.onStop()
        unbindService(connection)
        mBound = false
    }

    fun getContactService(): ContactService {
        return mService
    }
}