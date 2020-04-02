package com.balajinagar.admin.service.impl;

import com.balajinagar.admin.exceptions.HouseServiceException;
import com.balajinagar.admin.io.entity.HouseEntity;
import com.balajinagar.admin.io.entity.ResidentEntity;
import com.balajinagar.admin.io.repositories.HouseRepository;
import com.balajinagar.admin.io.repositories.ResidentRepository;
import com.balajinagar.admin.service.ResidentService;
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


    @Override
    public List<ResidentDto> getResidents(String houseId) {
        HouseEntity houseEntity = houseRepository.findHouseByHouseId(houseId);

        List<ResidentEntity> residentEntities = residentRepository.findAllByHouseDetails(houseEntity);

        List<ResidentDto> returnValue = new ArrayList<>();

        for (ResidentEntity residentEntity : residentEntities){
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
    public List<ResidentDto> getResidentByFirstName(String firstName) {
        List<ResidentEntity> residents = residentRepository.findByFirstName(firstName);
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
}
