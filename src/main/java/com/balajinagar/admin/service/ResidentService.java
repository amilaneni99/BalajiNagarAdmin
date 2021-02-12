package com.balajinagar.admin.service;

import com.balajinagar.admin.shared.dto.ResidentDto;

import java.util.List;

public interface ResidentService {
    List<ResidentDto> getResidentsByHouseId(String houseId);
    List<ResidentDto> getResidentsByHouseNo(String houseNo);
    ResidentDto getResident(String residentId);
    ResidentDto addResident(ResidentDto residentDto);
    List<ResidentDto> getResidentByFirstName(String firstName, int page, int limit);
    List<ResidentDto> getAllResidents(int page, int limit);
    List<ResidentDto> getAllBySex(String sex, int page, int limit);
    ResidentDto updateResident(String residentId, ResidentDto residentDto);
    void deleteResident(String residentId);
    void deleteAllResidentsByHouse(String houseId);
    int getNumberOfResidents();
}
