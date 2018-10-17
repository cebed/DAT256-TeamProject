package com.semcon.oil.carpoc.database;

import com.semcon.oil.carpoc.R;


public class Dataforstore {

    private int[] Images = {R.drawable.kaffe, R.drawable.glass, R.drawable.tank, R.drawable.biltvatt, R.drawable.headset, R.drawable.spolar, R.drawable.motor, R.drawable.bio, R.drawable.iphone};


    private String[] Names = {"Kaffe", "Glass", "Bränsle", "Biltvätt", "Hörlurar", "Spolarvätska", "Motorolja", "Biobiljett", "Iphone"};
    //private int price  [] = {250,500,1000,1500,2500,3000,3500,5000,10000}; // orginal prices

    private int price[] = {1, 2, 3, 4, 5, 10, 20, 25, 30}; // prices for demo

    public int getImages(int position) {
        return Images[position];
    }


    public String getNames(int position) {
        return Names[position];
    }


    public int getPrice(int position) {

        return price[position];
    }


}
