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
    public HouseDto createHouse(HouseDto house) {
        if (houseRepository.findHouseByHouseNo(house.getHouseNo()) != null)
            throw new HouseServiceException("House already exists");

        for (int i=0;i<house.getResidents().size();i++){
            ResidentDto resident = house.getResidents().get(i);
            resident.setHouseDetails(house);
            resident.setResidentId(utils.generateResidentId(20));
            house.getResidents().set(i, resident);
        }

        ModelMapper modelMapper = new ModelMapper();
        HouseEntity houseEntity = new HouseEntity();
        houseEntity = modelMapper.map(house, HouseEntity.class);

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

        Page<HouseEntity> housePages = houseRepository.findHouseByFamilyHeadName(name, pageableRequest);

        List<HouseEntity> houseEntities = housePages.getContent();

        if (houseEntities.size() == 0)
            throw new HouseServiceException("No records found");

        List<HouseDto> returnValue = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();

        for (HouseEntity houseEntity : houseEntities){
            HouseDto houseDto = modelMapper.map(houseEntity, HouseDto.class);
            returnValue.add(houseDto);
        }
        return returnValue;
    }

    @Override
    public List<HouseDto> getHouseByResidentName(String name, int page, int limit) {
        if (page >0 ) page-=1;

        Pageable pageableRequest = PageRequest.of(page, limit);

        Page<HouseEntity> housePages = houseRepository.findAllByFirstName(name, pageableRequest);

        List<HouseEntity> houseEntities = housePages.getContent();

        if (houseEntities.size() == 0)
            throw new HouseServiceException("No records found");

        List<HouseDto> returnValue = new ArrayList<>();

        ModelMapper modelMapper = new ModelMapper();

        for (HouseEntity houseEntity : houseEntities){
            HouseDto houseDto = modelMapper.map(houseEntity, HouseDto.class);
            returnValue.add(houseDto);
        }
        return returnValue;
    }

    @Override
    public HouseDto addResident(String houseId, ResidentDto resident) {
        HouseEntity houseEntity = houseRepository.findHouseByHouseId(houseId);
        HouseDto houseDto = new ModelMapper().map(houseEntity, HouseDto.class);

        if (houseEntity == null)
            throw new HouseServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        resident.setResidentId(utils.generateResidentId(20));
        resident.setHouseDetails(houseDto);

        List<ResidentDto> residents = houseDto.getResidents();

        ResidentDto newResident = new ModelMapper().map(resident, ResidentDto.class);

        residents.add(newResident);

        houseDto.setResidents(residents);

        HouseEntity newHouseEntity = new ModelMapper().map(houseDto, HouseEntity.class);

        HouseEntity updatedHouse = houseRepository.save(newHouseEntity);

        HouseDto returnValue = new ModelMapper().map(updatedHouse, HouseDto.class);

        return returnValue;
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
    public HouseDto deleteAllResidents(String houseId) {
        HouseEntity houseEntity = houseRepository.findHouseByHouseId(houseId);

        if (houseEntity == null)
            throw new HouseServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

//        List<ResidentEntity> deleteResidents = houseEntity.getResidents();
//        deleteResidents.removeAll(deleteResidents);
//        houseEntity.setResidents(deleteResidents);

        residentRepository.deleteAllByHouseId(houseId);

        HouseEntity updatedHouse = houseRepository.save(houseEntity);

        HouseDto returnValue = new ModelMapper().map(updatedHouse, HouseDto.class);
        return returnValue;
    }

    @Override
    public HouseDto deleteResident(String houseId, String residentId) {
        HouseEntity houseEntity = houseRepository.findHouseByHouseId(houseId);
        ResidentEntity resident = residentRepository.findByResidentId(residentId);
        residentRepository.delete(resident);

        if (houseEntity == null)
            throw new HouseServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        HouseEntity updatedHouse = houseRepository.save(houseEntity);

        HouseDto returnValue = new ModelMapper().map(updatedHouse, HouseDto.class);

        return returnValue;
    }

    @Override
    public void deleteHouse(String houseNo) {
        HouseEntity houseEntity = houseRepository.findHouseByHouseId(houseNo);

        if (houseEntity == null)
            throw new HouseServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        houseRepository.delete(houseEntity);
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
}
