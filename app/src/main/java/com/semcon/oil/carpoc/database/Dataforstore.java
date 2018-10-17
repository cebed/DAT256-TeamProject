package com.semcon.oil.carpoc.database;

import com.semcon.oil.carpoc.R;



public class Dataforstore {

    private int [] Images = {R.drawable.kaffe,R.drawable.glass,R.drawable.tank,R.drawable.biltvatt,R.drawable.headset,R.drawable.spolar,R.drawable.motor,R.drawable.bio,R.drawable.iphone};
    private String [] Image_Strings ={"R.drawable.cherry","R.drawable.cola","R.drawable.fresquito","R.drawable.fruit_salad","R.drawable.cherry","R.drawable.cola","R.drawable.fresquito","R.drawable.fruit_salad","R.drawable.cherry","R.drawable.cola","R.drawable.fresquito"};

    private String [] Names = {"cherry","cola","fresquito","fruit_salad","cherry","cola","fresquito","fruit_salad","cherry","cola","fresquito","fruit_salad"};
    private int price  [] = {10,40,101,120,10,40,101,120,10,40,101,120};


    public int getImages(int position) {
        return Images[position];
    }
    public String get_Images_I_Strings(int position){
        return Image_Strings[position];
    }

    public int Imagelength() {
        return Images.length;
    }

    public String getNames(int position) {
        return Names[position];
    }


    public int getPrice(int position) {

        return price[position];
    }





}
