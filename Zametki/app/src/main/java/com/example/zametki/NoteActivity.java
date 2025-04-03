package com.example.zametki;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NoteActivity extends AppCompatActivity {
    private EditText titleEditText, contentEditText, dateEdit;
    private Button saveButton;
    private DBHelper dbHelper;
    private long noteId = -1;

    private GridView tagGridView;
    private LinearLayout selectedTagsContainer;
    private List<Tag> availableTags;
    private List<String> selectedTags;
    private TagAdapter tagAdapter;

    private Button deleteNoteButton;

    private List<String> tags = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        titleEditText = findViewById(R.id.editTextTitle);
        contentEditText = findViewById(R.id.editTextContent);
        saveButton = findViewById(R.id.saveButton);
        dateEdit = findViewById(R.id.editDate);
        tagGridView = findViewById(R.id.tagGridView);
        selectedTagsContainer = findViewById(R.id.selectedTagsContainer);

        availableTags = new ArrayList<>();
        selectedTags = new ArrayList<>();

        tagAdapter = new TagAdapter(this, availableTags);
        tagGridView.setAdapter(tagAdapter);

        deleteNoteButton = findViewById(R.id.delete_note_button);
        dbHelper = new DBHelper(this);

        loadTagsFromDatabase();

        tagGridView.setOnItemClickListener((parent, view, position, id) -> {
            String selectedTag = availableTags.get(position).getTagName();
            if (!selectedTags.contains(selectedTag)) {
                selectedTags.add(selectedTag);
                displaySelectedTags();
            }
        });

        this.noteId = getIntent().getLongExtra("NOTE_ID", -1);
        if (this.noteId != -1) {
            loadNote();
            deleteNoteButton.setOnClickListener( v-> {
                deleteNote(this.noteId);
            }
            );
        }


        saveButton.setOnClickListener(v -> saveNote());
    }

    private void loadNote() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("notes", null, "id = ?", new String[]{String.valueOf(noteId)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            String id = cursor.getString(cursor.getColumnIndex("id"));
            String date = cursor.getString(cursor.getColumnIndex("date"));
            String title = cursor.getString(cursor.getColumnIndex("title"));
            String content = cursor.getString(cursor.getColumnIndex("content"));
            dateEdit.setText(date);
            titleEditText.setText(title);
            contentEditText.setText(content);
            Cursor cursorTags = db.query("note_tags", new String[]{"tag_name"}, "note_id = ?", new String[]{String.valueOf(id)}, null, null, null);
            if(cursorTags != null ){
                while (cursorTags.moveToNext()) {
                    String tagName = cursorTags.getString(cursorTags.getColumnIndex("tag_name"));
                    selectedTags.add(tagName);
                }

            }
            displaySelectedTags();
            cursor.close();
        }
    }

    private void saveNote() {
        String title = titleEditText.getText().toString();
        String content = contentEditText.getText().toString();
        String date = dateEdit.getText().toString();

        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("date", date);
        values.put("title", title);
        values.put("content", content);
        long noteId = 0;
        if (this.noteId == -1) {
            noteId= db.insert("notes", null, values);
            saveTagsForNote(db, noteId);
        } else {
            db.update("notes", values, "id = ?", new String[]{String.valueOf(this.noteId)});
            updateTagsForNote(db,this.noteId);
        }

        finish();
    }

    private void loadTagsFromDatabase() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("tags", new String[]{"id","tag_name"}, null, null, null, null, null);

        if (cursor != null) {
            while (cursor.moveToNext()) {
                Tag tag = new Tag(cursor.getString(cursor.getColumnIndex("id")),cursor.getString(cursor.getColumnIndex("tag_name")));
                availableTags.add(tag);
            }
            cursor.close();
        }
    }

    private void displaySelectedTags() {
        selectedTagsContainer.removeAllViews();

        for (String tag : selectedTags) {
            TextView tagView = new TextView(this);
            tagView.setText(tag);
            tagView.setBackgroundResource(android.R.drawable.btn_default);
            tagView.setPadding(10, 5, 10, 5);
            tagView.setTextColor(Color.WHITE);
            tagView.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));
            tagView.setOnClickListener(v -> {
                selectedTags.remove(tag);
                displaySelectedTags();

            });
            selectedTagsContainer.addView(tagView);

        }
    }

    private void saveTagsForNote(SQLiteDatabase db, long noteId) {
        for (String tag : selectedTags) {
            ContentValues tagValues = new ContentValues();
            tagValues.put("note_id", noteId);
            tagValues.put("tag_name", tag);
            db.insert("note_tags", null, tagValues);
        }
    }

    private void updateTagsForNote(SQLiteDatabase db, long noteId) {
        db.delete("note_tags", "note_id = ?",new String[]{String.valueOf(noteId)});
        for (String tag : selectedTags) {
            ContentValues tagValues = new ContentValues();
            tagValues.put("note_id", noteId);
            tagValues.put("tag_name", tag);

            db.insert("note_tags", null, tagValues);
        }
    }

    private void deleteNote(long noteId) {
        if (noteId != -1) {
            SQLiteDatabase db = dbHelper.getWritableDatabase();

            db.delete("note_tags", "note_id = ?", new String[]{String.valueOf(noteId)});

            db.delete("notes", "id = ?", new String[]{String.valueOf(noteId)});

            db.close();
        }

        finish();
    }


}
