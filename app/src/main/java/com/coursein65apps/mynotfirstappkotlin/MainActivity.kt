package com.coursein65apps.mynotfirstappkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var mService: ContactService = ContactService()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            val list = ContactListFragment.newInstance()
            supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, list)
                    .commit()
        }
    }

    override fun onStart() {
        super.onStart()
        // Bind to Service
        mService = mService.getBinder().getService()
        Intent(this, ContactService::class.java).also { intent ->
            bindService(intent, mService.getConnection(), Context.BIND_AUTO_CREATE)
        }
        startService(Intent(this, ContactService::class.java))
    }

    override fun onStop() {
        super.onStop()
        unbindService(mService.getConnection())
        mService.unBind()
    }

    fun getContactService(): ContactService {
        return mService
    }
}
