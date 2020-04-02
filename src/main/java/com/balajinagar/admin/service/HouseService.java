package com.balajinagar.admin.service;

import com.balajinagar.admin.shared.dto.HouseDto;
import com.balajinagar.admin.shared.dto.ResidentDto;

import java.util.List;

public interface HouseService {
    HouseDto createHouse(HouseDto house);
    HouseDto getHouseDetails(String houseId);
    HouseDto getHouseDetailsByHouseNo(String houseNo);
    List<HouseDto> getHouseByHeadName(String name, int page, int limit);
    List<HouseDto> getHouseByResidentName(String name, int page, int limit);
    HouseDto addResident(String houseNo, ResidentDto resident);
    HouseDto updateHouse(String id, HouseDto houseDto);
    HouseDto deleteAllResidents(String houseId);
    HouseDto deleteResident(String houseId, String residentId);
    void deleteHouse(String houseId);
    List<HouseDto> getHouses(int page, int limit);
}
