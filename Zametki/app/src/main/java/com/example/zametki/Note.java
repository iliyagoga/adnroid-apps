package com.example.zametki;

import java.util.List;

public class Note {
    private long id;
    private String title;
    private String description;
    private String date;
    private List<String> tags; // Список тегов

    public Note(long id, String title, String description, String date, List<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.date = date;
        this.tags = tags;
    }

    public long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDate() {
        return date;
    }

    public List<String> getTags() {
        return tags;
    }
}
