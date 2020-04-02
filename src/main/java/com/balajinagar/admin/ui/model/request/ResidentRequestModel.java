package com.balajinagar.admin.ui.model.request;

import com.balajinagar.admin.shared.dto.HouseDto;

public class ResidentRequestModel {
    private String firstName;
    private String lastName;
    private String dob;
    private String sex;
    private String aadhaarNo;
    private String voterId;
    private Boolean familyHead;
    private String fatherOrHusbandName;
    private String relationToHead;
    private String qualification;
    private String occupation;
    private String mobileNo;
    private String bloodGroup;
    private HouseDto houseDetails;

    public HouseDto getHouseDetails() {
        return houseDetails;
    }

    public void setHouseDetails(HouseDto houseDetails) {
        this.houseDetails = houseDetails;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getAadhaarNo() {
        return aadhaarNo;
    }

    public void setAadhaarNo(String aadhaarNo) {
        this.aadhaarNo = aadhaarNo;
    }

    public String getVoterId() {
        return voterId;
    }

    public void setVoterId(String voterId) {
        this.voterId = voterId;
    }

    public Boolean getFamilyHead() {
        return familyHead;
    }

    public void setFamilyHead(Boolean familyHead) {
        this.familyHead = familyHead;
    }

    public String getFatherOrHusbandName() {
        return fatherOrHusbandName;
    }

    public void setFatherOrHusbandName(String fatherOrHusbandName) {
        this.fatherOrHusbandName = fatherOrHusbandName;
    }

    public String getRelationToHead() {
        return relationToHead;
    }

    public void setRelationToHead(String relationToHead) {
        this.relationToHead = relationToHead;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(String mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public void setBloodGroup(String bloodGroup) {
        this.bloodGroup = bloodGroup;
    }
}
