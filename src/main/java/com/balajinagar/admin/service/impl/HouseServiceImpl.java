package com.balajinagar.admin.service.impl;

import com.balajinagar.admin.exceptions.HouseServiceException;
import com.balajinagar.admin.io.entity.HouseEntity;
import com.balajinagar.admin.io.entity.ResidentEntity;
import com.balajinagar.admin.io.repositories.HouseRepository;
import com.balajinagar.admin.io.repositories.ResidentRepository;
import com.balajinagar.admin.service.HouseService;
import com.balajinagar.admin.shared.Utils;
import com.balajinagar.admin.shared.dto.HouseDto;
import com.balajinagar.admin.shared.dto.ResidentDto;
import com.balajinagar.admin.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HouseServiceImpl implements HouseService {

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    ResidentRepository residentRepository;

    @Autowired
    Utils utils;

    @Override
    public List<String> getStreetNames() {
        return houseRepository.getAllStreetNames();
    }

    @Override
    public HouseDto createHouse(HouseDto house) {
        if (houseRepository.findHouseByHouseNo(house.getHouseNo()) != null)
            throw new HouseServiceException("House already exists");

        ModelMapper modelMapper = new ModelMapper();
        HouseEntity houseEntity = modelMapper.map(house, HouseEntity.class);

        String houseId = utils.generateHouseId(20);
        houseEntity.setHouseId(houseId);

        HouseEntity storedHouseDetails = houseRepository.save(houseEntity);

        HouseDto returnValue = modelMapper.map(storedHouseDetails, HouseDto.class);

        return returnValue;
    }

    @Override
    public HouseDto getHouseDetails(String houseId) {
        HouseEntity houseEntity = houseRepository.findHouseByHouseId(houseId);

        if (houseEntity == null)
            throw new HouseServiceException(houseId);

        HouseDto returnValue = new ModelMapper().map(houseEntity, HouseDto.class);

        return returnValue;
    }

    @Override
    public String findHouseIdByNo(String houseNo) {
        if(houseRepository.findHouseIdByHouseNo(houseNo) == null)
            throw new HouseServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        return houseRepository.findHouseIdByHouseNo(houseNo);
    }

    @Override
    public String findHouseNoById(String houseId) {
        if(houseRepository.findHouseNoByHouseId(houseId) == null)
            throw new HouseServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());
        return houseRepository.findHouseNoByHouseId(houseId);
    }

    @Override
    public List<ResidentDto> getAllResidents(String houseNo) {
        String houseId = this.findHouseIdByNo(houseNo);
        List<ResidentEntity> residents = residentRepository.findAllByHouseId(houseId);
        List<ResidentDto> returnValue = new ArrayList<>();

        for(ResidentEntity residentEntity : residents) {
            returnValue.add(new ModelMapper().map(residentEntity, ResidentDto.class));
        }

        return returnValue;
    }

    @Override
    public HouseDto getHouseDetailsByHouseNo(String houseNo) {
        HouseEntity houseEntity = houseRepository.findHouseByHouseNo(houseNo);

        if (houseEntity == null)
            throw new HouseServiceException(houseNo);

        HouseDto returnValue = new ModelMapper().map(houseEntity, HouseDto.class);

        return returnValue;
    }

    @Override
    public List<HouseDto> getHouseByHeadName(String name, int page, int limit) {

        if (page >0 ) page-=1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<ResidentEntity> residentQueryResults = residentRepository.findResidentsByFamilyHeadName(name, pageableRequest);

        List<ResidentEntity> residents = residentQueryResults.getContent();

        if (residents.size() == 0)
            throw new HouseServiceException("No records found");

        List<HouseDto> returnValue = new ArrayList<>();

        for(ResidentEntity residentEntity: residents) {
            String houseId = residentEntity.getHouseId();
            returnValue.add(new ModelMapper().map(houseRepository.findHouseByHouseId(houseId), HouseDto.class));
        }

        return returnValue;
    }

    @Override
    public List<HouseDto> getHouseByResidentName(String name, int page, int limit) {
        if (page >0 ) page-=1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<ResidentEntity> residentQueryResults = residentRepository.findByFirstNameOrderByFirstName(name, pageableRequest);
        List<ResidentEntity> residentPages = residentQueryResults.getContent();

        List<HouseDto> housesByFirstName = new ArrayList<>();

        for(ResidentEntity residentEntity: residentPages) {
            String houseId = residentEntity.getHouseId();
            HouseEntity houseEntity = houseRepository.findHouseByHouseId(houseId);
            housesByFirstName.add(new ModelMapper().map(houseEntity, HouseDto.class));
        }

        if (residentPages.size() == 0)
            throw new HouseServiceException("No records found");

        return housesByFirstName;
    }

    @Override
    public HouseDto updateHouse(String id, HouseDto houseDto) {
        HouseDto returnVal = new HouseDto();

        HouseEntity houseEntity = houseRepository.findHouseByHouseId(id);

        if (houseEntity == null) throw new HouseServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        if (houseDto.getType() != null)
            houseEntity.setType(houseDto.getType());

        HouseEntity houseEntityNew = houseRepository.save(houseEntity);

        returnVal = new ModelMapper().map(houseEntityNew, HouseDto.class);

        return returnVal;
    }

    @Override
    public void deleteAllResidents(String houseId) {
        residentRepository.deleteAllByHouseId(houseId);
    }

    @Override
    public void deleteHouse(String houseId) {
        HouseEntity houseEntity = houseRepository.findHouseByHouseId(houseId);

        if (houseEntity == null)
            throw new HouseServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        houseRepository.delete(houseEntity);
        if (residentRepository.findAllByHouseId(houseId).size() == 0) return;
        residentRepository.deleteAllByHouseId(houseId);
    }

    @Override
    public List<HouseDto> getHouses(int page, int limit) {
        List<HouseDto> returnValue = new ArrayList<>();

        if (page >0 ) page-=1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<HouseEntity> housesPage = houseRepository.findAll(pageableRequest);

        List<HouseEntity> houses = housesPage.getContent();

        for (HouseEntity houseEntity : houses){
            HouseDto houseDto = new ModelMapper().map(houseEntity, HouseDto.class);
            returnValue.add(houseDto);
        }

        return returnValue;
    }

    @Override
    public int getNumberOfHouses() {
        return houseRepository.getNumberOfHouses();
    }

    @Override
    public List<HouseDto> getHousesByPlotNo(String plotNo, int page, int limit) {
        List<HouseDto> returnValue = new ArrayList<>();

        if(page > 0) page-=1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<HouseEntity> housesPage = houseRepository.findHousesByPlotNo(plotNo, pageableRequest);

        List<HouseEntity> houses = housesPage.getContent();

        for(HouseEntity houseEntity: houses) {
            HouseDto houseDto = new ModelMapper().map(houseEntity, HouseDto.class);
            returnValue.add(houseDto);
        }

        return returnValue;
    }
}
