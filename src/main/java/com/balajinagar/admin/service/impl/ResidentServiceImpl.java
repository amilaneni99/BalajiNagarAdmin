package com.balajinagar.admin.service.impl;

import com.balajinagar.admin.exceptions.HouseServiceException;
import com.balajinagar.admin.exceptions.ResidentServiceException;
import com.balajinagar.admin.io.entity.ResidentEntity;
import com.balajinagar.admin.io.repositories.HouseRepository;
import com.balajinagar.admin.io.repositories.ResidentRepository;
import com.balajinagar.admin.service.HouseService;
import com.balajinagar.admin.service.ResidentService;
import com.balajinagar.admin.shared.Utils;
import com.balajinagar.admin.shared.dto.ResidentDto;
import com.balajinagar.admin.ui.model.response.ErrorMessages;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ResidentServiceImpl implements ResidentService {

    @Autowired
    HouseRepository houseRepository;

    @Autowired
    ResidentRepository residentRepository;

    @Autowired
    HouseService houseService;

    @Autowired
    Utils utils;


    @Override
    public List<ResidentDto> getResidentsByHouseId(String houseId) {
        List<ResidentEntity> residentEntities = residentRepository.findAllByHouseId(houseId);

        List<ResidentDto> returnValue = new ArrayList<>();

        for (ResidentEntity residentEntity : residentEntities){
            ResidentDto residentDto = new ModelMapper().map(residentEntity, ResidentDto.class);
            returnValue.add(residentDto);
        }

        return returnValue;
    }

    @Override
    public List<ResidentDto> getResidentsByHouseNo(String houseNo) {
        List<ResidentEntity> residentEntities = residentRepository.getResidentsByHouseNo(houseNo);
        List<ResidentDto> returnValue = new ArrayList<>();

        for(ResidentEntity residentEntity: residentEntities) {
            ResidentDto residentDto = new ModelMapper().map(residentEntity, ResidentDto.class);
            returnValue.add(residentDto);
        }

        return returnValue;
    }

    @Override
    public ResidentDto getResident(String residentId) {
        ResidentEntity residentEntity = residentRepository.findByResidentId(residentId);

        return new ModelMapper().map(residentEntity, ResidentDto.class);
    }

    @Override
    public ResidentDto addResident(ResidentDto residentDto) {
        if (residentRepository.findByResidentId(residentDto.getResidentId()) != null)
            throw new HouseServiceException("House already exists");

        ModelMapper modelMapper = new ModelMapper();
        ResidentEntity residentEntity = modelMapper.map(residentDto, ResidentEntity.class);

        String residentId = utils.generateResidentId(20);
        residentEntity.setResidentId(residentId);

        ResidentEntity storedResidentDetails = residentRepository.save(residentEntity);

        ResidentDto returnValue = modelMapper.map(storedResidentDetails, ResidentDto.class);

        return returnValue;
    }

    @Override
    public List<ResidentDto> getResidentByFirstName(String firstName, int page, int limit) {
        if (page > 0) page-=1;
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<ResidentEntity> residentPages = residentRepository.findByFirstNameOrderByFirstName(firstName, pageableRequest);
        List<ResidentEntity> residents = residentPages.getContent();

        if(residents.size() == 0)
            throw new ResidentServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        List<ResidentDto> returnValue = new ArrayList<>();
        for (ResidentEntity residentEntity : residents){
            ResidentDto residentDto = new ModelMapper().map(residentEntity, ResidentDto.class);
            returnValue.add(residentDto);
        }
        return returnValue;
    }

    @Override
    public List<ResidentDto> getAllResidents(int page, int limit) {
        if (page >0 ) page-=1;
        Pageable pageableRequest = PageRequest.of(page, limit);
        Page<ResidentEntity> residentPages = residentRepository.findAllByOrderByFirstName(pageableRequest);
        List<ResidentEntity> residents = residentPages.getContent();
        List<ResidentDto> returnValue = new ArrayList<>();
        for (ResidentEntity residentEntity : residents){
            ResidentDto residentDto = new ModelMapper().map(residentEntity, ResidentDto.class);
            returnValue.add(residentDto);
        }
        return returnValue;
    }

    @Override
    public List<ResidentDto> getAllBySex(String sex, int page, int limit) {
        if (page >0 ) page-=1;
        Pageable pageableRequest = PageRequest.of(page, limit, Sort.by("firstName"));
        Page<ResidentEntity> residentPages = residentRepository.findAllBySex(sex, pageableRequest);
        List<ResidentEntity> residents = residentPages.getContent();
        List<ResidentDto> returnValue = new ArrayList<>();
        for (ResidentEntity residentEntity : residents){
            ResidentDto residentDto = new ModelMapper().map(residentEntity, ResidentDto.class);
            returnValue.add(residentDto);
        }
        return returnValue;
    }

    @Override
    public ResidentDto updateResident(String residentId, ResidentDto residentDto) {
        ResidentDto returnVal = new ResidentDto();
        ResidentEntity residentEntity = residentRepository.findByResidentId(residentId);
        if (residentEntity == null) throw new HouseServiceException(ErrorMessages.NO_RECORD_FOUND.getErrorMessage());

        if (residentDto.getLastName() != null)
            residentEntity.setLastName(residentDto.getLastName());

        if (residentDto.getFamilyHead() != null)
            residentEntity.setFamilyHead(residentDto.getFamilyHead());

        if (residentDto.getFatherOrHusbandName() != null)
            residentEntity.setFatherOrHusbandName(residentDto.getFatherOrHusbandName());

        if (residentDto.getRelationToHead() != null)
            residentEntity.setRelationToHead(residentDto.getRelationToHead());

        if (residentDto.getQualification() != null)
            residentEntity.setQualification(residentDto.getQualification());

        if (residentDto.getMobileNo() != null)
            residentEntity.setMobileNo(residentDto.getMobileNo());

        ResidentEntity residentEntityNew = residentRepository.save(residentEntity);

        returnVal = new ModelMapper().map(residentEntityNew, ResidentDto.class);

        return returnVal;
    }

    @Override
    public void deleteResident(String residentId) {
        ResidentEntity residentEntity = residentRepository.findByResidentId(residentId);
        residentRepository.delete(residentEntity);
    }

    @Override
    public void deleteAllResidentsByHouse(String houseId) {
        if (residentRepository.findAllByHouseId(houseId).size() <= 0) return;
        residentRepository.deleteAllByHouseId(houseId);
    }

    @Override
    public int getNumberOfResidents() {
        return residentRepository.getNumberOfResidents();
    }
}
