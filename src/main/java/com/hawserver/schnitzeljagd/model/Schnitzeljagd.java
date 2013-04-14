package com.hawserver.schnitzeljagd.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author egon
 */
@Entity
@Table(name = "Schnitzeljagd")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Schnitzeljagd.findAll", query = "SELECT s FROM Schnitzeljagd s"),
    @NamedQuery(name = "Schnitzeljagd.findById", query = "SELECT s FROM Schnitzeljagd s WHERE s.id = :id"),
    @NamedQuery(name = "Schnitzeljagd.findByName", query = "SELECT s FROM Schnitzeljagd s WHERE s.name = :name"),
    @NamedQuery(name = "Schnitzeljagd.deleteById", query = "DELETE FROM Schnitzeljagd s WHERE s.id = :id")})
public class Schnitzeljagd implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "Name")
    private String name;
    @OneToMany(mappedBy = "schnitzeljagdId")
    private List<Ziel> zielList;

    public Schnitzeljagd() {
    }

    public Schnitzeljagd(Integer id) {
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

    @XmlTransient
    public List<Ziel> getZielList() {
        return zielList;
    }

    public void setZielList(List<Ziel> zielList) {
        this.zielList = zielList;
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
        if (!(object instanceof Schnitzeljagd)) {
            return false;
        }
        Schnitzeljagd other = (Schnitzeljagd) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "de.hawserver.schnitzeljagd.Schnitzeljagd[ id=" + id + " ]";
    }
    
}
