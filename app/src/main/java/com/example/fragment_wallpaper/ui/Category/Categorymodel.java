package com.example.fragment_wallpaper.ui.Category;



public class Categorymodel {

    String image;
    String type;
    String name;
    String user_image;
    String user_name;
    String website_icon;
    String website_name;
    String categoryid;

    public Categorymodel(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getCategoryId() {
        return categoryid;
    }

    public void setCategoryId(String categoryid) {
        this.categoryid = categoryid;
    }

    public Categorymodel() {
    }

    public Categorymodel(String image, String type, String name, String user_image, String user_name, String website_icon, String website_name) {
        this.image = image;
        this.type = type;
        this.name = name;
        this.user_image = user_image;
        this.user_name = user_name;
        this.website_icon = website_icon;
        this.website_name = website_name;



    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getWebsite_icon() {
        return website_icon;
    }

    public void setWebsite_icon(String website_icon) {
        this.website_icon = website_icon;
    }

    public String getWebsite_name() {
        return website_name;
    }

    public void setWebsite_name(String website_name) {
        this.website_name = website_name;
    }
}
