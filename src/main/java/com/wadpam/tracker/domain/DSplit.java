package com.wadpam.tracker.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import net.sf.mardao.core.Parent;

import net.sf.mardao.core.domain.AbstractLongEntity;
import net.sf.mardao.core.geo.DLocation;

/**
 * Created with IntelliJ IDEA.
 *
 * @author osandstrom
 * Date: 1/24/14 Time: 6:40 PM
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"raceKey", "name"}),
    @UniqueConstraint(columnNames = {"raceKey", "timestamp"})})
public class DSplit extends AbstractLongEntity {

  @Parent(kind="DRace")
  private Object raceKey;
  
  @Basic
  private String name;
    
  @Basic
  private Long trackPointId;
  
  @Basic
  private DLocation point;

  @Basic
  private Long timestamp;

  @Basic
  private Float elevation;

  @Basic
  private Float distance;

    @Override
    public String toString() {
        return name + '@' + timestamp;
    }

  
    public Object getRaceKey() {
        return raceKey;
    }

    public void setRaceKey(Object raceKey) {
        this.raceKey = raceKey;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public DLocation getPoint() {
        return point;
    }

    public void setPoint(DLocation point) {
        this.point = point;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }

    public Float getElevation() {
        return elevation;
    }

    public void setElevation(Float elevation) {
        this.elevation = elevation;
    }

    public Long getTrackPointId() {
        return trackPointId;
    }

    public void setTrackPointId(Long trackPointId) {
        this.trackPointId = trackPointId;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

}
