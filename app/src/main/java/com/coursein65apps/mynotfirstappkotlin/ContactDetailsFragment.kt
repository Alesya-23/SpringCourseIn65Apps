package com.coursein65apps.mynotfirstappkotlin

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.coursein65apps.mynotfirstappkotlin.databinding.ItemFragmentDetailsBinding

class ContactDetailsFragment : Fragment(R.layout.item_fragment_details) {
    private lateinit var viewBinding: ItemFragmentDetailsBinding
    private var ID_ARGUMENT = "ID"

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = ItemFragmentDetailsBinding.bind(view)
        requireActivity().title = getString(R.string.details_contact_title)
    }

    companion object {
        fun newInstance(contactId: Int) = ContactDetailsFragment().apply {
            this.arguments = bundleOf(
                ID_ARGUMENT to contactId
            )
        }
    }

}