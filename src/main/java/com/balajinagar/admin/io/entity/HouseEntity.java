package com.balajinagar.admin.io.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Table(name = "houses")
public class HouseEntity implements Serializable {

    private static final long serialVersionUID = 6012041L;

    @Id
    private String houseId;

    @Column(nullable = false)
    private String houseNo;

    @Column(nullable = false)
    private String streetName;

    @Column(nullable = false)
    private String areaName;

    @Column
    private String electricityBillNo;

    @Column
    private String gasConnectionNo;

    @Column
    private String propertyTaxNo;

    @Column
    private String waterTaxNo;

    @Column(nullable = false)
    private String type;

    @OneToMany(mappedBy = "houseDetails", cascade = CascadeType.ALL)
    private List<ResidentEntity> residents;

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

    public String getElectricityBillNo() {
        return electricityBillNo;
    }

    public void setElectricityBillNo(String electricityBillNo) {
        this.electricityBillNo = electricityBillNo;
    }

    public String getGasConnectionNo() {
        return gasConnectionNo;
    }

    public void setGasConnectionNo(String gasConnectionNo) {
        this.gasConnectionNo = gasConnectionNo;
    }

    public String getPropertyTaxNo() {
        return propertyTaxNo;
    }

    public void setPropertyTaxNo(String propertyTaxNo) {
        this.propertyTaxNo = propertyTaxNo;
    }

    public String getWaterTaxNo() {
        return waterTaxNo;
    }

    public void setWaterTaxNo(String waterTaxNo) {
        this.waterTaxNo = waterTaxNo;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<ResidentEntity> getResidents() {
        return residents;
    }

    public void setResidents(List<ResidentEntity> residents) {
        this.residents = residents;
    }
}
