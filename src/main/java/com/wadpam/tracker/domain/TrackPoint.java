package com.wadpam.tracker.domain;

import java.io.Serializable;

/**
 *
 * @author osandstrom
 */
public class TrackPoint implements Serializable {
    private float lat;
    private float lon;
    private long t;
    private float alt;
    private float d;

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public float getLon() {
        return lon;
    }

    public void setLon(float lon) {
        this.lon = lon;
    }

    public long getT() {
        return t;
    }

    public void setT(long t) {
        this.t = t;
    }

    public float getAlt() {
        return alt;
    }

    public void setAlt(float alt) {
        this.alt = alt;
    }

    public float getD() {
        return d;
    }

    public void setD(float d) {
        this.d = d;
    }
    
}
