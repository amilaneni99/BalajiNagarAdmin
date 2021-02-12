package com.balajinagar.admin.ui.controller;

import com.balajinagar.admin.service.HouseService;
import com.balajinagar.admin.service.ResidentService;
import com.balajinagar.admin.shared.dto.HouseDto;
import com.balajinagar.admin.shared.dto.ResidentDto;
import com.balajinagar.admin.ui.model.request.HouseRequestModel;
import com.balajinagar.admin.ui.model.response.HouseResponseModel;
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
@RequestMapping("/houses")
public class HouseController {

    @Autowired
    HouseService houseService;

    @Autowired
    ResidentService residentService;

    @RequestMapping(value = "/nos",
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE}
    )
    public JSONObject getNoOfHouses(){
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("number",houseService.getNumberOfHouses());
        return jsonObject;
    }

    @RequestMapping(value = "/search",
                params = "houseId",
                method = RequestMethod.GET,
                produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
            )
    public HouseResponseModel getHouse(@RequestParam String houseId){
        HouseResponseModel returnValue;

        HouseDto houseDto = houseService.getHouseDetails(houseId);

        returnValue = new ModelMapper().map(houseDto, HouseResponseModel.class);

        return returnValue;
    }

    @RequestMapping(value = "/search",
            params = "houseNo",
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public HouseResponseModel getHouseByNo(@RequestParam String houseNo){
        HouseResponseModel returnValue;

        HouseDto houseDto = houseService.getHouseDetailsByHouseNo(houseNo);

        returnValue = new ModelMapper().map(houseDto, HouseResponseModel.class);

        return returnValue;
    }

    @RequestMapping(value = "/search",
            params = {"familyHeadName","page","limit"},
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<HouseResponseModel> getHouseByHeadName(@RequestParam String familyHeadName, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "2") int limit){
        List<HouseDto> houses = houseService.getHouseByHeadName(familyHeadName, page, limit);
        List<HouseResponseModel> returnValue = new ArrayList<>();

        for (HouseDto houseDto : houses){
            HouseResponseModel houseResponseModel = new ModelMapper().map(houseDto, HouseResponseModel.class);
            returnValue.add(houseResponseModel);
        }

        return returnValue;
    }

    @RequestMapping(value = "/search",
            params = {"residentName","page","limit"},
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<HouseResponseModel> getHouseByResidentName(@RequestParam String residentName, @RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "2") int limit){
        List<HouseDto> houses = houseService.getHouseByResidentName(residentName, page, limit);
        List<HouseResponseModel> returnValue = new ArrayList<>();

        for (HouseDto houseDto : houses){
            HouseResponseModel houseResponseModel = new ModelMapper().map(houseDto, HouseResponseModel.class);
            returnValue.add(houseResponseModel);
        }

        return returnValue;
    }

    @RequestMapping(value = "/search",
            method = RequestMethod.GET,
            produces = { MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<HouseResponseModel> getAllHouses(@RequestParam(value = "page", defaultValue = "0") int page, @RequestParam(value = "limit", defaultValue = "5") int limit){
        List<HouseDto> houses = houseService.getHouses(page, limit);
        List<HouseResponseModel> returnValue = new ArrayList<>();

        for (HouseDto houseDto : houses){
            HouseResponseModel houseResponseModel = new ModelMapper().map(houseDto, HouseResponseModel.class);
            returnValue.add(houseResponseModel);
        }

        return returnValue;
    }

    @PutMapping(path = "/{id}",
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
        )
    public HouseResponseModel updateHouse(@PathVariable String id, @RequestBody HouseRequestModel houseDetails){
        ModelMapper modelMapper = new ModelMapper();
        HouseDto houseDto = modelMapper.map(houseDetails, HouseDto.class);

        HouseDto updatedHouse = houseService.updateHouse(id, houseDto);
        HouseResponseModel returnVal = modelMapper.map(updatedHouse, HouseResponseModel.class);
        return returnVal;
    }

    @DeleteMapping(path = "/{id}/deleteAllResidents",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public OperationStatusModel deleteAllResidents(@PathVariable String id){
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationStatus("DELETE");
        houseService.deleteAllResidents(id);
        operationStatusModel.setOperationMessage("SUCCESS");
        return operationStatusModel;
    }

    @PostMapping(
            consumes = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE},
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
        )
    public HouseResponseModel createHouse(@RequestBody HouseRequestModel houseRequestModel){
       ModelMapper modelMapper = new ModelMapper();

       HouseDto houseDto = modelMapper.map(houseRequestModel, HouseDto.class);

       HouseDto createdHouse = houseService.createHouse(houseDto);
       HouseResponseModel returnHouse = modelMapper.map(createdHouse, HouseResponseModel.class);

       return returnHouse;
    }

    @DeleteMapping(path = "/{id}",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
        )
    public OperationStatusModel deleteHouse(@PathVariable String id){
        OperationStatusModel operationStatusModel = new OperationStatusModel();
        operationStatusModel.setOperationStatus("DELETE");
        houseService.deleteHouse(id);
        residentService.deleteAllResidentsByHouse(id);
        operationStatusModel.setOperationMessage("SUCCESS");
        return operationStatusModel;
    }

    @GetMapping(path = "/{houseNo}/residents",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
        )
    public List<ResidentResponseModel> getHouseResidents(@PathVariable String houseNo){
        List<ResidentDto> residents = houseService.getAllResidents(houseNo);
        List<ResidentResponseModel> returnValue = new ArrayList<>();

        for(ResidentDto residentDto: residents) {
            returnValue.add(new ModelMapper().map(residentDto, ResidentResponseModel.class));
        }
        return returnValue;
    }

    @GetMapping(path = "/streets",
            produces = {MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE}
    )
    public List<String> getStreetNames() {
        return houseService.getStreetNames();
    }

}
