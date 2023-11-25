package com.example.milkmobileapp.ui.editMilk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.example.milkmobileapp.DBHelper;
import com.example.milkmobileapp.DBManager;
import com.example.milkmobileapp.Milk;
import com.example.milkmobileapp.R;
import com.example.milkmobileapp.databinding.FragmentEditMilkBinding;

public class EditMilkFragment extends Fragment {

    private FragmentEditMilkBinding binding;

    private static final String ID = "ID";
    private int id;
    public static EditMilkFragment newInstance(int itemId) {
        EditMilkFragment fragment = new EditMilkFragment();
        Bundle args = new Bundle();
        args.putInt(ID, itemId);
        fragment.setArguments(args);
        return fragment;
    }

    public EditMilkFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            id = getArguments().getInt(ID);
            System.out.println("getting " + id);
        }
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEditMilkBinding.inflate(inflater, container, false);

        final TextView year = binding.textInputYear;
        final TextView cost = binding.textInputCost;
        final TextView production = binding.textInputProduction;
        final Button save = binding.buttonEditMilkSave;

        DBHelper DB = new DBHelper(this.getContext());
        DBManager manager = new DBManager(DB);
        Milk milk = manager.getRow(id);
        year.setText(String.valueOf(milk.Year));
        cost.setText(String.valueOf(milk.Cost));
        production.setText(String.valueOf(milk.Production));
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editMilk(v);
            }
        });
        return binding.getRoot();
    }



    private void editMilk(View v) {
        final EditText year = binding.textInputYear;
        final EditText cost = binding.textInputCost;
        final EditText production = binding.textInputProduction;
        int c = Integer.parseInt(cost.getText().toString());
        int y = Integer.parseInt(year.getText().toString());
        int p = Integer.parseInt(production.getText().toString());
        DBHelper DB = new DBHelper(this.getContext());
        DBManager manager = new DBManager(DB);
        Milk table = new Milk(0, y, c, p);
        manager.editRow(id, table);
        Bundle bundle = new Bundle();
        NavHostFragment.findNavController(EditMilkFragment.this)
                .navigate(R.id.action_editMilkFragment_to_nav_milk, bundle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}