package com.aaryan.coronaUtility.service.Service;


import com.aaryan.coronaUtility.service.Controller.Model.DistrictExcelData.DistrictExcelData;
import com.aaryan.coronaUtility.service.Controller.Model.IndiaStateCasesModel.DistrictData;
import com.aaryan.coronaUtility.service.Controller.Model.IndiaStateCasesModel.IndianStates;
import com.aaryan.coronaUtility.service.Controller.Model.LocationStats;
import com.aaryan.coronaUtility.service.Controller.Model.UserModelDto;
import com.aaryan.coronaUtility.service.Controller.Model.UserProcessModelDto.UserModelStatsDto;
import com.aaryan.coronaUtility.service.Controller.Model.UserProcessModelDto.UserModelStatsDto2;
import com.aaryan.coronaUtility.service.Controller.Model.WORLDCases;
import com.aaryan.coronaUtility.service.Controller.Model.weatherApiModel.ZIP;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;


@Service
@Component
@Getter
@Slf4j
public class CoronaVirusDataService {

    private static String VIRUS_DATA_URL="https://raw.githubusercontent.com/CSSEGISandData/COVID-19/master/csse_covid_19_data/csse_covid_19_time_series/time_series_covid19_confirmed_global.csv";
    private static final String WEATHER_API_KEY="2f9b645d1073cba70dcdc5be0644d341";
    private static final String INDIA_STATEWISE_DATA = "https://api.covidindiatracker.com/state_data.json";
    private static final String INDIA_STATE_DISTRICTWISE_DATA="https://api.covid19india.org/csv/latest/raw_data20.csv";
    private List<LocationStats> allStats=new ArrayList<>();
    private List<WORLDCases> APIallStats = new ArrayList<>();
    private List<DistrictExcelData> districtExcelData =new ArrayList<>();
    private List<IndianStates> indianStates = new ArrayList<>();
    private List<DistrictExcelData> yesterdayDistrictCovidCount = new ArrayList<>();
    private List<DistrictExcelData> totalDataINExcel = new LinkedList<>();
    private ZIP cityCondition=ZIP.builder().build();

    private RestTemplate restTemplate;
    private Logger logger = LoggerFactory.getLogger(CoronaVirusDataService.class);


    public CoronaVirusDataService(RestTemplateBuilder restTemplateBuilder){
        this.restTemplate=restTemplateBuilder.build();
    }


    @PostConstruct
    @Scheduled(fixedDelay = 1000000)
    public void fetchVirusData() throws IOException, InterruptedException {

        processCovidDataOnIndia();
        processLatestDistrictCases();
        processLatestWorldCases();
        processLatestworldCasesForAPI();
        processPrevoiusDaysRecordedCases();


    }

    @PostConstruct
    public void fetchIndianDistrictDataUnary() throws IOException,InterruptedException{
        processAllMonthData();
    }

