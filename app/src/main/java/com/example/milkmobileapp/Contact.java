package com.example.milkmobileapp;

import java.io.Serializable;

public class Contact implements Serializable {
    public String name;
    public String number;
    public String address;
    public String lastName;
    public Contact(String _nam, String _num, String _address, String _contactLastName){
        name = _nam;
        number = _num;
        address = _address;
        lastName = _contactLastName;
    }
}
