package com.example.milkmobileapp.ui.milk;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.example.milkmobileapp.DBHelper;
import com.example.milkmobileapp.DBManager;
import com.example.milkmobileapp.Milk;
import com.example.milkmobileapp.R;
import com.example.milkmobileapp.databinding.MilkItemBinding;

import java.util.ArrayList;

public class MilkAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Milk> dataList;

    private AdapterCallback callback;

    public MilkAdapter(Context context, ArrayList<Milk> dataList, AdapterCallback callback) {
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
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            MilkItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.milk_item, parent, false);
            convertView = binding.getRoot();
            convertView.setTag(binding);
        }

        Milk milk = (dataList.get(position));

        TextView year = convertView.findViewById(R.id.milk_year);
        TextView cost = convertView.findViewById(R.id.milk_cost);
        TextView production = convertView.findViewById(R.id.milk_production);

        year.setText(String.valueOf(milk.Year));
        cost.setText(String.valueOf(milk.Cost));
        production.setText(String.valueOf(milk.Production));

        View delete = convertView.findViewById(R.id.button_delete);
        View edit = convertView.findViewById(R.id.button_edit);

        int id = milk.Id;
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onDeleteClicked(id);
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callback.onEditClicked(id);
            }
        });


        return convertView;
    }

}