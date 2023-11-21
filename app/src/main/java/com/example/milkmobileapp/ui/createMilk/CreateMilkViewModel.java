package com.example.milkmobileapp.ui.createMilk;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class CreateMilkViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public CreateMilkViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is home fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}