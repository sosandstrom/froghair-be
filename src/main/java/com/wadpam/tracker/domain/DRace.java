package com.wadpam.tracker.domain;

import com.google.appengine.api.blobstore.BlobKey;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Entity;

import net.sf.mardao.core.domain.AbstractLongEntity;

/**
 * Created with IntelliJ IDEA.
 *
 * @author osandstrom
 * Date: 1/24/14 Time: 6:40 PM
 */
@Entity
public class DRace extends AbstractLongEntity {

  @Basic
  private BlobKey blobKey;
    
  @Basic
  private String displayName;

  @Basic
  private String extractorClassname;

  @Basic
  private String imageUri;
  
  @Basic
  private String queryUrl;

  @Basic
  private Date startDate;

  @Basic
  private String timeZone;
  
    public BlobKey getBlobKey() {
        return blobKey;
    }

    public void setBlobKey(BlobKey blobKey) {
        this.blobKey = blobKey;
    }

  public String getDisplayName() {
    return displayName;
  }

  public void setDisplayName(String displayName) {
    this.displayName = displayName;
  }

  public String getQueryUrl() {
    return queryUrl;
  }

  public void setQueryUrl(String queryUrl) {
    this.queryUrl = queryUrl;
  }

  public String getExtractorClassname() {
    return extractorClassname;
  }

  public void setExtractorClassname(String extractorClassname) {
    this.extractorClassname = extractorClassname;
  }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public String getTimeZone() {
        return timeZone;
    }

    public void setTimeZone(String timeZone) {
        this.timeZone = timeZone;
    }

    public String getImageUri() {
        return imageUri;
    }

    public void setImageUri(String imageUri) {
        this.imageUri = imageUri;
    }

}
