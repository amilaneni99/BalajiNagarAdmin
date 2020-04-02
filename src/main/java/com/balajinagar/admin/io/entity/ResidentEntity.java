package com.balajinagar.admin.io.entity;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "residents")
public class ResidentEntity implements Serializable {
    private static final long serialVersionUID = 6012042L;

    @Id
    private String residentId;

    @Column(nullable = false)
    private String firstName;

    @Column
    private String lastName;

    @Column(nullable = false)
    private String dob;

    @Column(nullable = false)
    private String sex;

    @Column(nullable = false)
    private String aadhaarNo;

    @Column(nullable = false)
    private String voterId;

    @Column(nullable = false)
    private Boolean familyHead;

    @Column(nullable = false)
    private String fatherOrHusbandName;

    @Column(nullable = false)
    private String relationToHead;

    @Column
    private String qualification;

    @Column(nullable = false)
    private String occupation;

    @Column(nullable = false, length = 12)
    private String mobileNo;

    @Column(nullable = false)
    private String bloodGroup;

    @ManyToOne
    @JoinColumn(name = "houseId")
    private HouseEntity houseDetails;



    public String getResidentId() {
        return residentId;
    }

    public void setResidentId(String residentId) {
        this.residentId = residentId;
    }

    public HouseEntity getHouseDetails() {
        return houseDetails;
    }

    public void setHouseDetails(HouseEntity houseDetails) {
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
