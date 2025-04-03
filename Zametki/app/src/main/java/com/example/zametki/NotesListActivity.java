package com.example.zametki;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class NotesListActivity extends AppCompatActivity {
    private NotesAdapter notesAdapter;
    private ListView notesListView;
    private List<Note> notes;
    private Button addNoteButton;
    private DBHelper dbHelper;
    private Button tagButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes_list);

        notesListView = findViewById(R.id.notesListView);
        addNoteButton = findViewById(R.id.addNoteButton);
        tagButton = findViewById(R.id.tagsButton);
        dbHelper = new DBHelper(this);
        notes = new ArrayList<>();
        loadNotes();

        notesListView.setAdapter(notesAdapter);

        notesAdapter = new NotesAdapter(this, notes);
        notesListView.setAdapter(notesAdapter);
        notesListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(NotesListActivity.this, NoteActivity.class);
            Note selectedNote = notes.get(position);
            intent.putExtra("NOTE_ID", selectedNote.getId());
            startActivity(intent);
        });

        addNoteButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, NoteActivity.class);
            startActivity(intent);
        });

        tagButton.setOnClickListener(v ->{
            Intent intent = new Intent(this, TagsListActivity.class);
            startActivity(intent);
        });
    }

    private void loadNotes() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("notes", null, null, null, null, null, "date DESC");

        if (cursor != null) {
            notes.clear();
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

                        notes.add(new Note(id, title, description, date, tags));
                    } catch (IllegalArgumentException e) {
                        Log.e("NoteLoader", "Column not found: " + e.getMessage());
                    }
                } while (cursor.moveToNext());
                cursor.close();
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
}
