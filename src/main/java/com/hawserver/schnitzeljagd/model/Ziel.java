package com.hawserver.schnitzeljagd.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author egon
 */
@Entity
@Table(name = "Ziel")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ziel.findAll", query = "SELECT z FROM Ziel z"),
    @NamedQuery(name = "Ziel.findById", query = "SELECT z FROM Ziel z WHERE z.id = :id"),
    @NamedQuery(name = "Ziel.findByName", query = "SELECT z FROM Ziel z WHERE z.name = :name"),
    @NamedQuery(name = "Ziel.findByLongitude", query = "SELECT z FROM Ziel z WHERE z.longitude = :longitude"),
    @NamedQuery(name = "Ziel.findByLatitude", query = "SELECT z FROM Ziel z WHERE z.latitude = :latitude"),
    @NamedQuery(name = "Ziel.findByCode", query = "SELECT z FROM Ziel z WHERE z.code = :code"),
    @NamedQuery(name = "Ziel.findBySchnitzeljagdId", query = "SELECT z FROM Ziel z WHERE z.schnitzeljagdId = :schnitzeljagdId"),
    @NamedQuery(name = "Ziel.deleteById", query = "DELETE FROM Ziel z WHERE z.id = :id"),
    @NamedQuery(name = "Ziel.deleteBySchnitzeljagdId", query = "DELETE FROM Ziel z WHERE z.schnitzeljagdId = :schnitzeljagdId")})
public class Ziel implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "Id")
    private Integer id;
    @Column(name = "Name")
    private String name;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "Longitude")
    private Double longitude;
    @Column(name = "Latitude")
    private Double latitude;
    @Column(name = "Code")
    private String code;
    @Column(name = "Radius")
    private Double radius;
    @JoinColumn(name = "SchnitzeljagdId", referencedColumnName = "id")
    @ManyToOne
    private Schnitzeljagd schnitzeljagdId;

    public Ziel() {
    }

    public Ziel(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getRadius() {
        return radius;
    }
    
    public void setRadius(Double radius) {
        this.radius = radius;
    }
    
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Schnitzeljagd getSchnitzeljagdId() {
        return schnitzeljagdId;
    }

    public void setSchnitzeljagdId(Schnitzeljagd schnitzeljagdId) {
        this.schnitzeljagdId = schnitzeljagdId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ziel)) {
            return false;
        }
        Ziel other = (Ziel) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hawserver.schnitzeljagd.Ziel[ id=" + id + " ]";
    }
    
}
