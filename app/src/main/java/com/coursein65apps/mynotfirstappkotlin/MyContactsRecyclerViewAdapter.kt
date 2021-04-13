package com.coursein65apps.mynotfirstappkotlin

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.coursein65apps.mynotfirstappkotlin.data.Contact

class MyContactsRecyclerViewAdapter(Context: Context, private val contact: List<Contact>) :
    RecyclerView.Adapter<MyContactsRecyclerViewAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(Context)
    private var listener: onItemClickListener? = null
    private var currentId: Int? = null

    fun getСurrentId(): Int? {
        if (currentId != null) {
            return currentId
        }
        return -1
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(inflater.inflate(R.layout.item_fragment_contact_list, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemCount(): Int = contact.size

    private fun getItem(position: Int): Contact = contact[position]

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val photo: ImageView = itemView.findViewById(R.id.photo_contact)
        private val name: TextView = itemView.findViewById(R.id.fullName)
        private val telephone: TextView = itemView.findViewById(R.id.telephone)

        fun bind(version: Contact) {
            photo.setImageResource(version.photo_contact)
            name.text = version.name
            telephone.text = version.telephone_one
        }

        init {
            itemView.setOnClickListener(View.OnClickListener {
                listener?.let { listener ->
                    val position: Int = adapterPosition
                    if (position in 0..itemCount) {
                        listener.onСlick(contact[position])
                        currentId = contact[position].id
                    }
                }
            })
        }
    }

    fun setOnCLickListener(listener : onItemClickListener) {
        this.listener = listener
    }

    interface onItemClickListener {
        fun onСlick(itemList: Contact)
    }
}
