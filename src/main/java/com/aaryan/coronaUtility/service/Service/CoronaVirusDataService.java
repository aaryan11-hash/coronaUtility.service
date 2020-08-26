package com.aaryan.coronaUtility.service.Service;


import com.aaryan.coronaUtility.service.Controller.Model.DistrictExcelData.DistrictExcelData;
import com.aaryan.coronaUtility.service.Controller.Model.IndiaStateCasesModel.DistrictData;
import com.aaryan.coronaUtility.service.Controller.Model.IndiaStateCasesModel.IndianStates;
import com.aaryan.coronaUtility.service.Controller.Model.LocationStats;
import com.aaryan.coronaUtility.service.Controller.Model.UserProcessModelDto.UserModelStatsDto;
import com.aaryan.coronaUtility.service.Controller.Model.weatherApiModel.ZIP;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.persistence.criteria.CriteriaBuilder;
import java.io.IOException;
import java.io.StringReader;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;


@Service
@Component
@Getter
public class CoronaVirusDataService {

    private static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private  static final String WEATHER_API_KEY="2f9b645d1073cba70dcdc5be0644d341";
    private static final String INDIA_STATEWISE_DATA = "https://api.covidindiatracker.com/state_data.json";
    private static final String INDIA_STATE_DISTRICTWISE_DATA="https://api.covid19india.org/csv/latest/raw_data14.csv";

    private List<LocationStats> allStats=new ArrayList<>();
    private List<DistrictExcelData> districtExcelData =new ArrayList<>();
    private List<IndianStates> indianStates = new ArrayList<>();
    private ZIP cityCondition=ZIP.builder().build();

    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(CoronaVirusDataService.class);


    public CoronaVirusDataService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate=restTemplateBuilder.build();
    }


    @PostConstruct
    @Scheduled(cron = "* * * 1 * *")
    public void fetchVirusData() throws IOException, InterruptedException {

        processCovidDataOnIndia();
        processLatestDistrictCases();
        processLatestWorldCases();

    }

    public void processLatestWorldCases() throws IOException, InterruptedException {
        List<LocationStats> newStats=new ArrayList<>();

        HttpClient client= HttpClient.newHttpClient();
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        HttpResponse<String> httpResponse=client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader=new StringReader(httpResponse.body());
        Iterable<CSVRecord> records= CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);



        for(CSVRecord record:records){
            LocationStats locationStats=new LocationStats();
            locationStats.setState(record.get("Province/State"));
            locationStats.setCountry(record.get("Country/Region"));
            int latestcases= Integer.parseInt(record.get(record.size()-1));
            int prevDayCase= Integer.parseInt(record.get(record.size()-2));
            locationStats.setLatestTotalCases(latestcases);
            locationStats.setDiffFromPreviousDay(latestcases-prevDayCase);

            newStats.add(locationStats);
        }

        this.allStats=newStats;
    }

    public void processLatestDistrictCases() throws IOException, InterruptedException {

        List<DistrictExcelData> tempDistrictList = new ArrayList<>();

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                                .uri(URI.create(INDIA_STATE_DISTRICTWISE_DATA))
                                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        StringReader excelBody = new StringReader(response.body());
        Iterable<CSVRecord> records =CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(excelBody);

        for(CSVRecord record : records){
            DistrictExcelData data = DistrictExcelData.builder().build();
            data.setDistrictORCity(record.get("Detected District"));
            data.setState(record.get("Detected State"));
            data.setTotalReportedCase(record.get("Num Cases"));
            data.setReportedDate(record.get("Date Announced"));
            tempDistrictList.add(data);
        }

        this.districtExcelData = tempDistrictList;

    }

    public void processCovidDataOnIndia(){

        ResponseEntity<List<IndianStates>> list =this.restTemplate.exchange(INDIA_STATEWISE_DATA, HttpMethod.GET, null, new ParameterizedTypeReference<List<IndianStates>>(){});
        this.indianStates = list.getBody();

    }

    public ZIP weatherRestTemplateCall(int zipcode){

        String Open_api="https://api.openweathermap.org/data/2.5/weather?zip="+zipcode+",IN&appid="+WEATHER_API_KEY;
        ResponseEntity<ZIP> responseEntity=restTemplate.getForEntity(Open_api,ZIP.class);
        this.cityCondition=responseEntity.getBody();
        return responseEntity.getBody();
    }



    public UserModelStatsDto processCurrentUserDataRequest(String state,String city){

        UserModelStatsDto userModelStatsDto = UserModelStatsDto.builder().build();

        this.indianStates.stream()
                .filter(r->r.getState().contentEquals(state))
                .forEach(r->{
                    userModelStatsDto.setState(state);
                    userModelStatsDto.setStateConfirmedCases(r.getConfirmed());
                    userModelStatsDto.setStateActiveCases(r.getActive());
                    userModelStatsDto.setStateDeathCases(r.getDeaths());
                    userModelStatsDto.setStateRecoveredCases(r.getRecovered());
                    r.getDistrictData().stream()
                            .filter(i->i.getName().contentEquals(city))
                            .forEach(j->{
                                userModelStatsDto.setCity(city);
                                userModelStatsDto.setDistrictConfirmedCases(j.getConfirmed());
                            });

                    //todo add the user districts tracked case for present time or 1 day before

                });

            logger.warn("User Request Proccesed ===> "+userModelStatsDto);
        return userModelStatsDto;

    }





    //    public void weatherStatusCall() throws IOException, InterruptedException {
//        String OPEN_WEATHER_API="https://api.openweathermap.org/data/2.5/weather?q=Pune&appid=2f9b645d1073cba70dcdc5be0644d341";
//        String Open_api="https://api.openweathermap.org/data/2.5/weather?zip=411060,IN&appid=2f9b645d1073cba70dcdc5be0644d341";
//        HttpClient httpClient= HttpClient.newHttpClient();
//        HttpRequest request= HttpRequest.newBuilder()
//                .uri(URI.create(Open_api))
//                .build();
//
//        HttpResponse<String> httpResponse=httpClient.send(request, HttpResponse.BodyHandlers.ofString());
//
//    }
}


















