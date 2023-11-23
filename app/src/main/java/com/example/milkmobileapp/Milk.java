package com.example.milkmobileapp;

public class Milk {
    public int Id;
    public int Year;
    public int Cost;
    public int Production;
    public Milk(int _id, int _year, float _cost, float _production){
        Id = _id;
        Year = _year;
        Cost = Math.round(_cost);
        Production = Math.round(_production);
    }
}
