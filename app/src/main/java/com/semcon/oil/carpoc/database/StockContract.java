

package com.semcon.oil.carpoc.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;


public class StockContract {

    public StockContract() {
    }

    public static final class StockEntry implements BaseColumns {

        public static final String TABLE_NAME = "stock";

        public static final String _ID = BaseColumns._ID;
        public static final String COLUMN_NAME = "name";
        public static final String COLUMN_PRICE = "price";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_SUPPLIER_NAME = "supplier_name";
        public static final String COLUMN_SUPPLIER_PHONE = "supplier_phone";
        public static final String COLUMN_SUPPLIER_EMAIL = "supplier_email";
        public static final String COLUMN_IMAGE = "image";

        public static final String CREATE_TABLE_STOCK = "CREATE TABLE " +
                StockEntry.TABLE_NAME + "(" +
                StockEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                StockEntry.COLUMN_NAME + " TEXT NOT NULL," +
                StockEntry.COLUMN_PRICE + " TEXT NOT NULL," +
                StockEntry.COLUMN_QUANTITY + " INTEGER NOT NULL DEFAULT 0," +
                StockEntry.COLUMN_SUPPLIER_NAME + " TEXT NOT NULL," +
                StockEntry.COLUMN_SUPPLIER_PHONE + " TEXT NOT NULL," +
                StockEntry.COLUMN_SUPPLIER_EMAIL + " TEXT NOT NULL," +
                StockEntry.COLUMN_IMAGE + " TEXT NOT NULL" + ");";
    }

    /**
     * Created by Lara on 03/10/2016.
     */
    public static class StockItem {

        private final String productName;
        private final String price;
        private final int quantity;
        private final String supplierName;
        private final String supplierPhone;
        private final String supplierEmail;
        private final String image;

        public StockItem(String productName, String price, int quantity, String supplierName, String supplierPhone, String supplierEmail, String image) {
            this.productName = productName;
            this.price = price;
            this.quantity = quantity;
            this.supplierName = supplierName;
            this.supplierPhone = supplierPhone;
            this.supplierEmail = supplierEmail;
            this.image = image;
        }

        public String getProductName() {
            return productName;
        }

        public String getPrice() {
            return price;
        }

        public int getQuantity() {
            return quantity;
        }

        public String getSupplierName() {
            return supplierName;
        }

        public String getSupplierPhone() {
            return supplierPhone;
        }

        public String getSupplierEmail() {
            return supplierEmail;
        }

        public String getImage() {
            return image;
        }
        @Override
        public String toString() {
            return "StockItem{" +
                    "productName='" + productName + '\'' +
                    ", price='" + price + '\'' +
                    ", quantity=" + quantity +
                    ", supplierName='" + supplierName + '\'' +
                    ", supplierPhone='" + supplierPhone + '\'' +
                    ", supplierEmail='" + supplierEmail + '\'' +
                    '}';
        }

    }

    /**
     * Created by lara on 2/10/16.
     */
    public static class InventoryDbHelper extends SQLiteOpenHelper {

        public final static String DB_NAME = "inventory.db";
        public final static int DB_VERSION = 1;
        public final static String LOG_TAG = InventoryDbHelper.class.getCanonicalName();

        public InventoryDbHelper(Context context) {
            super(context, DB_NAME, null, DB_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL(StockEntry.CREATE_TABLE_STOCK);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }

        public void insertItem(StockItem item) {
            SQLiteDatabase db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(StockEntry.COLUMN_NAME, item.getProductName());
            values.put(StockEntry.COLUMN_PRICE, item.getPrice());
            values.put(StockEntry.COLUMN_QUANTITY, item.getQuantity());
            values.put(StockEntry.COLUMN_SUPPLIER_NAME, item.getSupplierName());
            values.put(StockEntry.COLUMN_SUPPLIER_PHONE, item.getSupplierPhone());
            values.put(StockEntry.COLUMN_SUPPLIER_EMAIL, item.getSupplierEmail());
            values.put(StockEntry.COLUMN_IMAGE, item.getImage());
            long id = db.insert(StockEntry.TABLE_NAME, null, values);
        }

        public Cursor readStock() {
            SQLiteDatabase db = getReadableDatabase();
            String[] projection = {
                    StockEntry._ID,
                    StockEntry.COLUMN_NAME,
                    StockEntry.COLUMN_PRICE,
                    StockEntry.COLUMN_QUANTITY,
                    StockEntry.COLUMN_SUPPLIER_NAME,
                    StockEntry.COLUMN_SUPPLIER_PHONE,
                    StockEntry.COLUMN_SUPPLIER_EMAIL,
                    StockEntry.COLUMN_IMAGE
            };
            Cursor cursor = db.query(
                    StockEntry.TABLE_NAME,
                    projection,
                    null,
                    null,
                    null,
                    null,
                    null
            );
            return cursor;
        }

        public Cursor readItem(long itemId) {
            SQLiteDatabase db = getReadableDatabase();
            String[] projection = {
                    StockEntry._ID,
                    StockEntry.COLUMN_NAME,
                    StockEntry.COLUMN_PRICE,
                    StockEntry.COLUMN_QUANTITY,
                    StockEntry.COLUMN_SUPPLIER_NAME,
                    StockEntry.COLUMN_SUPPLIER_PHONE,
                    StockEntry.COLUMN_SUPPLIER_EMAIL,
                    StockEntry.COLUMN_IMAGE
            };
            String selection = StockEntry._ID + "=?";
            String[] selectionArgs = new String[] { String.valueOf(itemId) };

            Cursor cursor = db.query(
                    StockEntry.TABLE_NAME,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );
            return cursor;
        }

        public void updateItem(long currentItemId, int quantity) {
            SQLiteDatabase db = getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(StockEntry.COLUMN_QUANTITY, quantity);
            String selection = StockEntry._ID + "=?";
            String[] selectionArgs = new String[] { String.valueOf(currentItemId) };
            db.update(StockEntry.TABLE_NAME,
                    values, selection, selectionArgs);
        }

        public void sellOneItem(long itemId, int quantity) {
            SQLiteDatabase db = getWritableDatabase();
            int newQuantity = 0;
            if (quantity > 0) {
                newQuantity = quantity -1;
            }
            ContentValues values = new ContentValues();
            values.put(StockEntry.COLUMN_QUANTITY, newQuantity);
            String selection = StockEntry._ID + "=?";
            String[] selectionArgs = new String[] { String.valueOf(itemId) };
            db.update(StockEntry.TABLE_NAME,
                    values, selection, selectionArgs);
        }
    }
}
