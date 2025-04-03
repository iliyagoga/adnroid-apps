package com.example.zametki;

import java.util.List;

public class Tag {
    private String id;
    private String tag_name;

    public Tag(String id, String tag_name) {
        this.id = id;
        this.tag_name = tag_name;

    }

    public String getId() {
        return id;
    }

    public String getTagName() {
        return tag_name;
    }

}
