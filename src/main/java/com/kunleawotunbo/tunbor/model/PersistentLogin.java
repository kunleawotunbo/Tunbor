/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.kunleawotunbo.tunbor.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author olakunle
 */
@Entity
@Table(name = "persistent_logins")
@NamedQueries({
    @NamedQuery(name = "PersistentLogin.findAll", query = "SELECT p FROM PersistentLogin p")})
public class PersistentLogin implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "username")
    private String username;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "series")
    private String series;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 64)
    @Column(name = "token")
    private String token;
    @Basic(optional = false)
    @NotNull
    @Column(name = "last_used")
    @Temporal(TemporalType.TIMESTAMP)
    private Date lastUsed;

    public PersistentLogin() {
    }

    public PersistentLogin(String series) {
        this.series = series;
    }

    public PersistentLogin(String series, String username, String token, Date lastUsed) {
        this.series = series;
        this.username = username;
        this.token = token;
        this.lastUsed = lastUsed;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getSeries() {
        return series;
    }

    public void setSeries(String series) {
        this.series = series;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Date getLastUsed() {
        return lastUsed;
    }

    public void setLastUsed(Date lastUsed) {
        this.lastUsed = lastUsed;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (series != null ? series.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PersistentLogin)) {
            return false;
        }
        PersistentLogin other = (PersistentLogin) object;
        if ((this.series == null && other.series != null) || (this.series != null && !this.series.equals(other.series))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.kunleawotunbo.tunbor.model.PersistentLogin[ series=" + series + " ]";
    }
    
}
