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
import org.jetbrains.annotations.NotNull

class ContactListFragment : Fragment() {
    @NotNull
    private var adapter: MyContactsRecyclerViewAdapter? = null

    @NotNull
    private var contact: List<Contact>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list_contact, container, false)
        val list: RecyclerView = view.findViewById(R.id.list)
        contact = ContactStorage.getContactList()
        adapter = MyContactsRecyclerViewAdapter(context!!, contact!!)
        list.adapter = adapter
        list.setHasFixedSize(true)
        list.layoutManager = LinearLayoutManager(view?.context)
        val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
        decoration.setDrawable(context?.let {
            ContextCompat.getDrawable(
                it,
                R.color.colorPrimary
            )
        }!!)
        list.addItemDecoration(decoration)
        onСlick()
        activity?.title = "Список контактов"
        return view
    }

     private fun onСlick() {
        adapter?.setOnCLickListener(object : MyContactsRecyclerViewAdapter.onItemClickListener {
            override fun onСlick(itemList: Contact) {
                if (contact != null) {
                    val detailContact = ContactDetailsFragment()
                    val bundle = Bundle()
                    if (adapter?.getСurrentId() != -1) {
                        adapter?.getСurrentId()?.let { bundle.putInt("ID", it) }
                        detailContact.arguments = bundle
                        fragmentManager?.beginTransaction()?.addToBackStack(null)
                            ?.replace(R.id.container, detailContact)?.commit()
                    }
                }
            }
        })
    }
}