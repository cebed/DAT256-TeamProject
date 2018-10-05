package com.semcon.oil.carpoc.database;

public class Dataforstore {


    private String [] Names = {"cherry","cola","fresquito","fruit_salad","cherry","cola","fresquito","fruit_salad","cherry","cola","fresquito","fruit_salad"};
    private int price  [] = {10,40,101,120,10,40,101,120,10,40,101,120};

    public String getNames(int position) {
        return Names[position];
    }


    public int getPrice(int position) {

        return price[position];
    }


}
