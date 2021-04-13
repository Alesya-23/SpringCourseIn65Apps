package com.coursein65apps.mynotfirstappkotlin

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.coursein65apps.mynotfirstappkotlin.data.Contact
import com.coursein65apps.mynotfirstappkotlin.data.ContactStorage

class ContactDetailsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val id: Int? = arguments?.getInt("ID", 0)
        val list: List<Contact> = ContactStorage.getContactList()
        val fullName: TextView? = view?.findViewById(R.id.fullName)
        val photo: ImageView? = view?.findViewById(R.id.photo_contact)
        val telephoneOne: TextView? = view?.findViewById(R.id.telephone_one)
        val telephoneTwo: TextView? = view?.findViewById(R.id.telephone_two)
        val emailOne: TextView? = view?.findViewById(R.id.email_one)
        val emailTwo: TextView? = view?.findViewById(R.id.email_two)
        val description: TextView? = view?.findViewById(R.id.description)

        fullName?.text = id?.let { list[id].name }
        id?.let { list[id].photo_contact }?.let { photo?.setImageResource(it) }
        telephoneOne?.text = id?.let { list[id].telephone_one }
        telephoneTwo?.text = id?.let { list[id].telephone_two }
        emailOne?.text = id?.let { list[id].email_one }
        emailTwo?.text = id?.let { list[id].email_two }
        description?.text = id?.let { list[id].description }
        activity?.title = "Детали контактов"
        return inflater.inflate(R.layout.item_fragment_details, container, false)
    }

    fun newInstance(key: String?, value: Int): ContactDetailsFragment {
        val fragment = ContactDetailsFragment()
        val bundle = Bundle()
        bundle.putInt(key, value)
        fragment.arguments = bundle
        return fragment
    }
}
