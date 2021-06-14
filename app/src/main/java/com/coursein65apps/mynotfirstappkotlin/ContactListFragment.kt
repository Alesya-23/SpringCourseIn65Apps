package com.coursein65apps.mynotfirstappkotlin

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.coursein65apps.mynotfirstappkotlin.databinding.FragmentListContactBinding


class   ContactListFragment : Fragment() {
    private lateinit var viewListBinding: FragmentListContactBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewListBinding = FragmentListContactBinding.bind(view)
        viewListBinding.itemList.itemFragmentContactListRoot.setOnClickListener {
            onСlick()
        }
        requireActivity().title = getString(R.string.list_contact_title)
    }

    private fun onСlick() {
        val contactId = 0
        val detailContact = ContactDetailsFragment.newInstance("ID", contactId)
        val bundle = Bundle()
        bundle.putInt("ID", 0)
        detailContact.arguments = bundle
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
}