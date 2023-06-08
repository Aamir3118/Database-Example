package com.example.databaseexample;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "contacts_db";
    public static final String TABLE_NAME = "contacts";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT,Name TEXT,Phone_No LONG)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(sqLiteDatabase);
    }

    public boolean addContact(Contacts_Model contacts_model) {
        try {
            SQLiteDatabase sqLiteDatabase = getWritableDatabase();
            ContentValues contentValues = new ContentValues();
            contentValues.put("Name", contacts_model.getName());
            contentValues.put("Phone_No", contacts_model.getPhn_no());
            long result = sqLiteDatabase.insert(TABLE_NAME, null, contentValues);
            sqLiteDatabase.close();
            if (result == -1) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public ArrayList<Contacts_Model> displayContact() {
        try {
            SQLiteDatabase sqLiteDatabase = getReadableDatabase();
            ArrayList<Contacts_Model> arrayList = new ArrayList<>();
            Cursor cursor = sqLiteDatabase.rawQuery("SELECT * FROM " + TABLE_NAME, null);
            while (cursor.moveToNext()) {
                Contacts_Model contacts_model = new Contacts_Model();
                contacts_model.setId(cursor.getInt(0));
                contacts_model.setName(cursor.getString(1));
                contacts_model.setPhn_no(cursor.getLong(2));
                arrayList.add(contacts_model);
            }
            sqLiteDatabase.close();
            return arrayList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public boolean updateContact(Contacts_Model contacts_model)
    {
        try
        {
            SQLiteDatabase sqLiteDatabase=getWritableDatabase();
            ContentValues contentValues=new ContentValues();
            contentValues.put("Name",contacts_model.getName());
            contentValues.put("Phone_No",contacts_model.getPhn_no());
            int row=sqLiteDatabase.update(TABLE_NAME,contentValues,"ID=?",new String[]{String.valueOf(contacts_model.getId())});
            sqLiteDatabase.close();
            return row > 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean deleteContact(int id)
    {
        try {
            SQLiteDatabase sqLiteDatabase=getWritableDatabase();
            int row=sqLiteDatabase.delete(TABLE_NAME,"ID=?",new String[]{String.valueOf(id)});
            sqLiteDatabase.close();
            return row > 0;
        }
        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

}
