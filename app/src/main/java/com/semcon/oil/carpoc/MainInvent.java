package com.semcon.oil.carpoc;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.semcon.oil.carpoc.database.StockContract;

public class MainInvent extends AppCompatActivity {
private CursorInventAdapter adapter;
private ListView listView;
private StockContract.InventoryDbHelper dbHelper;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        dbHelper = new StockContract.InventoryDbHelper(this);
        Cursor cursor = dbHelper.readStock();

        final ListView listView = (ListView) findViewById(R.id.list_view_invent);
        adapter = new CursorInventAdapter(this,cursor);


        listView.setAdapter(adapter);

       // addDummyData();
        /*
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 1) {
                    setTitle(id +"");
                    deleteAllRowsFromTable();
                }


            }
        });
*/

    }



    public void deleteAllRowsFromTable() {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
         database.delete(StockContract.StockEntry.TABLE_NAME, null, null);
    }


    public int deleteOneItemFromTable(long itemId) {
        SQLiteDatabase database = dbHelper.getWritableDatabase();
        String selection = StockContract.StockEntry._ID + "=?";
        String[] selectionArgs = { String.valueOf(itemId) };
        int rowsDeleted = database.delete(
                StockContract.StockEntry.TABLE_NAME, selection, selectionArgs);
        return rowsDeleted;
    }



}
