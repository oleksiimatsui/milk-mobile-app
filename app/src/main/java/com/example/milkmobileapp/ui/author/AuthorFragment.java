package com.example.milkmobileapp.ui.author;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.milkmobileapp.databinding.FragmentAuthorBinding;
import com.example.milkmobileapp.databinding.FragmentMapsBinding;

public class AuthorFragment extends Fragment {

    private FragmentAuthorBinding binding;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = FragmentAuthorBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }
}
