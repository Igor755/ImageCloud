package com.image.imagecloud;

import com.google.firebase.database.Exclude;

public class Image {


    private String name;
    private String uri;

    public String key;

    @Exclude
    public String getKey() {
        return key;
    }

    @Exclude
    public void setKey(String key) {
        this.key = key;
    }

    public Image(){

    }


    public Image(String name, String uri) {
        if (name.trim().equals("")){
            name = "no name";
        }
        this.uri = uri;
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getName() {
        return name;
    }

    public void setName(String namel) {
        this.name = namel;
    }
}
