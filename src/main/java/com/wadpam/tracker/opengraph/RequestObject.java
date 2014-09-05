package com.wadpam.tracker.opengraph;

/**
 *
 * @author osandstrom
 */
public class RequestObject {
    private String object;
    private String access_token;

    public String getAccess_token() {
        return access_token;
    }

    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    
    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }
    
}
