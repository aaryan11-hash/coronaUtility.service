package com.aaryan.coronaUtility.service.Controller;

import com.aaryan.coronaUtility.service.Controller.Model.DistrictExcelData.DistrictExcelData;
import com.aaryan.coronaUtility.service.Controller.Model.IndiaStateCasesModel.IndianStates;
import com.aaryan.coronaUtility.service.Controller.Model.LocationStats;
import com.aaryan.coronaUtility.service.Controller.Model.UserProcessModelDto.UserModelStatsDto;
import com.aaryan.coronaUtility.service.Controller.Model.weatherApiModel.ZIP;
import com.aaryan.coronaUtility.service.Domain.UserModel;
import com.aaryan.coronaUtility.service.Service.CoronaVirusDataService;
import com.aaryan.coronaUtility.service.Service.userDataJPAImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/covid/data")
public class CovidController {

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;

    @Autowired
    private userDataJPAImpl userDataJPA;



    @GetMapping("/casesTable")
    public ResponseEntity<List<LocationStats>> getCurrentCases(){

        return new ResponseEntity<>(coronaVirusDataService.getAllStats(), HttpStatus.OK);

    }

    @GetMapping("/userdata/")
    public ResponseEntity<List<UserModel>> getUserData(){

        //List<UserModel> list = userDataJPA.getAllUsers();
        System.out.println(userDataJPA.getAllUsers());
        return new ResponseEntity<>(userDataJPA.getAllUsers(),HttpStatus.OK);
    }

    @GetMapping("/getIndianStateData")
    public ResponseEntity<List<IndianStates>> getIndianStatesData(){

        return new ResponseEntity<>(coronaVirusDataService.getIndianStates(),HttpStatus.OK);
    }

    @GetMapping("/weather")
    public ResponseEntity<ZIP> getZIP(){

        return new ResponseEntity<>(coronaVirusDataService.weatherRestTemplateCall(411060),HttpStatus.OK);
    }

    @GetMapping("/mock")
    public ResponseEntity<List<IndianStates>> mock() throws IOException, InterruptedException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

        List<DistrictExcelData> MahrashtraCases = new ArrayList<>();

        coronaVirusDataService.getDistrictExcelData()
                .stream()
                .filter(data->data.getState().contentEquals("Maharashtra"))
                .filter(data->data.getReportedDate().contentEquals("25/08/2020"))
                .forEach(r->{MahrashtraCases.add(r);
                            System.out.println(r);
                });

        return new ResponseEntity( coronaVirusDataService.getIndianStates(),HttpStatus.OK);
    }

    @GetMapping("/user/{state}/{city}")
    public ResponseEntity<UserModelStatsDto> getLiveUserRequest(@PathVariable("state") String state, @PathVariable("city") String city){

        return new ResponseEntity<>(coronaVirusDataService.processCurrentUserDataRequest(state,city),HttpStatus.CREATED);
    }


}
