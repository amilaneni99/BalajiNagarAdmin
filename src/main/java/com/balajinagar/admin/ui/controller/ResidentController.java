package com.balajinagar.admin.ui.controller;

import com.balajinagar.admin.service.ResidentService;
import com.balajinagar.admin.shared.dto.ResidentDto;
import com.balajinagar.admin.ui.model.request.ResidentRequestModel;
import com.balajinagar.admin.ui.model.response.ResidentResponseModel;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/residents")
public class ResidentController {

    @Autowired
    ResidentService residentService;

    @RequestMapping(
            value = "/search",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
        )
    public ResidentResponseModel getResidentById(@RequestParam(value = "residentId") String id){
        ResidentResponseModel returnValue;
        ResidentDto residentDto = residentService.getResident(id);
        returnValue = new ModelMapper().map(residentDto, ResidentResponseModel.class);
        return returnValue;
    }

    @RequestMapping(
            value = "/search",
            params = {"sex","houseId","page","limit"},
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public List<ResidentResponseModel> getAllBySex(@RequestParam(value = "sex", defaultValue = "Male") String sex, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "2") int limit){
        List<ResidentDto> residentsList = residentService.getAllBySex(sex, page, limit);
        List<ResidentResponseModel> returnValue = new ArrayList<>();

        for (ResidentDto residentDto : residentsList){
            ResidentResponseModel residentResponseModel = new ModelMapper().map(residentDto, ResidentResponseModel.class);
            returnValue.add(residentResponseModel);
        }

        return returnValue;
    }


    @RequestMapping(
            value = "/all",
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public List<ResidentResponseModel> getAllResidents(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "2") int limit){
        List<ResidentDto> residentsList = residentService.getAllResidents(page, limit);
        List<ResidentResponseModel> returnValue = new ArrayList<>();

        for (ResidentDto residentDto : residentsList){
            ResidentResponseModel residentResponseModel = new ModelMapper().map(residentDto, ResidentResponseModel.class);
            returnValue.add(residentResponseModel);
        }

        return returnValue;
    }

    @RequestMapping(
            value = "/search",
            params = {"houseId"},
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
    )
    public List<ResidentResponseModel> getAllByHouseId(@RequestParam(value = "houseId") String houseId){
        List<ResidentDto> residentsList = residentService.getResidents(houseId);
        List<ResidentResponseModel> returnValue = new ArrayList<>();

        for (ResidentDto residentDto : residentsList){
            ResidentResponseModel residentResponseModel = new ModelMapper().map(residentDto, ResidentResponseModel.class);
            returnValue.add(residentResponseModel);
        }

        return returnValue;
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE},
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
        )
    public ResidentResponseModel updateResident(@PathVariable String id, @RequestBody ResidentRequestModel residentRequestModel){
        ModelMapper modelMapper = new ModelMapper();
        ResidentDto residentDto = modelMapper.map(residentRequestModel, ResidentDto.class);

        ResidentDto updatedResident = residentService.updateResident(id, residentDto);
        ResidentResponseModel returnVal = modelMapper.map(updatedResident, ResidentResponseModel.class);
        return returnVal;
    }

    @DeleteMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_XML_VALUE, MediaType.APPLICATION_JSON_VALUE}
        )
    public String deleteResident(@PathVariable String id){
        return "Resident Deleted";
    }
}
