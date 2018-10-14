package com.semcon.oil.carpoc;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.content.Intent;

public class QRActivity extends AppCompatActivity {
    Button backbutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);


<<<<<<< HEAD
        Intent intent = getIntent();
        String temp = intent.getExtras().getString("bought");
        TextView textView = (TextView)findViewById(R.id.boughtitem);
        textView.setText(temp);
        homebuton();
    }

    public void homebuton(){
        backbutton = (Button) findViewById(R.id.backb);
        backbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();

            }
        });
    }



    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
=======
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
>>>>>>> dcc2d61772ef520a9f1b7c568438a1c8bd61fb8d
    }
}
