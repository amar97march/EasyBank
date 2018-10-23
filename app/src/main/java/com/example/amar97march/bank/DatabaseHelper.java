package com.example.amar97march.bank;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by amar97march on 17-10-2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "Customers.db";
    public static final String TABLE_NAME = "UserInfo";
    public static final String COL_1 = "ID";
    public static final String COL_2 = "First_Name";
    public static final String COL_3 = "Last_Name";
    public static final String COL_4 = "Money";
    public static final String COL_5 = "Email";
    public static final String COL_6 = "Password";
    public static final String COL_7 = "Transactions";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table "+ TABLE_NAME +" ("+COL_1+ " INTEGER PRIMARY KEY AUTOINCREMENT, "+ COL_2 +" TEXT , "+ COL_3 +" TEXT, "+ COL_4 +" INT Constraint DF_ID DEFAULT(5000), "+ COL_5 +" TEXT, "+ COL_6 +" TEXT, "+COL_7+" TEXT CONSTRAINT DF_ID2 DEFAULT(''))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(sqLiteDatabase);

    }

    public boolean insertData(String firstname, String lastname, String email,String password){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE Email = '"+email+"'",null);
        if(res.getCount() > 0){
            res.close();
            return false;
        }
        contentValues.put(COL_2,firstname);
        contentValues.put(COL_3,lastname);
        contentValues.put(COL_5,email);
        contentValues.put(COL_6,password);
        long result = db.insert(TABLE_NAME,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor getAllData(String email){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE Email = '"+email+"'",null);
        return res;
    }
    public boolean updateMoney(String sender, String reciever ,int cash){

        ContentValues cv = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE Email = '"+reciever+"'",null);
        if(res.getCount() <= 0){
            res.close();
            return false;
        }
        int totalMoney=0;
        String tempTrans = "";
        if (res != null && res.moveToFirst()) {
            totalMoney = (res.getInt(3)) + cash;
            tempTrans = (res.getString(6)) +"\n"+"Received "+cash+"$ from "+sender;

        }
        cv.put(COL_4,Integer.toString(totalMoney));
        cv.put(COL_7,tempTrans);
        db.update(TABLE_NAME, cv, "Email='"+reciever+"'", null);

        res = db.rawQuery("SELECT * FROM "+TABLE_NAME+" WHERE Email = '"+sender+"'",null);

        if (res != null && res.moveToFirst()) {
            totalMoney = (res.getInt(3)) - cash;
            tempTrans = (res.getString(6)) +"\n"+"Transferred "+cash+"$ to "+reciever;
        }
        cv.put(COL_4,Integer.toString(totalMoney));
        cv.put(COL_7,tempTrans);
        db.update(TABLE_NAME, cv, "Email = '"+sender+"'", null);


        db.close();
        return true;
    }


}
