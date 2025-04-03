package com.example.zametki;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class TagsListActivity extends AppCompatActivity {
    private ListView tagsListView;
    private Button addTagButton;
    private DBHelper dbHelper;
    private TagAdapter tagsAdapter;
    private List<Tag> tagsList = new ArrayList<>();

    private Button noteList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tags_list);

        tagsListView = findViewById(R.id.tagsListView);
        addTagButton = findViewById(R.id.addTagButton);
        dbHelper = new DBHelper(this);
        noteList = findViewById(R.id.notesButton);

        loadTags();
        tagsAdapter = new TagAdapter(this, tagsList);
        tagsListView.setAdapter(tagsAdapter);

        addTagButton.setOnClickListener(v -> {
            Intent intent = new Intent(this, TagActivity.class);
            startActivity(intent);
        });

        tagsListView.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(this, TagActivity.class);
            intent.putExtra("TAG_ID", tagsList.get(position).getId());
            startActivity(intent);
        });

        noteList.setOnClickListener(v ->{
            Intent intent = new Intent(this, NotesListActivity.class);
            startActivity(intent);
        });
    }

    private void loadTags() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.query("tags", new String[]{"id", "tag_name"}, null, null, null, null, null);

        tagsList.clear();
        if (cursor != null && cursor.moveToFirst()) {
            do {
                Tag tag = new Tag(cursor.getString(cursor.getColumnIndex("id")),String.valueOf(cursor.getString(cursor.getColumnIndex("tag_name"))));
                tagsList.add(tag);
            } while (cursor.moveToNext());
            cursor.close();
        }
    }
}
