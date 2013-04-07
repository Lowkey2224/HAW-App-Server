package com.hawserver.mensaplan.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.joda.time.DateMidnight;

@Entity
@Table(name="MenutItem")
@NamedQueries({
    @NamedQuery(name = "MenuItem.deleteAll", query = "DELETE FROM MenuItem m"),
    @NamedQuery(name = "MenuItem.findAll", query = "SELECT m FROM MenuItem m"),
    @NamedQuery(name = "MenuItem.findById", query = "SELECT m FROM MenuItem m WHERE m.id = :id"),
    @NamedQuery(name = "MenuItem.findByDateOfItem", query = "SELECT m FROM MenuItem m WHERE m.dateOfItem = :dateOfItem")})
public class MenuItem {
    
    public MenuItem() {
    }
    
    public MenuItem(Date date, int studentPrice, int nonStudentPrice,
            String description, String category, String attributes) {
        super();
        this.dateOfItem = date;
        this.studentPrice = studentPrice;
        this.nonStudentPrice = nonStudentPrice;
        this.description = description;
        this.category = category;
        this.attributes = attributes;
    }



    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public Date getDateOfItem() {
        return dateOfItem;
    }
    
    public DateMidnight getDateMidnight(){
        return new DateMidnight(dateOfItem);
    }
    
    public void setDateOfItem(Date date) {
        this.dateOfItem = date;
    }
    public int getStudentPrice() {
        return studentPrice;
    }
    public void setStudentPrice(int studentPrice) {
        this.studentPrice = studentPrice;
    }
    public int getNonStudentPrice() {
        return nonStudentPrice;
    }
    public void setNonStudentPrice(int nonStudentPrice) {
        this.nonStudentPrice = nonStudentPrice;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getAttributes() {
        return attributes;
    }
    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long id;
    
    @Column(name="DateOfItem")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dateOfItem;
    
    @Column(name="StudentPrice")
    private int studentPrice;
    
    @Column(name="NonStudentPrice")
    private int nonStudentPrice;
    
    @Column(name="Description")
    private String description;
    
    @Column(name="Category")
    private String category;
    
    @Column(name="Attributes")
    private String attributes;
    
}
