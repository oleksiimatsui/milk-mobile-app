package com.example.milkmobileapp;

import java.io.Serializable;

public class Contact implements Serializable {
    public String name;
    public String number;
    public String address;
    public Contact(String _nam, String _num, String _address){
        name = _nam;
        number = _num;
        address = _address;
    }
}
