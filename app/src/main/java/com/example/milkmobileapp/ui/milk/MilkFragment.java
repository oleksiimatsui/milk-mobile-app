package com.example.milkmobileapp.ui.milk;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.fragment.NavHostFragment;

import com.example.milkmobileapp.DBHelper;
import com.example.milkmobileapp.DBManager;
import com.example.milkmobileapp.Milk;
import com.example.milkmobileapp.R;
import com.example.milkmobileapp.databinding.FragmentMilkBinding;
import com.example.milkmobileapp.ui.editMilk.EditMilkFragment;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.OptionalDouble;

public class MilkFragment extends Fragment implements AdapterCallback {

    private FragmentMilkBinding binding;
    private MilkViewModel milkViewModel;

   /* private TextView label(String text){
        TextView label = new TextView(this.getContext());    // part3
        label.setId( generateViewId ());
        label.setText(text);
        //label.setBackgroundResource(R.drawable.border);
        label.setPadding(10, 10, 10, 10);
        LinearLayout.LayoutParams params = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT,5f);
        params.weight = 1;
        label.setLayoutParams(params);
        return label;
    }*/

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentMilkBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        DBHelper DB = new DBHelper(this.getContext());
        DBManager manager = new DBManager(DB);

        MilkViewModel milkViewModel =
                new ViewModelProvider(this).get(MilkViewModel.class);
        this.milkViewModel = milkViewModel;
        milkViewModel.setDBManager(manager);
        milkViewModel.getMilks().observe(getViewLifecycleOwner(), milkObserver);
        Switch filter = binding.filter;
        View btn = binding.buttonAdd;
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCreateMilk(v);
            }
        });

        filter.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                milkViewModel.setFilter(isChecked);
            }
        });

        return root;
    }


    final Observer<ArrayList<Milk>> milkObserver = new Observer<ArrayList<Milk>>() {
        @Override
        public void onChanged(@Nullable final ArrayList<Milk> newList) {
            setList(newList);
        }
    };

    public void setList(ArrayList<Milk> milks){
        ListView listView = binding.milkList;
        MilkAdapter milkAdapter = new MilkAdapter(this.getContext(), milks, this);
        listView.setAdapter(milkAdapter);

        double average;
        average = calculateAverage(milks);
        DecimalFormat decimalFormat = new DecimalFormat("0.00");
        String stringAverage = decimalFormat.format(average);

        TextView text_average = binding.textAverage;
        text_average.setText(stringAverage + " т");
    }

    private double calculateAverage(ArrayList <Milk> milks) {
        Integer sum = 0;
        if(!milks.isEmpty()) {
            for (Milk milk : milks) {
                sum += milk.Production;
            }
            return sum.doubleValue() / milks.size();
        }
        return sum;
    }

    @Override
    public void onDeleteClicked(int itemId) {
        // Communicate with the ViewModel and perform necessary actions
        milkViewModel.delete(itemId);
    }
    @Override
    public void onEditClicked(int itemId) {
        // Communicate with the ViewModel and perform necessary actions
       /* Bundle bundle = new Bundle();
        bundle.putInt("ID", itemId);*/
        EditMilkFragment fragment = EditMilkFragment.newInstance(itemId);
        NavHostFragment.findNavController(MilkFragment.this)
                .navigate(R.id.action_nav_milk_to_editMilkFragment, fragment.getArguments());
    }


    /*public void setTable(ArrayList<Milk> milks){
        TableRow tr_head = new TableRow(this.getContext());
        tr_head.setId(generateViewId());
        tr_head.setBackgroundColor(Color.GRAY);        // part1
        tr_head.setLayoutParams(new TableRow.LayoutParams(
                TableRow.LayoutParams.MATCH_PARENT,
                TableRow.LayoutParams.WRAP_CONTENT));

        TableLayout table = (TableLayout) ( binding.milkTable );
        tr_head.setWeightSum(4);
        table.addView(tr_head);

        tr_head.addView(label(String.valueOf("Year")));
        tr_head.addView(label(String.valueOf("Production")));
        tr_head.addView(label(String.valueOf("Cost")));

        milks.forEach(milk -> {
            TableRow tr = new TableRow(this.getContext());
            tr.setLayoutParams(new TableRow.LayoutParams(
                    TableRow.LayoutParams.MATCH_PARENT,
                    TableRow.LayoutParams.MATCH_PARENT));
            tr.setWeightSum(4);
            tr.addView(label(String.valueOf(milk.Year)));
            tr.addView(label(String.valueOf(milk.Production)));
            tr.addView(label(String.valueOf(milk.Cost)));
            table.addView(tr);
        });
    }*/

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public void openCreateMilk(View view){
        Bundle bundle = new Bundle();
        NavHostFragment.findNavController(MilkFragment.this)
                .navigate(R.id.action_nav_milk_to_createMilk, bundle);
    }
}