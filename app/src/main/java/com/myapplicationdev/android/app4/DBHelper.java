package com.myapplicationdev.android.app4;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    // Start version with 1
    // increment by 1 whenever db schema changes.
    private static final int DATABASE_VER = 5;

    // Filename of the database
    private static final String DATABASE_NAME = "tasks.db";

    private static final String TABLE_TASK = "task";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_DESCRIPTION = "description";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_DATE = "date";
    private static final String COLUMN_TIME = "time";

//    private static final String COLUMN_SECONDS = "seconds";


    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTableSql = "CREATE TABLE " + TABLE_TASK
                +  "(" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_NAME + " TEXT,"
                + COLUMN_DESCRIPTION + " TEXT,"
                + COLUMN_DATE + " TEXT,"
                + COLUMN_TIME + " TEXT)";

        db.execSQL(createTableSql);
        Log.i("info" ,"created tables");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_TASK);
        // Create table(s) again
        onCreate(db);


    }

    public long insertTask(String name, String description, String date, String time) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME , name);
        values.put(COLUMN_DESCRIPTION,description );
        values.put(COLUMN_DATE , date);
        values.put(COLUMN_TIME, time);


        long result = db.insert(TABLE_TASK, null, values);
        db.close();

        if (result == -1) {
            Log.d("DBHelper", "Insert failed");
        }

        Log.d("SQL Insert ",""+ result); //id returned, shouldn’t be -1
        return result;
    }


    public ArrayList<String> getTaskContent() {
        // Create an ArrayList that holds String objects
        ArrayList<String> tasks = new ArrayList<String>();
        // Select all the tasks' description
        String selectQuery = "SELECT " + COLUMN_DESCRIPTION
                + " FROM " + TABLE_TASK;

        // Get the instance of database to read
        SQLiteDatabase db = this.getReadableDatabase();
        // Run the SQL query and get back the Cursor object
        Cursor cursor = db.rawQuery(selectQuery, null);

        // moveToFirst() moves to first row
        if (cursor.moveToFirst()) {
            // Loop while moveToNext() points to next row
            //  and returns true; moveToNext() returns false
            //  when no more next row to move to
            do {
                // Add the task content to the ArrayList object
                //  0 in getString(0) return the data in the first
                //  column in the Cursor object. getString(1)
                //  return second column data and so on.
                //  Use getInt(0) if data is an int
                tasks.add(cursor.getString(0));
            } while (cursor.moveToNext());
        }
        // Close connection
        cursor.close();
        db.close();

        return tasks;
    }


    public ArrayList<Task> getTasks() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        String selectQuery = "SELECT " + COLUMN_ID + ", "
                + COLUMN_NAME + ", "
                + COLUMN_DESCRIPTION + ", "
                + COLUMN_DATE + ", "
                + COLUMN_TIME
                + " FROM " + TABLE_TASK;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                String description = cursor.getString(2);
                String date = cursor.getString(3);
                String time = cursor.getString(4);
                Task obj = new Task(id, name, description, date, time);
                tasks.add(obj);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return tasks;
    }


    public int updateTask(Task data){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, data.getName());
        values.put(COLUMN_DESCRIPTION, data.getDescription());
        values.put(COLUMN_TIME,data.getTime());
        values.put(COLUMN_DATE,data.getDate());
        String condition = COLUMN_ID + "= ?" ;
        String[] args = {String.valueOf(data.getId())};
        int result = db.update(TABLE_TASK, values, condition, args);
        db.close();
        return result;
    }

    public int deleteTask(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        String condition = COLUMN_ID + "= ?";
        String[] args = {String.valueOf(id)};
        int result = db.delete(TABLE_TASK, condition, args);
        db.close();
        return result;
    }

    public int noOfTask() {
        String selectQuery = "SELECT * " + "FROM " + TABLE_TASK;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();
        return count;
    };


}



