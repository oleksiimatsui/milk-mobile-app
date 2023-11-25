package com.example.milkmobileapp.ui.contacts;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.milkmobileapp.Contact;
import com.example.milkmobileapp.R;
import com.example.milkmobileapp.databinding.FragmentContactsBinding;
import com.example.milkmobileapp.ui.editMilk.EditMilkFragment;
import com.example.milkmobileapp.ui.map.MapsFragment;
import com.example.milkmobileapp.ui.milk.MilkFragment;


import java.util.ArrayList;

public class ContactsFragment extends Fragment {
    private FragmentContactsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ContactsViewModel slideshowViewModel =
                new ViewModelProvider(this).get(ContactsViewModel.class);
        binding = FragmentContactsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        slideshowViewModel.setList(this.getContext().getContentResolver());
        slideshowViewModel.getList().observe(getViewLifecycleOwner(), listObserver);
        return root;
    }
    final Observer<ArrayList<Contact>> listObserver = new Observer<ArrayList<Contact>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<Contact> newList) {
            // Update the UI, in this case, a TextView.
            ListView listView = binding.list;
            assert newList != null;
            String[] arr = new String[newList.size()];
            for (int i = 0; i < newList.size(); i++){
                arr[i] = newList.get(i).name;
            }
            ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arr);
            listView.setAdapter(itemsAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    // Тут ви виконуєте перехід до MapsFragment
                    MapsFragment mapsFragment = new MapsFragment();
                    NavHostFragment.findNavController(ContactsFragment.this)
                            .navigate(R.id.action_nav_contacts_to_mapsFragment);
                }
            });
        }
    };
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}