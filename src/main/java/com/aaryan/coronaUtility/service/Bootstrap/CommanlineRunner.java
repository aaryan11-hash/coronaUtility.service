package com.aaryan.coronaUtility.service.Bootstrap;

import com.aaryan.coronaUtility.service.Controller.Model.DistrictExcelData.DistrictExcelData;
import com.aaryan.coronaUtility.service.Repository.ExcelSheetRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;



@RequiredArgsConstructor
@Component
public class CommanlineRunner implements CommandLineRunner {

//    private String excelSheetPrefix = "https://api.covid19india.org/csv/latest/raw_data";
//    private String excelSheetPostfix = ".csv";
//    private final ExcelSheetRepository excelSheetRepository;

    @Override
    public void run(String... args) throws Exception {


//        for(int i = 1;i<=16;i++){
//
//            HttpRequest request = HttpRequest.newBuilder()
//                    .uri(URI.create(excelSheetPrefix+i+excelSheetPostfix))
//                    .build();
//
//            HttpResponse<String> response= HttpClient.newHttpClient()
//                    .send(request, HttpResponse.BodyHandlers.ofString());
//
//
//            StringReader exelbody = new StringReader(response.body());
//            Iterable<CSVRecord> records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(exelbody);
//
//            for(CSVRecord record : records){
//                DistrictExcelData data = DistrictExcelData.builder().build();
//                data.setDistrictORCity(record.get("Detected District"));
//                data.setState(record.get("Detected State"));
//                data.setTotalReportedCase(record.get("Num Cases"));
//                data.setReportedDate(record.get("Date Announced"));
//               excelSheetRepository.save(data);
//
//            }
//            System.out.println(i+" turn done");
//        }
    }




}
