package com.example.zametki;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TagActivity extends AppCompatActivity {
    private EditText tagEditText;
    private Button saveTagButton;
    private Button deleteNoteButton;
    private NotesAdapter notesAdapter;
    private ListView notesListView;
    private List<Note> notes;
    private DBHelper dbHelper;
    private String tagId = "-1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tag);

        tagEditText = findViewById(R.id.editTextTag);
        saveTagButton = findViewById(R.id.saveTagButton);
        dbHelper = new DBHelper(this);
        deleteNoteButton = findViewById(R.id.daleteTagButton);
        tagId = getIntent().getStringExtra("TAG_ID");
        if (tagId != "-1") {
            loadTag();
        }

        saveTagButton.setOnClickListener(v -> saveTag(tagId));
        deleteNoteButton.setOnClickListener(v ->{
            SQLiteDatabase db = dbHelper.getReadableDatabase();
            Cursor cursor = db.query("tags", null, "id = ?", new String[]{String.valueOf(tagId)}, null, null, null);

            if (cursor != null && cursor.moveToFirst()) {
                String tagName = cursor.getString(cursor.getColumnIndex("tag_name"));
                deleteTag(tagName);
            }

            }
        );
    }

    private void loadTag() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("tags", null, "id = ?", new String[]{String.valueOf(tagId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String tagName = cursor.getString(cursor.getColumnIndex("tag_name"));
            tagEditText.setText(tagName);
            notes = new ArrayList<>();
            notesListView = findViewById(R.id.notesListView);
            loadNotes(tagName);
            notesAdapter = new NotesAdapter(this, notes);
            notesListView.setAdapter(notesAdapter);
            cursor.close();
        }

    }

    private void saveTag(String tagId) {
        String tagName = tagEditText.getText().toString();
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("tag_name", tagName);

        if (tagId == null || tagId == "-1") {
            db.insert("tags", null, values);
        } else {
            db.update("tags", values, "id = ?", new String[]{String.valueOf(tagId)});
        }
        finish();
    }

    private void loadNotes(String tagName) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursorTag = db.query("note_tags",new String[]{"note_id"},"tag_name = ?", new String[]{tagName}, null, null,null);
        if (cursorTag!=null){
            this.notes.clear();
            if(cursorTag.moveToFirst()) {
                do{
                    int noteId = cursorTag.getInt(cursorTag.getColumnIndex("note_id"));

                    Cursor cursor = db.query("notes", null, "id = ?", new String[]{String.valueOf(noteId)}, null, null, "date DESC");

                    if (cursor != null) {
                        if (cursor.moveToFirst()) {
                            do {
                                try {

                                    int idIndex = cursor.getColumnIndexOrThrow("id");

                                    int titleIndex = cursor.getColumnIndexOrThrow("title");

                                    int dateIndex = cursor.getColumnIndexOrThrow("date");
                                    int descriptionIndex = cursor.getColumnIndexOrThrow("content");


                                    long id = cursor.getLong(idIndex);
                                    String title = cursor.getString(titleIndex);
                                    String description = cursor.getString(descriptionIndex);
                                    String date = cursor.getString(dateIndex);

                                    List<String> tags = loadTagsForNote(id);

                                    this.notes.add(new Note(id, title, description, date, tags));
                                } catch (IllegalArgumentException e) {
                                    Log.e("NoteLoader", "Column not found: " + e.getMessage());
                                }
                            } while (cursor.moveToNext());
                            cursor.close();
                        }
                    }
                }while (cursorTag.moveToNext());
                cursorTag.close();
            }
        }

    }
    private List<String> loadTagsForNote(long noteId) {
        List<String> tags = new ArrayList<>();
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT tag_name FROM note_tags t WHERE t.note_id = ?", new String[]{String.valueOf(noteId)});

        if (cursor != null) {
            while (cursor.moveToNext()) {
                tags.add(cursor.getString(cursor.getColumnIndex("tag_name")));
            }
            cursor.close();
        }
        return tags;
    }

    private void deleteTag(String tagName) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursorTag = db.query("note_tags",new String[]{"note_id"},"tag_name = ?", new String[]{tagName}, null, null,null);
        if (cursorTag!=null) {
            this.notes.clear();
            if (cursorTag.moveToFirst()) {
                do {
                    int noteId = cursorTag.getInt(cursorTag.getColumnIndex("note_id"));
                    db.delete("note_tags", "note_id = ? AND tag_name= ?", new String[]{String.valueOf(noteId), tagName});
                }while (cursorTag.moveToNext());
                cursorTag.close();
            }
        }
        db.delete("tags", "tag_name = ?", new String[]{tagName});


        db.close();

        finish();
    }
}
