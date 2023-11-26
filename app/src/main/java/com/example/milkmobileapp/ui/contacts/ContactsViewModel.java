package com.example.milkmobileapp.ui.contacts;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashSet;

import com.example.milkmobileapp.Contact;
import com.example.milkmobileapp.Milk;

public class ContactsViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Contact>> list;
    private final MutableLiveData<ArrayList<Contact>> sortedList;

    public ContactsViewModel() {
        list = new MutableLiveData<>();
        sortedList = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Contact>> getList() {
        return sortedList;
    }
    public void setList(ContentResolver cr){
        ArrayList<Contact> contactList = getContactList(cr);
        list.setValue(contactList);
        sortedList.setValue(contactList);
    }

    public void setFilter(Boolean bool){
        ArrayList<Contact> contacts = this.list.getValue();
        ArrayList<Contact> list;
        if(bool == true){
            list = new ArrayList<>();
            for (Contact contact : contacts) {
                if(contact.lastName.startsWith("Ми")){
                    list.add(contact);
                }
            }
        }else{
            list = contacts;
        }

        this.sortedList.setValue(list);
    }
    private static Cursor getNameCursorById(ContentResolver contentResolver,String contact_ID)
    {
        Uri addressUri = ContactsContract.Data.CONTENT_URI;
        String selection = ContactsContract.Data.MIMETYPE + " = ? AND " + ContactsContract.CommonDataKinds.StructuredName.CONTACT_ID + " = ?";
        String[] selectionArgs  = new String[] { ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE, contact_ID };;
        Cursor cursor = contentResolver.query(addressUri, null, selection, selectionArgs, null);
        return cursor;
    }
    private static Cursor getPostalCursorByLookUpKey(ContentResolver contentResolver,String lookUpKey)
    {
        Uri addressUri = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
        String selection = ContactsContract.CommonDataKinds.StructuredName.LOOKUP_KEY  + " = ?";
        String[] selectionArgs = { String.valueOf(lookUpKey) };
        Cursor cursorAddress = contentResolver.query(addressUri, null, selection, selectionArgs, null);
        return cursorAddress;
    }

    @SuppressLint("Range")
    private ArrayList<Contact> getContactList(ContentResolver cr) {
        ArrayList<Contact> contactList = new ArrayList<>();
        String[] projectionPhone = {
                ContactsContract.CommonDataKinds.Phone.NUMBER,
                ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY,
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID
        };
        Cursor cursorPhone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projectionPhone, null, null, null);

        if (cursorPhone != null) {
            try {
                while (cursorPhone.moveToNext()) {
                    //знаходимо номер телефону
                    String contactNumber = cursorPhone.getString(cursorPhone.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));

                    //беремо id контакту
                    String lookup = cursorPhone.getString(cursorPhone.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.LOOKUP_KEY));
                    String contactId = cursorPhone.getString(cursorPhone.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.CONTACT_ID));

                    //за id знаходимо інші поля
                    String contactAddress, contactName, contactLastName;
                    Cursor cursor = getPostalCursorByLookUpKey(cr, lookup);
                    if(cursor.moveToFirst()){
                        contactAddress = cursor.getString(cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
                    }else{
                        contactAddress = "address not found";
                    }
                    cursor.close();
                    Cursor cursorName = getNameCursorById(cr, contactId);
                    if(cursorName.moveToFirst()){
                        contactLastName = cursorName.getString(cursorName.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredName.FAMILY_NAME));
                        contactName = cursorName.getString(cursorName.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME));
                    }else{
                        contactLastName = "name not found";
                        contactName = "name not found";
                    }
                    if(contactLastName == null){
                        contactLastName = "";
                    }
                    if(contactName == null){
                        contactName = "";
                    }
                    if(contactAddress == null){
                        contactAddress = "";
                    }
                    Contact contact = new Contact(contactName, contactNumber, contactAddress, contactLastName);
                    contactList.add(contact);
                }
            } finally {
                cursorPhone.close();
            }
        }
        return contactList;
    }
}