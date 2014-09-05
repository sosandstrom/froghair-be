package com.wadpam.tracker.opengraph;

/**
 *
 * @author osandstrom
 */
public class StandardObject {
    private String app_id;
    private String image;
    private String title;
//    private String type;

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

//    @JsonProperty("og:type")
//    public String getType() {
//        return type;
//    }
//
//    public void setType(String type) {
//        this.type = type;
//    }
    
    
}
