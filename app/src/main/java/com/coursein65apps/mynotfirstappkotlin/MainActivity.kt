package com.coursein65apps.mynotfirstappkotlin

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.coursein65apps.mynotfirstappkotlin.data.ContactStorage

private const val CONTACT_DETAIL_ID = "CONTACT_DETAIL_ID"

class MainActivity : AppCompatActivity() {
    private var mService: ContactService = ContactService()
    private var mBroadcastReceiver: ContactBroadcastReceiver = ContactBroadcastReceiver()
    private var isContactBirthday = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            checkNotification()
            if (!isContactBirthday) {
                val list = ContactListFragment.newInstance()
                supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(R.id.container, list)
                    .commit()
            } else {
                openDetailsFragment()
            }
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

    fun getContactBroadcastReceiver(): ContactBroadcastReceiver {
        return mBroadcastReceiver
    }

    private fun checkNotification() {
        val contactDetailId = intent.getIntExtra(CONTACT_DETAIL_ID, -1)
        if (contactDetailId != -1) {
            isContactBirthday = true
        }
    }

    private fun openDetailsFragment() {
        val contactDetailsFragment = ContactDetailsFragment.newInstance(0, buttonNotifyState = true)
        this.supportFragmentManager.beginTransaction()
            .replace(R.id.container, contactDetailsFragment)
            .addToBackStack(ContactListFragment::class.java.simpleName)
            .commit()
    }
}
