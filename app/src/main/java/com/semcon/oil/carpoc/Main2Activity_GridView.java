package com.semcon.oil.carpoc;




import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.semcon.oil.carpoc.database.Dataforstore;


public class Main2Activity_GridView extends BaseAdapter {


    private final Main2Activity activity;
    private Dataforstore data;

    public Main2Activity_GridView(Main2Activity context) {

        this.activity = context;
        data = new Dataforstore();
    }

    @Override
    public int getCount() {
       return 9;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = activity.getLayoutInflater().inflate(R.layout.grid_view,null);
        ImageView image = (ImageView) convertView.findViewById(R.id.image_view);
        image.setImageResource(data.getImages(position));


        return convertView;
    }





}
