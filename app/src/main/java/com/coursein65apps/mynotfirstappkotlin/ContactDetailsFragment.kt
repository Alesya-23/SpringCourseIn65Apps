package com.coursein65apps.mynotfirstappkotlin

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.coursein65apps.mynotfirstappkotlin.data.Contact
import com.coursein65apps.mynotfirstappkotlin.databinding.ItemFragmentDetailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ID_ARGUMENT = "ID_ARGUMENT"

class ContactDetailsFragment : Fragment(R.layout.item_fragment_details) {
    private lateinit var viewBinding: ItemFragmentDetailsBinding
    private val contactDetailIdArgument by lazy {
        requireArguments().getInt(ID_ARGUMENT, -1)
    }
    private var buttonNotifyState = false

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = ItemFragmentDetailsBinding.bind(view)
        getPersonContact(contactDetailIdArgument)
        buttonState()
        viewBinding.buttonNotify.setOnClickListener { buttonCallReceiver() }
        requireActivity().title = getString(R.string.details_contact_title)
    }

    companion object {
        fun newInstance(contactId: Int, intent: Intent) = ContactDetailsFragment().apply {
            this.arguments = bundleOf(
                    ID_ARGUMENT to contactId
            )
            buttonNotifyState = intent.getBooleanExtra("BUTTON_NOTIFY_STATE", false)
        }
    }

    private fun getPersonContact(id: Int) {
        CoroutineScope(Dispatchers.IO).launch {
            val contact = (activity as MainActivity).getContactService().getContact(id)
            withContext(Dispatchers.Main) {
                setPersonData(contact)
            }
        }
    }

    private fun setPersonData(contact: Contact) {
        with(viewBinding) {
            fullName.text = contact.name
            description.text = contact.description
            emailOne.text = contact.emailOne
            emailTwo.text = contact.emailTwo
            telephoneOne.text = contact.telephoneOne
            telephoneTwo.text = contact.telephoneTwo
            contact.photoContact.let { photoContact.setImageResource(it) }
            dateBirthday.text = contact.dateBirthday
        }
    }

    private fun buttonState() {
        if (buttonNotifyState) {
            viewBinding.buttonNotify.text = getString(R.string.off_notification)
        } else viewBinding.buttonNotify.text = getString(R.string.on_notification)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun buttonCallReceiver() {
        val intent = Intent("contact.broadcast")
        intent.putExtra("ID", contactDetailIdArgument)
        if (viewBinding.buttonNotify.text == getString(R.string.off_notification)) {
            viewBinding.buttonNotify.text = getString(R.string.on_notification)
            (activity as MainActivity).getContactBroadcastReceiver().cancelAlarmNotificationContact((activity as MainActivity).applicationContext, intent)
            //отключаем опцию
        } else {
            // включаем
            viewBinding.buttonNotify.text = getString(R.string.off_notification)
            intent.putExtra("textNotification", viewBinding.fullName.text)
            (activity as MainActivity).getContactBroadcastReceiver().onReceive((activity as MainActivity).applicationContext, intent)
        }
    }
}