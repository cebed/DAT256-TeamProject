package com.semcon.oil.carpoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.semcon.oil.carpoc.database.Dataforstore;

public class Main2Activity extends AppCompatActivity {
    protected Button button1;
 private  GridView gridView;
 //private CursorInventAdapter storeImage;
 private Dataforstore data;
 private TextView textView;
 private Main2Activity_GridView adapter;
   // private StockContract.InventoryDbHelper dbHelper;
 private static int val = 5000;
 private    int [] newImages;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        data = new Dataforstore();
            gridView = (GridView) findViewById(R.id.list_view);
            adapter = new Main2Activity_GridView(this);
            gridView.setAdapter(adapter);

            textView = (TextView)findViewById(R.id.current_balance_store_textview);
            textView.setText(val +"");





        homebuton();


       gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> parent, View view, int position, long id) {




                if(val-data.getPrice(position)>0) {


                    textView.setText((val - data.getPrice(position) + ""));
                    val -= data.getPrice(position);


                    String stringToPass  = data.getNames(position);

                    Intent i = new Intent(Main2Activity.this, QRActivity.class);
                    i.putExtra("bought", stringToPass);

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

