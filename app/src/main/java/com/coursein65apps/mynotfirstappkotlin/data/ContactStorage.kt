package com.coursein65apps.mynotfirstappkotlin.data

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.database.Cursor.FIELD_TYPE_STRING
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.ContactsContract
import com.coursein65apps.mynotfirstappkotlin.R
import java.io.ByteArrayInputStream

@SuppressLint("StaticFieldLeak")
object ContactStorage {
    private lateinit var context: Context
    fun getContactList(): List<Contact> {
        val contentResolver: ContentResolver = context.contentResolver
        var cursor: Cursor? = null
        val contacts: MutableList<Contact> = mutableListOf()
        try {
            cursor = contentResolver.query(
                    ContactsContract.Contacts.CONTENT_URI,
                    null,
                    null,
                    null,
                    null
            )
            if (cursor != null) {
                while (cursor.moveToNext()) {
                    if (cursor.getType(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME_PRIMARY)) == FIELD_TYPE_STRING) {
                        val contactId = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID))
                        val contactName = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                        val cursorDate = contentResolver.query(ContactsContract.Data.CONTENT_URI, arrayOf(ContactsContract.CommonDataKinds.Event.DATA), ContactsContract.Data.CONTACT_ID + " = " + contactId + " AND " + ContactsContract.Data.MIMETYPE + " = '" + ContactsContract.CommonDataKinds.Event.CONTENT_ITEM_TYPE + "' AND " + ContactsContract.CommonDataKinds.Event.TYPE + " = " + ContactsContract.CommonDataKinds.Event.TYPE_BIRTHDAY, null, ContactsContract.Data.DISPLAY_NAME)
                        var contactDateBirthday = context.getString(R.string.the_field_contact_is_empty)
                        if (cursorDate?.moveToNext() == true) {
                            contactDateBirthday = cursorDate.getString(cursorDate.getColumnIndex(ContactsContract.CommonDataKinds.Event.START_DATE))
                        }
                        cursorDate?.close()
                        val photo = getContactPhoto(contactId)
                        val contactPhones: ArrayList<String> = getContactPhones(cursor, contactId)
                        val contactEmails: ArrayList<String> = getContactEmails(contactId)
                        val newContact = Contact(
                                id = contactId.toInt(),
                                name = contactName,
                                photoContact = photo,
                                telephoneOne = contactPhones[0],
                                telephoneTwo = contactPhones[1],
                                emailOne = contactEmails[0],
                                emailTwo = contactEmails[1],
                                description = "описание контакта",
                                dateBirthday = contactDateBirthday
                        )
                        contacts.add(newContact)
                    }
                }
            }
        } finally {
            cursor?.close()
        }
        return contacts
    }

    fun getContactId(contactId: Int): Contact {
        val contact = getContactList().find { it.id == contactId }
        if (contact != null) {
            return contact
        }
        return Contact(
                id = -1,
                name = "Not found",
                photoContact = BitmapFactory.decodeResource(context.resources, R.drawable.donut),
                telephoneOne = "89999999999",
                telephoneTwo = "89999999999",
                emailOne = "none",
                emailTwo = "none",
                description = "none",
                dateBirthday = "none"
        )
    }

    fun getContext(context: Context) {
        this.context = context
    }

    private fun getContactPhoto(contactId: String): Bitmap? {
        val contentResolver: ContentResolver = context.contentResolver
        val contactUri = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, contactId.toLong())
        val photoUri = Uri.withAppendedPath(contactUri, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY)
        val cursor: Cursor = contentResolver.query(photoUri, arrayOf(ContactsContract.Contacts.Photo.PHOTO), null, null, null)
                ?: return null
        try {
            if (cursor.moveToFirst()) {
                val data = cursor.getBlob(0)
                if (data != null) {
                    return BitmapFactory.decodeStream(ByteArrayInputStream(data))
                }
            }
        } finally {
            cursor.close()
        }
        return BitmapFactory.decodeResource(context.resources, R.drawable.donut)
    }

    private fun getContactPhones(cursor: Cursor, contactId: String): ArrayList<String> {
        var telephoneOne = ""
        var telephoneTwo = ""
        val contentUri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI
        val phoneContactId = ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        val number = ContactsContract.CommonDataKinds.Phone.NUMBER
        val contentResolver = context.contentResolver
        val hasPhoneNumber = Integer.parseInt(cursor.getString(
                cursor.getColumnIndex(
                        ContactsContract.Contacts.HAS_PHONE_NUMBER
                )
        ))
        if (hasPhoneNumber > 0) {
            val phoneCursor = contentResolver.query(contentUri, null, "$phoneContactId = ?", arrayOf(contactId), null)
            if (phoneCursor != null) {
                if (phoneCursor.moveToNext()) {
                    telephoneOne = phoneCursor.getString(phoneCursor.getColumnIndex(number))
                    telephoneTwo = if (phoneCursor.moveToNext()) {
                        phoneCursor.getString(phoneCursor.getColumnIndex(number))
                    } else {
                        context.getString(R.string.the_field_contact_is_empty)
                    }
                }
            }
            phoneCursor?.close()
        }
        return arrayListOf(telephoneOne, telephoneTwo)
    }

    private fun getContactEmails(contactId: String): ArrayList<String> {
        var emailOne = ""
        var emailTwo = ""
        val contentUri = ContactsContract.CommonDataKinds.Email.CONTENT_URI
        val emailContactId = ContactsContract.CommonDataKinds.Email.CONTACT_ID
        val data = ContactsContract.CommonDataKinds.Email.DATA
        val contentResolver = context.contentResolver
        val emailCursor = contentResolver.query(contentUri, null, "$emailContactId = ?", arrayOf(contactId), null)
        if (emailCursor != null) {
            if (emailCursor.moveToNext()) {
                emailOne = emailCursor.getString(emailCursor.getColumnIndex(data))
                emailTwo = if (emailCursor.moveToNext()) {
                    emailCursor.getString((emailCursor.getColumnIndex(data)))
                } else {
                    context.getString(R.string.the_field_contact_is_empty)
                }
            }
        }
        if (emailOne.isEmpty()) {
            emailOne = context.getString(R.string.the_field_contact_is_empty)
            emailTwo = context.getString(R.string.the_field_contact_is_empty)
        }
        emailCursor?.close()
        return arrayListOf(emailOne, emailTwo)
    }
}