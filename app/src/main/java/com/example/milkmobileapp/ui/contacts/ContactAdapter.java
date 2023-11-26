package com.example.milkmobileapp.ui.contacts;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import com.example.milkmobileapp.Contact;
import com.example.milkmobileapp.Milk;
import com.example.milkmobileapp.R;
import com.example.milkmobileapp.databinding.ContactsListItemBinding;
import com.example.milkmobileapp.databinding.MilkItemBinding;
import com.example.milkmobileapp.ui.milk.AdapterCallback;

import java.util.ArrayList;

public class ContactAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Contact> dataList;

    private ContactsFragment callback;

    public ContactAdapter(Context context, ArrayList<Contact> dataList, ContactsFragment callback) {
        this.context = context;
        this.dataList = dataList;
        this.callback = callback;
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.contacts_list_item, parent, false);
        }
        Contact contact = (dataList.get(position));

        TextView text = convertView.findViewById(R.id.contact_name);
        text.setText(contact.name + " " + contact.lastName);
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onContactClick(contact);
            }
        });
        return convertView;
    }

}