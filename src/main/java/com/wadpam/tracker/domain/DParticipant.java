package com.wadpam.tracker.domain;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import net.sf.mardao.core.domain.AbstractLongEntity;

/**
 * Ties a User to a Race.
 * Contains the id of the Facebook fitness action.
 * @author osandstrom
 */
@Entity
@Table(uniqueConstraints = {@UniqueConstraint(columnNames = {"raceId", "userId"}),
    @UniqueConstraint(columnNames = {"actionId"})})
public class DParticipant extends AbstractLongEntity {

    /** Race id */
    @Basic
    private Long raceId;
    
    /** User id */
    @Basic
    private Long userId;
    
    /** id of the fitness action */
    @Basic
    private String actionId;

    /** External user id at results service */
    @Basic
    private String extUserId;
    
    @Basic
    private Integer status;
    
    public Long getRaceId() {
        return raceId;
    }

    public void setRaceId(Long raceId) {
        this.raceId = raceId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getActionId() {
        return actionId;
    }

    public void setActionId(String actionId) {
        this.actionId = actionId;
    }

    public String getExtUserId() {
        return extUserId;
    }

    public void setExtUserId(String extUserId) {
        this.extUserId = extUserId;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
    
}
