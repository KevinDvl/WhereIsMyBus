package com.example.whereismybus;

public class HierarchieJSON {
    private String jsonArray;
    private String jsonObject;
    private String[] fields;

    public HierarchieJSON(String jsonArray, String jsonObject, String[] fields) {
        this.jsonArray = jsonArray;
        this.jsonObject = jsonObject;
        this.fields = fields;
    }

    public String getJsonArray() {
        return jsonArray;
    }

    public String getJsonObject() {
        return jsonObject;
    }

    public String getField(int i) {
        return fields[i];
    }
}
