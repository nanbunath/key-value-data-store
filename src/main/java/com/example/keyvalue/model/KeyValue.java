package com.example.keyvalue.model;

import org.json.JSONObject;

import java.io.Serializable;

public class KeyValue implements Serializable{
    private String key;

    private JSONObject jsonValue;

    private long timeToLive;


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public JSONObject getJsonValue() {
        return jsonValue;
    }

    public void setJsonValue(JSONObject jsonValue) {
        this.jsonValue = jsonValue;
    }

    public long getTimeToLive() {
        return timeToLive;
    }

    public void setTimeToLive(long timeToLive) {
        this.timeToLive = timeToLive;
    }
}
