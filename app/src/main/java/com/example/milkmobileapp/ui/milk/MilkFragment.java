package com.example.milkmobileapp.ui.milk;

import static android.view.View.generateViewId;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.milkmobileapp.DBHelper;
import com.example.milkmobileapp.DBManager;
import com.example.milkmobileapp.Milk;
import com.example.milkmobileapp.R;
import com.example.milkmobileapp.databinding.FragmentMilkBinding;

import java.util.ArrayList;

public class MilkFragment extends Fragment {

    private FragmentMilkBinding binding;

    private TextView label(String text){
        TextView label = new TextView(this.getContext());    // part3
        label.setId( generateViewId ());
        label.setText(text);
        //label.setBackgroundResource(R.drawable.border);
        label.setPadding(10, 10, 10, 10);
        LinearLayout.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,5f);
        params.weight = 1;
        label.setLayoutParams(params);
        return label;
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        MilkViewModel galleryViewModel =
                new ViewModelProvider(this).get(MilkViewModel.class);

        binding = FragmentMilkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DBHelper DB = new DBHelper(this.getContext());
        DBManager manager = new DBManager(DB);

        ArrayList<Milk> milks = manager.getTable();

        TableRow tr_head = new TableRow(this.getContext());
        tr_head.setId(generateViewId());
        tr_head.setBackgroundColor(Color.GRAY);        // part1
        tr_head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TableLayout table = (TableLayout) ( root.findViewById(R.id.milk_table));
        tr_head.setWeightSum(4);
        table.addView(tr_head);

        tr_head.addView(label(String.valueOf("Year")));
        tr_head.addView(label(String.valueOf("Name")));
        tr_head.addView(label(String.valueOf("Production")));
        tr_head.addView(label(String.valueOf("Cost")));

        milks.forEach(milk -> {
            TableRow tr = new TableRow(this.getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tr.setWeightSum(4);
            tr.addView(label(String.valueOf(milk.Year)));
            tr.addView(label(String.valueOf(milk.Name)));
            tr.addView(label(String.valueOf(milk.Production)));
            tr.addView(label(String.valueOf(milk.Cost)));
            table.addView(tr);
        });
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void openCreateMilk(View view){

    }
}