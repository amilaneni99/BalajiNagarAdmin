package com.balajinagar.admin.io.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "houses")
public class HouseEntity implements Serializable {

    private static final long serialVersionUID = 6012041L;

    @Id
    private String houseId;

    @Column(nullable = false)
    private String houseNo;

    @Column
    private String plotNo;

    @Column(nullable = false)
    private String ownerName;

    @Column(nullable = false)
    private String noOfFloors;

    @Column(nullable = false)
    private String streetName;

    @Column
    private String areaName;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false)
    private boolean vacant;

    @Column(nullable = false)
    private String ownerMobileNo;

    @Column
    private String rentAmount;

    public String getHouseId() {
        return houseId;
    }

    public void setHouseId(String houseId) {
        this.houseId = houseId;
    }

    public String getHouseNo() {
        return houseNo;
    }

    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public String getAreaName() {
        return areaName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getPlotNo() {
        return plotNo;
    }

    public void setPlotNo(String plotNo) {
        this.plotNo = plotNo;
    }

    public String getOwnerName() {
        return ownerName;
    }

    public void setOwnerName(String ownerName) {
        this.ownerName = ownerName;
    }

    public String getNoOfFloors() {
        return noOfFloors;
    }

    public void setNoOfFloors(String noOfFloors) {
        this.noOfFloors = noOfFloors;
    }

    public boolean isVacant() {
        return vacant;
    }

    public void setVacant(boolean vacant) {
        this.vacant = vacant;
    }

    public String getOwnerMobileNo() {
        return ownerMobileNo;
    }

    public void setOwnerMobileNo(String ownerMobileNo) {
        this.ownerMobileNo = ownerMobileNo;
    }

    public String getRentAmount() {
        return rentAmount;
    }

    public void setRentAmount(String rentAmount) {
        this.rentAmount = rentAmount;
    }
}
