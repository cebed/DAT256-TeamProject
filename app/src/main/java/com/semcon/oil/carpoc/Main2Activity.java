package com.semcon.oil.carpoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.format.Time;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import java.util.*;
import java.text.SimpleDateFormat;

import com.semcon.oil.carpoc.database.Dataforstore;
import com.semcon.oil.carpoc.utils.FileUtils;

public class Main2Activity extends AppCompatActivity {
    protected Button button1;
 private  GridView gridView;

 private Dataforstore data;
 private TextView textView;
 private FileUtils currentbalance;
 private Main2Activity_GridView adapter;

 private static int val = 0;
 private    int [] newImages;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        currentbalance = new FileUtils();
            data = new Dataforstore();
            gridView = (GridView) findViewById(R.id.list_view);
            adapter = new Main2Activity_GridView(this);
            gridView.setAdapter(adapter);

            textView = (TextView)findViewById(R.id.current_balance_store_textview);
            val = FileUtils.loadScore(getFilesDir());
            textView.setText(val +"");





        homebuton();


       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String timeStamp = new SimpleDateFormat("yyyy-MM-dd    HH:mm").format(Calendar.getInstance().getTime());



                if(val-data.getPrice(position)>0) {


                    textView.setText((val - data.getPrice(position) + ""));
                    val -= data.getPrice(position);

                    FileUtils.saveScore(getFilesDir(),val);

                    String stringToPass  = "Product: "+ data.getNames(position);
                    int picturePass = data.getImages(position);
                    String passPrice = "Price: " +  + data.getPrice(position);
                    Intent i = new Intent(Main2Activity.this, QRActivity.class);
                    i.putExtra("bought", stringToPass);
                    i.putExtra("tid", timeStamp);
                    i.putExtra("picture",picturePass);
                    i.putExtra("proPrice", passPrice);
                    startActivity(i);

                }



           }
       });





    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {  // skapa en menu i
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main,menu);
        return true;
    }





    public void homebuton(){
        button1 = (Button) findViewById(R.id.button3);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();

            }
        });
    }



        public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }



}

