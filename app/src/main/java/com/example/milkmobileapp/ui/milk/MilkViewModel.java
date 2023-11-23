package com.example.milkmobileapp.ui.milk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.milkmobileapp.DBManager;
import com.example.milkmobileapp.Milk;

import java.util.ArrayList;

public class MilkViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Milk>> milks;
    private DBManager manager;

    public MilkViewModel() {
        milks = new MutableLiveData<>();
        ArrayList<Milk> list = new ArrayList<>();

        milks.setValue(list);
    }

    public LiveData<ArrayList<Milk>> getMilks() {
        return milks;
    }

    public void setMilks(DBManager _manager){
        this.manager = _manager;
        ArrayList<Milk> list = manager.getTable();
        milks.setValue(list);
    }

    public void delete(int id){
        manager.deleteRow(id);
        milks.setValue(manager.getTable());
    }
}