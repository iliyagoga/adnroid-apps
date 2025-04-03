package com.example.zametki;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "notesDB";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE notes (id INTEGER PRIMARY KEY AUTOINCREMENT, date TEXT, title TEXT, content TEXT)";
        String CREATE_TAGS_TABLE = "CREATE TABLE tags (id INTEGER PRIMARY KEY AUTOINCREMENT, tag_name TEXT)";
        String CREATE_NOTE_TAGS_TABLE = "CREATE TABLE note_tags (note_id INTEGER, tag_name TEXT, FOREIGN KEY(note_id) REFERENCES notes(id))";

        db.execSQL(CREATE_NOTES_TABLE);
        db.execSQL(CREATE_TAGS_TABLE);
        db.execSQL(CREATE_NOTE_TAGS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS notes");
        db.execSQL("DROP TABLE IF EXISTS tags");
        db.execSQL("DROP TABLE IF EXISTS note_tags");
        onCreate(db);
    }


}
