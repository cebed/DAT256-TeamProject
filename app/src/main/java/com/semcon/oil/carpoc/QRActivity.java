package com.semcon.oil.carpoc;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class QRActivity extends AppCompatActivity {
    Button backButton;
    Button backToStore;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);



        Intent intent = getIntent();
        String temp = intent.getExtras().getString("bought");
        TextView textView = (TextView)findViewById(R.id.boughtitem);
        textView.setText(temp);
        homeButton();
        storeButton();


    }



    public void homeButton(){
        backButton = (Button) findViewById(R.id.backb);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(QRActivity.this);
                builder.setTitle("Home page");
                builder.setMessage("\n" +
                        "Do not forget to save QR before you go back otherwise it will be lost!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        openMainActivity();

                    }
                });

                builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

    }
    public void storeButton(){
      backToStore = (Button) findViewById(R.id.bkStore);
        backToStore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder builder = new AlertDialog.Builder(QRActivity.this);
                builder.setTitle("Back to store");
                builder.setMessage("\n" +
                        "Do not forget to save QR before you go back otherwise it will be lost!");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        openMain2Activity();
                    }
                });

                builder.setNegativeButton("Not now", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                        dialogInterface.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    public void openMain2Activity(){
        Intent intent = new Intent(this, Main2Activity.class);
        startActivity(intent);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }
}
