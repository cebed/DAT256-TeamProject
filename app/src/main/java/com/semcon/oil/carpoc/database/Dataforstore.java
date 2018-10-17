package com.semcon.oil.carpoc.database;

import com.semcon.oil.carpoc.R;



public class Dataforstore {

    private int [] Images = {R.drawable.kaffe,R.drawable.glass,R.drawable.tank,R.drawable.biltvatt,R.drawable.headset,R.drawable.spolar,R.drawable.motor,R.drawable.bio,R.drawable.iphone};


    private String [] Names = {"kaffe","glass","tank","biltvätt","hörlurar","spolarvätska","Motorolja","biobiljett","iphone"};
    private int price  [] = {250,500,1000,1500,2500,3000,3500,5000,10000};


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
