package com.coursein65apps.mynotfirstappkotlin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.coursein65apps.mynotfirstappkotlin.data.Contact
import com.coursein65apps.mynotfirstappkotlin.databinding.FragmentListContactBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactListFragment : Fragment(R.layout.fragment_list_contact) {
    private var viewListBinding: FragmentListContactBinding? = null
    private var listContact: List<Contact>? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewListBinding = FragmentListContactBinding.bind(view)
        viewListBinding?.itemList?.itemFragmentContactListRoot?.setOnClickListener {
            onClick()
        }
        getListContact()
        requireActivity().title = getString(R.string.list_contact_title)
    }

    private fun onClick() {
        val contactId = 0
        val detailContact = ContactDetailsFragment.newInstance(contactId)
        requireActivity().supportFragmentManager.beginTransaction()
            .addToBackStack(ContactDetailsFragment::class.java.simpleName)
            .replace(R.id.container, detailContact)
            .commit()
    }

    companion object {
        fun newInstance(): ContactListFragment {
            val args = Bundle()
            val fragment = ContactListFragment()
            fragment.arguments = args
            return fragment
        }
    }

    private fun getListContact() {
        CoroutineScope(Dispatchers.IO).launch {
            listContact = (activity as MainActivity).getContactService().getListContact()
        }
    }
}