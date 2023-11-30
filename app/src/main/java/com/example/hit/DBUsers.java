package com.example.hit;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DBUsers {
    private static final String DATABASE_NAME = "user.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "TableUser";

    private static final String COLUMN_ID = "id";
    private static final String COLUMN_LOGIN = "login";
    private static final String COLUMN_PASSWORD = "password";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SURNAME= "Surname";
    private static final String COLUMN_ROLE = "role";

    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_LOGIN = 1;
    private static final int NUM_COLUMN_PASSWORD = 2;
    private static final int NUM_COLUMN_NAME = 3;
    private static final int NUM_COLUMN_SURNAME = 4;
    private static final int NUM_COLUMN_ROLE = 5;

    private SQLiteDatabase mDataBase;

    public DBUsers(Context context){
        OpenHelper mOpenHelper = new OpenHelper(context);
        mDataBase = mOpenHelper.getWritableDatabase();
    }
    public long insert(String login, String password, String name, String surname,  String role){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LOGIN, login);
        cv.put(COLUMN_PASSWORD, password);
        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_SURNAME, surname);
        cv.put(COLUMN_ROLE, role);
        return mDataBase.insert(TABLE_NAME, null, cv);

    }

    public int update(Users md){
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_LOGIN, md.getLogin());
        cv.put(COLUMN_PASSWORD, md.getPassword());
        cv.put(COLUMN_NAME, md.getName());
        cv.put(COLUMN_SURNAME, md.getSurname());
        cv.put(COLUMN_ROLE, md.getRole());
        return mDataBase.update(
                TABLE_NAME,
                cv,
                COLUMN_ID + " =?",
                new String[]{String.valueOf(md.getId())});
    }

    public void deleteAll() {
        mDataBase.delete(TABLE_NAME, null, null);
    }

    public void delete(long id) {
        mDataBase.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }
    public Users select(long id){
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, COLUMN_ID + " =?", new String[]{String.valueOf(id)}, null, null, null);

        mCursor.moveToFirst();

        String login = mCursor.getString(NUM_COLUMN_LOGIN);
        String password = mCursor.getString(NUM_COLUMN_PASSWORD);
        String name = mCursor.getString(NUM_COLUMN_NAME);
        String surname = mCursor.getString(NUM_COLUMN_SURNAME);
        String role = mCursor.getString(NUM_COLUMN_ROLE);

        return new Users(id, login, password, name, surname, role);
    }
    public ArrayList<Users> selectAll(){
        Cursor mCursor = mDataBase.query(TABLE_NAME, null, null , null, null, null,null );

        ArrayList<Users> arr = new ArrayList<Users>();
        mCursor.moveToFirst();
        if(!mCursor.moveToFirst()){
            do{
                long id = mCursor.getLong(NUM_COLUMN_ID);
                String login = mCursor.getString(NUM_COLUMN_LOGIN);
                String password = mCursor.getString(NUM_COLUMN_PASSWORD);
                String name = mCursor.getString(NUM_COLUMN_NAME);
                String surname = mCursor.getString(NUM_COLUMN_SURNAME);
                String role = mCursor.getString(NUM_COLUMN_ROLE);
                arr.add(new Users(id, login, password, name, surname, role));
            }while (mCursor.moveToNext());
        }

        return arr;
    }




    private class OpenHelper extends SQLiteOpenHelper{
        OpenHelper(Context context){
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" +
                    COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    COLUMN_LOGIN + " TEXT, " +
                    COLUMN_PASSWORD + "TEXT, " +
                    COLUMN_NAME + " TEXT, " +
                    COLUMN_SURNAME + " TEXT, " +
                    COLUMN_ROLE + " TEXT); ";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }

}


