package com.aaryan.coronaUtility.service.Controller;

import com.aaryan.coronaUtility.service.Controller.Model.DistrictExcelData.DistrictExcelData;
import com.aaryan.coronaUtility.service.Controller.Model.IndiaStateCasesModel.IndianStates;
import com.aaryan.coronaUtility.service.Controller.Model.LocationStats;
import com.aaryan.coronaUtility.service.Controller.Model.LoginUser;
import com.aaryan.coronaUtility.service.Controller.Model.UserModelDto;
import com.aaryan.coronaUtility.service.Controller.Model.UserProcessModelDto.UserModelStatsDto;
import com.aaryan.coronaUtility.service.Controller.Model.UserProcessModelDto.UserModelStatsDto2;
import com.aaryan.coronaUtility.service.Controller.Model.WORLDCases;
import com.aaryan.coronaUtility.service.Controller.Model.weatherApiModel.ZIP;
import com.aaryan.coronaUtility.service.Domain.UserModel;
import com.aaryan.coronaUtility.service.Service.CoronaVirusDataService;
import com.aaryan.coronaUtility.service.Service.MailingService;
import com.aaryan.coronaUtility.service.Service.userDataJPAImpl;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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

    @GetMapping("/API/Source/casesTable")
    public ResponseEntity<List<WORLDCases>> getWorldCasesForApi(){
        return new ResponseEntity<List<WORLDCases>>(coronaVirusDataService.getAPIallStats(),HttpStatus.OK);
    }

    @GetMapping("/userdata/")
    public ResponseEntity<List<UserModel>> getUserData(){

        //List<UserModel> list = userDataJPA.getAllUsers();

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
        System.out.println(state+" "+city);
        return new ResponseEntity<>(coronaVirusDataService.processCurrentUserDataRequest(state,city),httpHeaders,HttpStatus.CREATED);
    }

    @GetMapping("/user/{state}/{city}/{startDate}/{startmonth}/{surplus}")
    public ResponseEntity<List<DistrictExcelData>> getCustomResult(@PathVariable String state, @PathVariable String city, @PathVariable String startDate, @PathVariable String startmonth, @PathVariable int surplus){

        List<DistrictExcelData> finalList = this.coronaVirusDataService.processCustomDateResult(state,city,startDate,startmonth,surplus);
        if(finalList.size()==0)
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        else
        return new ResponseEntity<List<DistrictExcelData>>(finalList,HttpStatus.OK);
    }

    @PostMapping("/postUser")
    public void postUser(@RequestBody UserModelDto userModelDto) throws InterruptedException{
        int threadCount = Runtime.getRuntime().availableProcessors();
        ExecutorService service = Executors.newCachedThreadPool();

            //this.userDataJPA.saveUserData(userModelDto);
            //this.mailingService.sendMai l(userModelDto.getEmail(), userModelDto.getToken());
            UserModelDto modelDto = userModelDto;
            userDataJPAImpl userDataJPA2 = this.userDataJPA;
            MailingService mailingService = this.mailingService;

            service.execute( new Thread(new Runnable() {
                @Override
                public void run() {
                    System.out.println(modelDto);
                    userDataJPA.saveUserData(modelDto);
                    mailingService.sendMail(modelDto.getEmail(),modelDto.getToken());
                }
            }));




    }

    @GetMapping("/login/user")
    public ResponseEntity<List<UserModel>> loginCheck() {
        List<UserModel> list = this.userDataJPA.getAllUsers();

        return new ResponseEntity<>(this.userDataJPA.getAllUsers(), HttpStatus.OK);

    }



}
