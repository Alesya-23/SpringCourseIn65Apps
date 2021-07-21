package com.coursein65apps.mynotfirstappkotlin

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

class ContactDetailsFragment : Fragment(R.layout.item_fragment_details) {
    private lateinit var viewBinding: ItemFragmentDetailsBinding
    private val contactDetailIdArgument by lazy {
        requireArguments().getInt(ID_ARGUMENT, -1)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = ItemFragmentDetailsBinding.bind(view)
        getPersonContact(contactDetailIdArgument)
        requireActivity().title = getString(R.string.details_contact_title)
    }

    companion object {
        fun newInstance(contactId: Int) = ContactDetailsFragment().apply {
            this.arguments = bundleOf(
                    ID_ARGUMENT to contactId
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
            contact.photoContact.let { photoContact.setImageResource(it) }
        }
    }
}
