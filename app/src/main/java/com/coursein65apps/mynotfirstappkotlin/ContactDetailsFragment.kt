package com.coursein65apps.mynotfirstappkotlin

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.coursein65apps.mynotfirstappkotlin.data.Contact
import com.coursein65apps.mynotfirstappkotlin.databinding.ItemFragmentDetailsBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

private const val ID_ARGUMENT = "ID_ARGUMENT"
private const val ID = "ID"
private const val BUTTON_STATE = "BUTTON_STATE"
private const val TEXT_NOTIFICATION = "TEXT_NOTIFICATION"
private const val BROADCAST_ACTION = "contact.broadcast"

class ContactDetailsFragment : Fragment(R.layout.item_fragment_details) {
    private lateinit var viewBinding: ItemFragmentDetailsBinding
    private val contactDetailIdArgument by lazy {
        requireArguments().getInt(ID_ARGUMENT, -1)
    }
    private var buttonNotifyState: Boolean? = null


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = ItemFragmentDetailsBinding.bind(view)
        getPersonContact(contactDetailIdArgument)
        buttonNotifyState = requireArguments().getBoolean(BUTTON_STATE)
        buttonState()
        viewBinding.buttonNotify.setOnClickListener { buttonCallReceiver() }
        requireActivity().title = getString(R.string.details_contact_title)
    }

    companion object {
        fun newInstance(contactId: Int, buttonNotifyState: Boolean) =
                ContactDetailsFragment().apply {
                    this.arguments = bundleOf(
                            ID_ARGUMENT to contactId,
                            BUTTON_STATE to buttonNotifyState,
                    )
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
            contact.photoContact.let { photoContact.setImageBitmap(it) }
            dateBirthday.text = contact.dateBirthday
        }
    }

    private fun buttonState() {
        viewBinding.buttonNotify.text = getString(
                if (buttonNotifyState == true) {
                    R.string.off_notification
                } else R.string.on_notification
        )
    }

    private fun buttonCallReceiver() {
        val intent = Intent(BROADCAST_ACTION)
        intent.putExtra(ID, contactDetailIdArgument)
        if (buttonNotifyState == true) {
            viewBinding.buttonNotify.text = getString(R.string.on_notification)
            (activity as MainActivity).getContactBroadcastReceiver().cancelAlarmNotificationContact(
                    (activity as MainActivity).applicationContext,
                    intent
            )
            buttonNotifyState = false
            //отключаем опцию
        } else {
            // включаем
            viewBinding.buttonNotify.text = getString(R.string.off_notification)
            intent.putExtra(TEXT_NOTIFICATION, viewBinding.fullName.text)
            (activity as MainActivity).getContactBroadcastReceiver()
                    .onReceive((activity as MainActivity).applicationContext, intent)
            buttonNotifyState = true
        }
    }
}