    private void  processAllMonthData() throws IOException, InterruptedException {
        String linkPrefix = "https://api.covid19india.org/csv/latest/raw_data";
        String linkPostfix = ".csv";
        LinkedList<DistrictExcelData> districtExcelData = new LinkedList<>();

        for(int i = 1;i<=20;i++){
            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(linkPrefix+i+linkPostfix))
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
                districtExcelData.add(data);
            }

        }
            this.totalDataINExcel = districtExcelData;
            districtExcelData = null;

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

        //logger.warn("world count data processed");
    }

    public void processLatestworldCasesForAPI() throws IOException,InterruptedException{
        List<WORLDCases> newWorldStats = new ArrayList<>();

        HttpClient client= HttpClient.newHttpClient();
        HttpRequest request= HttpRequest.newBuilder()
                .uri(URI.create(VIRUS_DATA_URL))
                .build();

        HttpResponse<String> httpResponse=client.send(request, HttpResponse.BodyHandlers.ofString());
        StringReader csvBodyReader=new StringReader(httpResponse.body());
        Iterable<CSVRecord> records= CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(csvBodyReader);

        for(CSVRecord record:records){

            WORLDCases worldCases = new WORLDCases();
            worldCases.setState(record.get("Province/State"));
            worldCases.setCountry(record.get("Country/Region"));
            int day1= Integer.parseInt(record.get(record.size()-1));
            int day2= Integer.parseInt(record.get(record.size()-2));
            int day3= Integer.parseInt(record.get(record.size()-3));
            int day4= Integer.parseInt(record.get(record.size()-4));
            int day5= Integer.parseInt(record.get(record.size()-5));
            worldCases.setDay1(day1);
            worldCases.setDay2(day2);
            worldCases.setDay3(day3);
            worldCases.setDay4(day4);
            worldCases.setDay5(day5);

            newWorldStats.add(worldCases);
        }

        this.APIallStats = newWorldStats;
        //logger.warn("world data for api processed");

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

        //logger.warn("district data proccessed");

    }

    private void processPrevoiusDaysRecordedCases() {

        String prevDate,currDate,nextDate;

        int MILLIS_IN_DAY = 1000 * 60 * 60 * 24;
        Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        prevDate = dateFormat.format(date.getTime() - MILLIS_IN_DAY);
        currDate = dateFormat.format(date.getTime());
        nextDate = dateFormat.format(date.getTime() + MILLIS_IN_DAY);

        this.districtExcelData.stream()
                .filter(r->r.getReportedDate().contentEquals(prevDate))
                .forEach(r->this.yesterdayDistrictCovidCount.add(r));

       // logger.warn("previous day data logged");

    }

    public void processCovidDataOnIndia(){

        ResponseEntity<List<IndianStates>> list =this.restTemplate.exchange(INDIA_STATEWISE_DATA, HttpMethod.GET, null, new ParameterizedTypeReference<List<IndianStates>>(){});
        this.indianStates = list.getBody();
        //logger.warn("covid data on india processed");

    }

    public ZIP weatherRestTemplateCall(int zipcode){

        String Open_api="https://api.openweathermap.org/data/2.5/weather?zip="+zipcode+",IN&appid="+WEATHER_API_KEY;
        ResponseEntity<ZIP> responseEntity=restTemplate.getForEntity(Open_api,ZIP.class);
        this.cityCondition=responseEntity.getBody();
        return responseEntity.getBody();
    }



    public UserModelStatsDto processCurrentUserDataRequest(String state,String city){

     AtomicReference<IndianStates> indianStates =new AtomicReference<>();
     AtomicReference<DistrictData> districtData = new AtomicReference<>();
     AtomicReference<DistrictExcelData> districtExcelData =new AtomicReference<>();

        this.indianStates.stream()
               .map(IndianStates::setToUpperCase)
                .filter(r->r.getState().contentEquals(state.toUpperCase()))
                .forEach(r->{
                    indianStates.set(r);
                    r.getDistrictData().stream()
                            .map(DistrictData::setToUpperCase)
                            .filter(i->i.getName().contentEquals(city.toUpperCase()))
                            .forEach(i->{
                                districtData.set(i);
                            });

                    //todo add the user districts tracked case for present time or 1 day before
                    this.yesterdayDistrictCovidCount.stream()
                            .map(DistrictExcelData::setToUpperCase)
                            .filter(t->t.getState().contentEquals(state.toUpperCase()))
                            .filter(t->t.getDistrictORCity().contentEquals(city.toUpperCase()))
                            .forEach(t->districtExcelData.set(t));


                });




            //logger.warn("User Request Proccesed ===> ");




        return UserModelStatsDto.builder()
                .stateActiveCases(indianStates.get().getActive())
                .stateConfirmedCases(indianStates.get().getConfirmed())
                .stateRecoveredCases(indianStates.get().getRecovered())
                .stateDeathCases(indianStates.get().getDeaths())
                .districtConfirmedCases(districtData.get().getConfirmed())
                .todaysCaseCount(Integer.parseInt(districtExcelData.get().getTotalReportedCase()))
                .state(state.toUpperCase())
                .city(city.toUpperCase())
                .build();

    }


    public List<DistrictExcelData> processCustomDateResult(String state, String city, String startDate, String startmonth,int surplus) {
        System.out.println("inside working function");
        List<DistrictExcelData> proccessedList1 = new LinkedList<>();
        List<DistrictExcelData> proccessedList2 = new LinkedList<>();
        System.out.println(this.totalDataINExcel);
        proccessedList1 = this.totalDataINExcel.stream()
                .filter(s->s.getState().contentEquals(state))
                .filter(s->s.getDistrictORCity().contentEquals(city))
                .collect(Collectors.toList());
        System.out.println("proccessed list 1 done");
        System.out.println(proccessedList1);

        String SDate = startDate+"/"+startmonth+"/2020";
        //String EDate = endDate+"/"+endmonth+"/2020";

        for(DistrictExcelData data : proccessedList1){
            if(data.getReportedDate().contentEquals(SDate)){
                proccessedList2.add(data);
                int index = proccessedList1.indexOf(data);
                for(int i = 1;i<=surplus;i++){
                    proccessedList2.add(proccessedList1.get(index));
                    index++;
                }
            }

            }
        System.out.println("proccessedList 2 done");
        System.out.println(proccessedList2);


        return proccessedList2;
        }


    }



















