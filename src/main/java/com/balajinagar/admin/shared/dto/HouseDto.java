package com.balajinagar.admin.shared.dto;

import java.io.Serializable;
import java.util.List;

public class HouseDto implements Serializable {

    private static final long serialVersionUID = 6012044L;

    private String houseId;
    private String houseNo;
    private String streetName;
    private String areaName;
    private String electricityBillNo;
    private String gasConnectionNo;
    private String propertyTaxNo;
    private String waterTaxNo;
    private String type;
    private List<ResidentDto> residents;

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

    public List<ResidentDto> getResidents() {
        return residents;
    }

    public void setResidents(List<ResidentDto> residents) {
        this.residents = residents;
    }
}
