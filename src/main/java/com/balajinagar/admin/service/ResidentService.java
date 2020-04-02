package com.balajinagar.admin.service;

import com.balajinagar.admin.shared.dto.ResidentDto;

import java.util.List;

public interface ResidentService {
    List<ResidentDto> getResidents(String houseId);
    ResidentDto getResident(String residentId);
    List<ResidentDto> getResidentByFirstName(String firstName);
    List<ResidentDto> getAllResidents(int page, int limit);
    List<ResidentDto> getAllBySex(String sex, int page, int limit);
    ResidentDto updateResident(String residentId, ResidentDto residentDto);
}
