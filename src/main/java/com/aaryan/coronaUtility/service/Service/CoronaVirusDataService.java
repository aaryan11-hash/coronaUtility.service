package com.aaryan.coronaUtility.service.Service;


import com.aaryan.coronaUtility.service.Controller.Model.LocationStats;
import com.aaryan.coronaUtility.service.Controller.Model.weatherApiModel.ZIP;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
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
public class CoronaVirusDataService {


    private static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";

    public static final String WEATHER_API_KEY="2f9b645d1073cba70dcdc5be0644d341";

    private List<LocationStats> allStats=new ArrayList<>();

    private ZIP cityCondition=ZIP.builder().build();

    private RestTemplate restTemplate;

    public CoronaVirusDataService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate=restTemplateBuilder.build();
    }


    @PostConstruct
    @Scheduled(cron = "* * * 1 * *")
    public void fetchVirusData() throws IOException, InterruptedException {
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

        allStats=newStats;
        //System.out.println(newStats);
    }


    public List<LocationStats> getAllStats() {
        return allStats;
    }

    public void weatherStatusCall() throws IOException, InterruptedException {
        String OPEN_WEATHER_API="https://api.openweathermap.org/data/2.5/weather?q=Pune&appid=2f9b645d1073cba70dcdc5be0644d341";
        String Open_api="https://api.openweathermap.org/data/2.5/weather?zip=411060,IN&appid=2f9b645d1073cba70dcdc5be0644d341";
        HttpClient httpClient= HttpClient.newHttpClient();
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(Open_api))
                .build();

        HttpResponse<String> httpResponse=httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        //System.out.println(httpResponse.body());
    }


    public ZIP weatherRestTemplateCall(int zipcode){
        String Open_api="https://api.openweathermap.org/data/2.5/weather?zip="+zipcode+",IN&appid="+WEATHER_API_KEY;

        ResponseEntity<ZIP> responseEntity=restTemplate.getForEntity(Open_api,ZIP.class);

        this.cityCondition=responseEntity.getBody();

        //System.out.println(this.cityCondition);
        return responseEntity.getBody();
    }
}


















