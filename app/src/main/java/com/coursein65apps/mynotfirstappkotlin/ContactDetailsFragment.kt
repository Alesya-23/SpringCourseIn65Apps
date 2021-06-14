package com.coursein65apps.mynotfirstappkotlin

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.coursein65apps.mynotfirstappkotlin.databinding.ItemFragmentDetailsBinding
import kotlinx.android.synthetic.main.item_fragment_details.*

class ContactDetailsFragment : Fragment(R.layout.item_fragment_details) {
    private lateinit var viewBinding: ItemFragmentDetailsBinding
    private var ID_ARGUMENT = "ID"
    private val contactId: Int by lazy (LazyThreadSafetyMode.NONE) {
        requireArguments().getInt(ID_ARGUMENT, 0)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = ItemFragmentDetailsBinding.bind(view)
        val id: Int? = arguments?.getInt("ID", 0)
        requireActivity().title = getString(R.string.details_contact_title)
    }

    companion object {
        fun newInstance(key: String?, value: Int) = ContactDetailsFragment().apply {
            this.arguments = bundleOf(
                ID_ARGUMENT to contactId
            )
        }
    }

}
