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
import androidx.lifecycle.ViewModelProvider;

import com.example.milkmobileapp.databinding.FragmentCreateMilkBinding;
import com.example.milkmobileapp.ui.home.HomeViewModel;

public class CreateMilkFragment extends Fragment {

    private FragmentCreateMilkBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        CreateMilkViewModel createMilkViewModel =
                new ViewModelProvider(this).get(CreateMilkViewModel.class);

        binding = FragmentCreateMilkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView year = binding.textInputYear;
        final TextView cost = binding.textInputCost;
        final TextView production = binding.textInputProduction;
        final Button save = binding.buttonCreateMilkSave;

        return root;
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