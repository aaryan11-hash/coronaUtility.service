package com.aaryan.coronaUtility.service.Controller;

import com.aaryan.coronaUtility.service.Controller.Model.IndiaStateCasesModel.IndianStates;
import com.aaryan.coronaUtility.service.Controller.Model.LocationStats;
import com.aaryan.coronaUtility.service.Controller.Model.LoginUser;
import com.aaryan.coronaUtility.service.Controller.Model.UserModelDto;
import com.aaryan.coronaUtility.service.Controller.Model.UserProcessModelDto.UserModelStatsDto;
import com.aaryan.coronaUtility.service.Controller.Model.weatherApiModel.ZIP;
import com.aaryan.coronaUtility.service.Domain.UserModel;
import com.aaryan.coronaUtility.service.Service.CoronaVirusDataService;
import com.aaryan.coronaUtility.service.Service.MailingService;
import com.aaryan.coronaUtility.service.Service.userDataJPAImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/covid/data")
public class CovidController {

    @Autowired
    private CoronaVirusDataService coronaVirusDataService;

    @Autowired
    private userDataJPAImpl userDataJPA;

    @Autowired
    private MailingService mailingService;


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



    @GetMapping("/user/{state}/{city}")
    public ResponseEntity<UserModelStatsDto> getLiveUserRequest(@PathVariable("state") String state, @PathVariable("city") String city){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("type","application/json");
        return new ResponseEntity<>(coronaVirusDataService.processCurrentUserDataRequest(state,city),httpHeaders,HttpStatus.CREATED);
    }

    @PostMapping("/postUser")
    public void postUser(@RequestBody UserModelDto userModelDto){

        this.userDataJPA.saveUserData(userModelDto);
        this.mailingService.sendMail(userModelDto.getEmail(),userModelDto.getToken());

    }

    @GetMapping("/login/user")
    public ResponseEntity<List<UserModel>> loginCheck() {
        List<UserModel> list = this.userDataJPA.getAllUsers();
        System.out.println(list);
        return new ResponseEntity<>(this.userDataJPA.getAllUsers(), HttpStatus.OK);

    }



}
