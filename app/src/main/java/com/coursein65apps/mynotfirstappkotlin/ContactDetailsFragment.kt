package com.coursein65apps.mynotfirstappkotlin

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.coursein65apps.mynotfirstappkotlin.data.Contact
import com.coursein65apps.mynotfirstappkotlin.databinding.ItemFragmentDetailsBinding
import kotlinx.coroutines.*


class ContactDetailsFragment : Fragment(R.layout.item_fragment_details) {
    private lateinit var viewBinding: ItemFragmentDetailsBinding
    private var ID_ARGUMENT = "ID"
    private lateinit var contact: Contact

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = ItemFragmentDetailsBinding.bind(view)
        //   getDataPerson()
        requireActivity().title = getString(R.string.details_contact_title)
    }

    companion object {
        fun newInstance(contactId: Int) = ContactDetailsFragment().apply {
            this.arguments = bundleOf(
                ID_ARGUMENT to contactId
            )
                    getPersonContact(contactId)
        }
    }

    fun getPersonContact(id: Int) {
        CoroutineScope(Dispatchers.Default).launch {
           contact =  (activity as MainActivity).getContactService().getContact(id)
        }
    }

    private fun getDataPerson() {
        viewBinding.fullName.text = contact.name
        viewBinding.description.text = contact.description
        viewBinding.emailOne.text = contact.emailOne
        viewBinding.emailTwo.text = contact.emailTwo
        viewBinding.telephoneOne.text = contact.telephoneOne
        viewBinding.telephoneTwo.text = contact.telephoneTwo
        viewBinding.photoContact.setImageResource(contact.photoContact)
    }
}
