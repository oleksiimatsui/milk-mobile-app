package com.example.milkmobileapp.ui.contacts;

import static android.app.PendingIntent.getActivity;

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

    private static final String[] PROJECTION = new String[]{
            ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
            ContactsContract.Contacts.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
    };

    private void getContactList(ContentResolver cr) {
        contactList = new ArrayList<>();
        Cursor cursor;
        try{
            cursor = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, PROJECTION, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");
        }
        catch (Exception e){
            return;
        }
        if (cursor != null) {
            HashSet<String> mobileNoSet = new HashSet<String>();
            try {
                final int nameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int numberIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);

                String name, number;
                while (cursor.moveToNext()) {
                    name = cursor.getString(nameIndex);
                    number = cursor.getString(numberIndex);
                    number = number.replace(" ", "");
                    if (!mobileNoSet.contains(number)) {
                        contactList.add(new Contact(name, number));
                        mobileNoSet.add(number);
                        Log.d("hvy", "onCreaterrView  Phone Number: name = " + name
                                + " No = " + number);
                    }
                }
            } finally {
                cursor.close();
            }
        }
    }
}