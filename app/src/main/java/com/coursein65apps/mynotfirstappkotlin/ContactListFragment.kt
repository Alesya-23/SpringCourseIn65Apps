package com.coursein65apps.mynotfirstappkotlin

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.coursein65apps.mynotfirstappkotlin.data.Contact
import com.coursein65apps.mynotfirstappkotlin.data.ContactStorage
import com.coursein65apps.mynotfirstappkotlin.databinding.FragmentListContactBinding
import kotlinx.coroutines.*

private const val REQUEST_CODE_READ_CONTACTS = 1

class ContactListFragment : Fragment(R.layout.fragment_list_contact) {
    private var viewListBinding: FragmentListContactBinding? = null
    private var listContact: List<Contact>? = null
    private var READ_CONTACTS_GRANTED = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewListBinding = FragmentListContactBinding.bind(view)
        viewListBinding?.itemList?.itemFragmentContactListRoot?.setOnClickListener {
            onClick()
        }
        activity?.applicationContext?.let { ContactStorage.getContext(it) }
        checkPermission()
        requireActivity().title = getString(R.string.list_contact_title)
    }

    private fun onClick() {
        val contactId = 0
        val detailContact = activity?.let {
            listContact?.get(contactId)
                    ?.let { it1 -> ContactDetailsFragment.newInstance(it1.id, false) }
        }
        if (detailContact != null) {
            requireActivity().supportFragmentManager.beginTransaction()
                    .addToBackStack(ContactDetailsFragment::class.java.simpleName)
                    .replace(R.id.container, detailContact)
                    .commit()
        }
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
            withContext(Dispatchers.Main) {
                listContact?.get(0)?.let { loadItemContactList(it) }
            }
        }
    }

    private fun checkPermission() {
        // получаем разрешения
        val hasReadContactPermission = ContextCompat.checkSelfPermission(
                (activity as MainActivity),
                Manifest.permission.READ_CONTACTS
        )
        // если устройство до API 23, устанавливаем разрешение
        if (hasReadContactPermission == PackageManager.PERMISSION_GRANTED) {
            READ_CONTACTS_GRANTED = true
        } else {
            // вызываем диалоговое окно для установки разрешений
            ActivityCompat.requestPermissions(
                    (activity as MainActivity),
                    arrayOf(Manifest.permission.READ_CONTACTS),
                    REQUEST_CODE_READ_CONTACTS
            )
        }
        // если разрешение установлено, загружаем контакты
        if (READ_CONTACTS_GRANTED) {
            getListContact()
        }
    }

    fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: ArrayList<String>,
            grantResults: IntArray
    ) {
        if (requestCode == REQUEST_CODE_READ_CONTACTS) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                READ_CONTACTS_GRANTED = true
            }
        }
        if (READ_CONTACTS_GRANTED) {
            getListContact()
        } else {
            Toast.makeText(requireActivity(), getString(R.string.required_install_permission), Toast.LENGTH_LONG)
                    .show()
        }
    }

    private fun loadItemContactList(contact: Contact) {
        with(viewListBinding) {
            this?.itemList?.fullName?.text = contact.name
            this?.itemList?.telephone?.text = contact.telephoneOne
            this?.itemList?.photoContact?.setImageBitmap(contact.photoContact)
        }
    }
}
