package com.example.zametki;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class NotesAdapter extends BaseAdapter {
    private Context context;
    private List<Note> notes;
    private LayoutInflater inflater;

    public NotesAdapter(Context context, List<Note> notes) {
        this.context = context;
        this.notes = notes;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return notes.size();
    }

    @Override
    public Object getItem(int position) {
        return notes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return notes.get(position).getId();
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_note, parent, false);
        }

        TextView titleTextView = convertView.findViewById(R.id.noteTitle);
        TextView dateTextView = convertView.findViewById(R.id.noteDate);
        TextView descriptionTextView = convertView.findViewById(R.id.noteDescription);
        TextView tagsTextView = convertView.findViewById(R.id.noteTags);

        Note note = notes.get(position);

        titleTextView.setText(note.getTitle());
        dateTextView.setText(note.getDate());

        String description = note.getDescription();
        if (description.length() > 100) {
            description = description.substring(0, 100) + "...";
        }
        descriptionTextView.setText(description);

        String tags = TextUtils.join(", ", note.getTags());
        tagsTextView.setText(tags);

        return convertView;
    }
}
