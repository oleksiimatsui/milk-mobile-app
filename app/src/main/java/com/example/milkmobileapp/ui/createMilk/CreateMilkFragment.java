package com.example.milkmobileapp.ui.createMilk;

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
import com.example.milkmobileapp.databinding.FragmentCreateMilkBinding;

public class CreateMilkFragment extends Fragment {

    private FragmentCreateMilkBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCreateMilkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final Button save = binding.buttonCreateMilkSave;

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                сreateMilk(v);
            }
        });

        return root;
    }

    private void сreateMilk(View v) {
        final EditText year = binding.textInputYear;
        final EditText cost = binding.textInputCost;
        final EditText production = binding.textInputProduction;
        int c = Integer.parseInt(cost.getText().toString());
        int y = Integer.parseInt(year.getText().toString());
        int p = Integer.parseInt(production.getText().toString());
        DBHelper DB = new DBHelper(this.getContext());
        DBManager manager = new DBManager(DB);
        Milk table = new Milk(0, y, c, p);
        manager.addRow(table);
        Bundle bundle = new Bundle();
        NavHostFragment.findNavController(CreateMilkFragment.this)
                .navigate(R.id.action_editMilk_to_nav_milk, bundle);
    }

    public void saveChanges(View view){
        return;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}