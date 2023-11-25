package com.example.milkmobileapp.ui.milk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.milkmobileapp.DBManager;
import com.example.milkmobileapp.Milk;

import java.util.ArrayList;

public class MilkViewModel extends ViewModel {

    private final MutableLiveData<ArrayList<Milk>> milks;
    private final MutableLiveData<Boolean> filter;
    private DBManager manager;

    public MilkViewModel() {
        milks = new MutableLiveData<>();
        ArrayList<Milk> list = new ArrayList<>();
        milks.setValue(list);
        filter = new MutableLiveData<>();
        filter.setValue(false);
    }

    public LiveData<ArrayList<Milk>> getMilks() {
        return milks;
    }

    public LiveData<Boolean> getFilter(){
        return filter;
    }

    public void setDBManager(DBManager _manager){
        this.manager = _manager;
        ArrayList<Milk> list = manager.getTable();
        if(this.filter.getValue() == true){
            list = new ArrayList<>();
        }
        milks.setValue(list);
    }
    public void setFilter(Boolean bool){
        filter.setValue(bool);
        ArrayList<Milk> milks =  manager.getTable();
        ArrayList<Milk> list = new ArrayList<>();
        if(bool == true){
            list = new ArrayList<>();
            for (Milk milk : milks) {
                if(milk.Production > 11000000){
                    list.add(milk);
                }
            }
        }else{
            list = milks;
        }

        this.milks.setValue(list);
    }

    public void delete(int id){
        manager.deleteRow(id);
        milks.setValue(manager.getTable());
    }
}