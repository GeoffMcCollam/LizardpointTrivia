package com.lizardpoint.lizardpointtrivia;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper {

    //database properties in case of needed referencing
    public static final String DATABASE_NAME = "stats.db";
    public static final String TABLE_NAME = "stats_table";

    //NOT TO BE CONFUSED WITH "quiz ID" from other activities, only used for auto increment
    //public static final String COL_1 = "ID";

    //THE REAL QUIZ ID
    public static final String COL_2 = "TITLE";
    //stores high score
    public static final String COL_3 = "TOP_SCORE";
    //stores previous score
    public static final String COL_4 = "PREVIOUS_SCORE";

    public Database(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    //when database object is created, tries to create DB incase it does not exist
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, TITLE STRING, TOP_SCORE INTEGER, PREVIOUS_SCORE INTEGER, UNIQUE(TITLE))");
    }

    //useful for if we need to change the DB properties, not entirely needed
    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int recent) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    //function that takes values of the previously declared string columns
    //returns true if data was inserted successfully
    public boolean insertData(String title, int top, int prev) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(COL_2, title);
        cv.put(COL_3, top);
        cv.put(COL_4, prev);

        long result = db.insert(TABLE_NAME, null, cv);

        if (result == -1) return false;

        else return true;
    }

    //sql statement function that pushes the previous users score to the database (based on quiz title)
    public void updatePrev (String title, int prev) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET PREVIOUS_SCORE = " + prev + " WHERE TITLE = '" + title + "'");
    }

    //sql statement function that pushes the high score if int top > current top_score (again based on quiz title)
    public void updateTop (String title, int top) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE " + TABLE_NAME + " SET TOP_SCORE = " + top + " WHERE TITLE = '" + title + "' AND " + top + " > TOP_SCORE");
    }

    //select from the previous score column based on quiz title
    public Cursor getPrev (String title) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT " + COL_4 + " FROM " + TABLE_NAME + " WHERE " + COL_2 + " = '" + title + "'", null);
        return result;
    }

    //select from the top score column based on quiz title
    public Cursor getTop (String title) {

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor result = db.rawQuery("SELECT " + COL_3 + " FROM " + TABLE_NAME + " WHERE " + COL_2 + " = '" + title + "'", null);
        return result;
    }
}
