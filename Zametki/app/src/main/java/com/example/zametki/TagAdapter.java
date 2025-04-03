package com.example.zametki;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class TagAdapter extends BaseAdapter {

    private Context context;
    private List<Tag> tags;

    private LayoutInflater inflater;

    public TagAdapter(Context context, List<Tag> tags) {
        this.context = context;
        this.tags = tags;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return tags.size();
    }

    @Override
    public Object getItem(int position) {
        return tags.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_tag, parent, false);
        }

        TextView textView = convertView.findViewById(R.id.tagName);

        textView.setText(tags.get(position).getTagName());

        textView.setPadding(10, 5, 10, 5);
        textView.setBackgroundResource(android.R.drawable.btn_default);

        return convertView;
    }


}
