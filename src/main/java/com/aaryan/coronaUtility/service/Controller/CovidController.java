package com.aaryan.coronaUtility.service.Controller;

import com.aaryan.coronaUtility.service.Controller.Model.LocationStats;
import com.aaryan.coronaUtility.service.Controller.Model.weatherApiModel.ZIP;
import com.aaryan.coronaUtility.service.Domain.UserModel;
import com.aaryan.coronaUtility.service.Service.CoronaVirusDataService;
import com.aaryan.coronaUtility.service.Service.MailingService;
import com.aaryan.coronaUtility.service.Service.userDataJPAImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
        return new ResponseEntity<>(HttpStatus.OK);
    }





}
