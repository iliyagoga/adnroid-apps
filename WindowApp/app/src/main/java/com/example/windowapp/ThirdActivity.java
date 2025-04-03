package com.example.windowapp;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ThirdActivity extends AppCompatActivity {

    private ListView listView;
    private List<Map<String, Object>> dataList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        listView = findViewById(R.id.listView);
        dataList = new ArrayList<>();
        for (int i = 1; i <= 20; i++) {
            Map<String, Object> item = new HashMap<String, Object>();
            item.put("image", R.drawable.oip);
            item.put("text", "текст" + i);
            dataList.add(item);
        }
        String[] from ={"image","text"};
        int[] to = {R.id.item_image, R.id.item_text};
        SimpleAdapter adapter = new SimpleAdapter(this, dataList, R.layout.block_imgs, from,to);
        listView.setAdapter(adapter);
    }

}