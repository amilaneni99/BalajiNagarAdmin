package com.balajinagar.admin.service;

import com.balajinagar.admin.shared.dto.HouseDto;
import com.balajinagar.admin.shared.dto.ResidentDto;

import java.util.List;

public interface HouseService {
    List<String> getStreetNames();
    HouseDto createHouse(HouseDto house);
    HouseDto getHouseDetails(String houseId);
    String findHouseIdByNo(String houseNo);
    String findHouseNoById(String houseId);
    List<ResidentDto> getAllResidents(String houseNo);
    HouseDto getHouseDetailsByHouseNo(String houseNo);
    List<HouseDto> getHouseByHeadName(String name, int page, int limit);
    List<HouseDto> getHouseByResidentName(String name, int page, int limit);
    List<HouseDto> getHousesByPlotNo(String plotNo, int page, int limit);
    HouseDto updateHouse(String id, HouseDto houseDto);
    void deleteAllResidents(String houseId);
    void deleteHouse(String houseId);
    List<HouseDto> getHouses(int page, int limit);
    int getNumberOfHouses();
}
