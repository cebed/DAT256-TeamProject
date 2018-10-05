package com.semcon.oil.carpoc;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.semcon.oil.carpoc.database.Dataforstore;

public class Main2Activity extends AppCompatActivity {
    private Button button1;
    private GridView gridView;
    private Dataforstore data;
    private TextView textView;




    private StockCursorAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);


        adapter = new StockCursorAdapter(this);
        data = new Dataforstore();
        gridView = (GridView) findViewById(R.id.list_view);
        adapter = new StockCursorAdapter(this);
        gridView.setAdapter(adapter);


























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

