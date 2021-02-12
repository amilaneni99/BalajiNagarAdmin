package com.balajinagar.admin.ui.controller;

import com.balajinagar.admin.service.HouseService;
import com.balajinagar.admin.service.ResidentService;
import com.balajinagar.admin.shared.dto.ResidentDto;
import com.balajinagar.admin.ui.model.request.ResidentRequestModel;
import com.balajinagar.admin.ui.model.response.OperationStatusModel;
import com.balajinagar.admin.ui.model.response.ResidentResponseModel;
import net.minidev.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/residents")
public class ResidentController {

    @Autowired
    ResidentService residentService;

    @Autowired
    HouseService houseService;

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public ResidentResponseModel addResident(@RequestBody ResidentRequestModel residentRequestModel) {
        System.out.println("From Request"+residentRequestModel.getHouseNo());
        String houseId = houseService.findHouseIdByNo(residentRequestModel.getHouseNo());
        System.out.println("House Id"+houseId);
        ResidentDto residentDto = new ModelMapper().map(residentRequestModel, ResidentDto.class);
        residentDto.setHouseId(houseId);
        System.out.println(residentDto.getHouseId());
        ResidentResponseModel returnValue = new ModelMapper().map(residentService.addResident(residentDto), ResidentResponseModel.class);
        returnValue.setHouseNo(residentRequestModel.getHouseNo());
        return returnValue;
    }

    @RequestMapping(
            value = "/nos",
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public JSONObject getNumberOfResidents(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("number",residentService.getNumberOfResidents());
        return jsonObject;
    }

    @RequestMapping(
            value = "/search",
            params = {"residentId"},
            method = RequestMethod.GET,
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
        )
    public ResidentResponseModel getResidentById(@RequestParam(value = "residentId") String id){
        ResidentResponseModel returnValue;
        ResidentDto residentDto = residentService.getResident(id);
        returnValue = new ModelMapper().map(residentDto, ResidentResponseModel.class);
        returnValue.setHouseNo(houseService.findHouseNoById(residentDto.getHouseId()));
        return returnValue;
    }

    @RequestMapping(
            value = "/search",
            params = {"sex","page","limit"},
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<ResidentResponseModel> getAllBySex(@RequestParam(value = "sex", defaultValue = "Male") String sex, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "2") int limit){
        List<ResidentDto> residentsList = residentService.getAllBySex(sex, page, limit);
        List<ResidentResponseModel> returnValue = new ArrayList<>();

        for (ResidentDto residentDto : residentsList){
            ResidentResponseModel residentResponseModel = new ModelMapper().map(residentDto, ResidentResponseModel.class);
            residentResponseModel.setHouseNo(houseService.findHouseNoById(residentDto.getHouseId()));
            returnValue.add(residentResponseModel);
        }

        return returnValue;
    }

    @RequestMapping(
            value = "/search",
            params = {"houseNo"},
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<ResidentResponseModel> getAllByHouseNo(@RequestParam(value = "houseNo") String houseNo) {
        String houseId = houseService.findHouseIdByNo(houseNo);
        List<ResidentDto> residentsList = residentService.getResidentsByHouseNo(houseNo);
        List<ResidentResponseModel> returnValue = new ArrayList<>();

        for (ResidentDto residentDto : residentsList){
            ResidentResponseModel residentResponseModel = new ModelMapper().map(residentDto, ResidentResponseModel.class);
            residentResponseModel.setHouseNo(houseService.findHouseNoById(residentDto.getHouseId()));
            returnValue.add(residentResponseModel);
        }

        return returnValue;
    }

    @RequestMapping(
            value = "/all",
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<ResidentResponseModel> getAllResidents(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "2") int limit){
        List<ResidentDto> residentsList = residentService.getAllResidents(page, limit);
        List<ResidentResponseModel> returnValue = new ArrayList<>();

        for (ResidentDto residentDto : residentsList){
            ResidentResponseModel residentResponseModel = new ModelMapper().map(residentDto, ResidentResponseModel.class);
            residentResponseModel.setHouseNo(houseService.findHouseNoById(residentDto.getHouseId()));
            returnValue.add(residentResponseModel);
        }

        return returnValue;
    }

    @RequestMapping(
            value = "/search",
            params = {"houseId"},
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<ResidentResponseModel> getAllByHouseId(@RequestParam(value = "houseId") String houseId){
        List<ResidentDto> residentsList = residentService.getResidentsByHouseId(houseId);
        List<ResidentResponseModel> returnValue = new ArrayList<>();

        for (ResidentDto residentDto : residentsList){
            ResidentResponseModel residentResponseModel = new ModelMapper().map(residentDto, ResidentResponseModel.class);
            residentResponseModel.setHouseNo(houseService.findHouseNoById(residentDto.getHouseId()));
            returnValue.add(residentResponseModel);
        }

        return returnValue;
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
        )
    public ResidentResponseModel updateResident(@PathVariable String id, @RequestBody ResidentRequestModel residentRequestModel){
        ModelMapper modelMapper = new ModelMapper();
        ResidentDto residentDto = modelMapper.map(residentRequestModel, ResidentDto.class);

        ResidentDto updatedResident = residentService.updateResident(id, residentDto);
        ResidentResponseModel returnVal = modelMapper.map(updatedResident, ResidentResponseModel.class);
        returnVal.setHouseNo(residentRequestModel.getHouseNo());
        return returnVal;
    }

    @DeleteMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
        )
    public OperationStatusModel deleteResident(@PathVariable String id){
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationStatus("DELETE");
        residentService.deleteResident(id);
        operationStatusModel.setOperationMessage("SUCCESS");
        return operationStatusModel;
    }
}
