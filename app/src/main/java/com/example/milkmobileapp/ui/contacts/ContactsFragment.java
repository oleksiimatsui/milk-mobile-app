package com.example.milkmobileapp.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.milkmobileapp.Contact;
import com.example.milkmobileapp.Milk;
import com.example.milkmobileapp.R;
import com.example.milkmobileapp.databinding.FragmentContactsBinding;
import com.example.milkmobileapp.ui.editMilk.EditMilkFragment;
import com.example.milkmobileapp.ui.map.MapsFragment;
import com.example.milkmobileapp.ui.milk.MilkAdapter;
import com.example.milkmobileapp.ui.milk.MilkFragment;


import java.text.DecimalFormat;
import java.util.ArrayList;

public class ContactsFragment extends Fragment {
    private FragmentContactsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ContactsViewModel contactsViewModel =
                new ViewModelProvider(this).get(ContactsViewModel.class);
        binding = FragmentContactsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        contactsViewModel.setList(this.getContext().getContentResolver());
        contactsViewModel.getList().observe(getViewLifecycleOwner(), listObserver);

        binding.filter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                contactsViewModel.setFilter(isChecked);
            }
        });

        return root;
    }
    final Observer<ArrayList<Contact>> listObserver = new Observer<ArrayList<Contact>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<Contact> newList) {
            setList(newList);
        }
    };
    public void setList(ArrayList<Contact> contacts){
        ListView listView = binding.list;
        ContactAdapter adapter = new ContactAdapter(this.getContext(), contacts, this);
        listView.setAdapter(adapter);
    }

    public void onContactClick(Contact contact){
        MapsFragment mapsFragment = MapsFragment.newInstance(contact);
        NavHostFragment.findNavController(ContactsFragment.this)
                .navigate(R.id.action_nav_contacts_to_mapsFragment, mapsFragment.getArguments());
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}