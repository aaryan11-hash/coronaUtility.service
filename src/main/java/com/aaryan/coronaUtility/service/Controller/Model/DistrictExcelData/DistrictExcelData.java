package com.aaryan.coronaUtility.service.Controller.Model.DistrictExcelData;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Data
@Builder
public class DistrictExcelData {

    private String districtORCity;
    private String state;
    private String totalReportedCase;
    private String reportedDate;


}
