package com.example.milkmobileapp.ui.contacts;

import static android.app.PendingIntent.getActivity;

import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.HashSet;

import com.example.milkmobileapp.Contact;

public class ContactsViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Contact>> mText;

    public ContactsViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<ArrayList<Contact>> getList() {
        return mText;
    }
    ArrayList<Contact> contactList = new ArrayList<>();
    public void setList(ContentResolver cr){
        getContactList(cr);
        mText.setValue(contactList);
    }

    @SuppressLint("Range")
    private void getContactList(ContentResolver cr) {
        contactList = new ArrayList<>();
        Cursor cursor;
        String[] projectionPhone = {
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER
        };
        Cursor cursorPhone = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, projectionPhone, null, null, null);
        String[] projectionAddress = {
                ContactsContract.CommonDataKinds.StructuredPostal.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS
        };

        // Query for addresses
        Cursor cursorAddress = cr.query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, projectionAddress, null, null, null);

        if (cursorPhone != null && cursorAddress != null) {
            try {
                while (cursorPhone.moveToNext()) {
                    String contactAddress;
                    if(cursorAddress.moveToNext()){
                        contactAddress = cursorAddress.getString(cursorAddress.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.StructuredPostal.FORMATTED_ADDRESS));
                    }else{
                        contactAddress = "";
                    }

                    String contactName = cursorPhone.getString(cursorPhone.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    String contactNumber = cursorPhone.getString(cursorPhone.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    Contact contact = new Contact(contactName, contactNumber, contactAddress);
                    contactList.add(contact);
                }
            } finally {
                cursorPhone.close();
                cursorAddress.close();
            }
        }
    }
}