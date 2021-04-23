package com.coursein65apps.mynotfirstappkotlin

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.coursein65apps.mynotfirstappkotlin.databinding.ItemFragmentDetailsBinding
import kotlinx.android.synthetic.main.item_fragment_details.*

class ContactDetailsFragment : Fragment(R.layout.item_fragment_details) {
    private lateinit var viewBinding: ItemFragmentDetailsBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewBinding = ItemFragmentDetailsBinding.inflate(layoutInflater)
        val id: Int? = arguments?.getInt("ID", 0)
        activity?.title = "Детали контактов"
    }

    fun newInstance(key: String?, value: Int): ContactDetailsFragment {
        val fragment = ContactDetailsFragment()
        val bundle = bundleOf()
        bundle.putInt(key, value)
        fragment.arguments = bundle
        return fragment
    }
}
