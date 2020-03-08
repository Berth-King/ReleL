package ht.berth.relel.DBO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "ReleL.db";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_NAME = "Contact";
    private static final String COLUMN_ID = "_id";

    private static final String COLUMN_FIRST_NAME = "fname";
    private static final String COLUMN_LAST_NAME = "lname";
    private static final String COLUMN_NUMBER = "number";
    private static final String COLUMN_ADDRESS = "address";
    private static final String COLUMN_EMAIL = "email";

    public MyDBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String query = "CREATE TABLE " + TABLE_NAME +
                " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_FIRST_NAME + " TEXT, " +
                COLUMN_LAST_NAME + " TEXT, " +
                COLUMN_NUMBER + " TEXT, "+
                COLUMN_ADDRESS + " TEXT, "+
                COLUMN_EMAIL+ " TEXT);";
        sqLiteDatabase.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public void saveContact(String first_name, String last_name, String number, String address, String email){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        // Pa bliye...
        contentValues.put(COLUMN_FIRST_NAME, first_name);
        contentValues.put(COLUMN_LAST_NAME, last_name);
        contentValues.put(COLUMN_NUMBER, number);
        contentValues.put(COLUMN_ADDRESS, address);
        contentValues.put(COLUMN_EMAIL, email);

        long result = sqLiteDatabase.insert(TABLE_NAME,null, contentValues);
        if(result == -1){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Saved Successfully!", Toast.LENGTH_SHORT).show();
        }
    }
    public Cursor readAllData(){
        String query = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();

        Cursor cursor = null;
        if(sqLiteDatabase != null){
            cursor = sqLiteDatabase.rawQuery(query, null);
        }
        return cursor;
    }
    public Cursor searchData(String searchText){
        String query = "SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_FIRST_NAME + " LIKE '%" + searchText + "%'";
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
            Cursor cursor = null;
            if(sqLiteDatabase != null){
                cursor = sqLiteDatabase.rawQuery(query, null);
            }
            return cursor;
        }


    public void updateChanges(String contact_id, String fnameS, String lnameS, String numbersS , String addressS , String emailS){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put(COLUMN_FIRST_NAME, fnameS);
        contentValues.put(COLUMN_LAST_NAME, lnameS);
        contentValues.put(COLUMN_NUMBER, numbersS);
        contentValues.put(COLUMN_ADDRESS, addressS);
        contentValues.put(COLUMN_EMAIL, emailS);

        long result = sqLiteDatabase.update(TABLE_NAME, contentValues, "_id=?", new String[]{contact_id});
        if(result == -1){
            Toast.makeText(context, "Failed" , Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(context, "Updated Successfully!  " + contact_id + fnameS + lnameS, Toast.LENGTH_SHORT).show();
        }

    }


    public void delete(String row_id){
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        long result = sqLiteDatabase.delete(TABLE_NAME, "_id=?", new String[]{row_id});
        if(result == -1){
            Toast.makeText(context, "Failed to Delete.", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(context, "Successfully Deleted.", Toast.LENGTH_SHORT).show();
        }
    }

    public void deleteAll() {
        SQLiteDatabase sqLiteDatabase = this.getWritableDatabase();
        sqLiteDatabase.execSQL("DELETE FROM " + TABLE_NAME);
    }

}
