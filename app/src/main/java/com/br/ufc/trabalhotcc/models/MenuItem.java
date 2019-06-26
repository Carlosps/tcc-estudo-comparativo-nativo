package com.br.ufc.trabalhotcc.models;

import java.io.Serializable;

public class MenuItem implements Serializable {
    private String description;
    private String image;
    private String imageBase64;
    private String slug;
    private String title;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageFile() {
        return imageBase64;
    }

    public void setImageFile(String imageFile) {
        this.imageBase64 = imageFile;
    }

}
