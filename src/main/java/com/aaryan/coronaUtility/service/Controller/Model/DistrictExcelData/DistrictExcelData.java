package com.aaryan.coronaUtility.service.Controller.Model.DistrictExcelData;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Data
@Builder
@Entity
public class DistrictExcelData {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String districtORCity;
    private String state;
    private String totalReportedCase;
    private String reportedDate;

    public DistrictExcelData setToUpperCase() {
        this.setState(this.getState().toUpperCase());
        this.setDistrictORCity(this.getDistrictORCity().toUpperCase());
        return this;
    }
}
