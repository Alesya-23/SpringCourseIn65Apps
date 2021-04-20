package com.coursein65apps.mynotfirstappkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.coursein65apps.mynotfirstappkotlin.data.Contact
import com.coursein65apps.mynotfirstappkotlin.data.ContactStorage
import kotlinx.android.synthetic.main.fragment_list_contact.*
import kotlinx.android.synthetic.main.fragment_list_contact.view.*

class ContactListFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_contact, container, false)
        activity?.title = "Список контактов"
        val item = view.findViewById<View>(R.id.item_list)
        item.setOnClickListener {
            onСlick()
        }
        return view
    }

    private fun onСlick() {
        val detailContact = ContactDetailsFragment()
        val bundle = Bundle()
        bundle.putInt("ID", 0)
        detailContact.arguments = bundle
        fragmentManager?.beginTransaction()?.addToBackStack(null)
            ?.replace(R.id.container, detailContact)?.commit()
    }
}