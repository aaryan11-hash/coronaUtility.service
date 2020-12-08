package com.aaryan.coronaUtility.service.Controller.Model.UserProcessModelDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DistinctDateCases {

    private String date;
    private Integer casesDetected;
}
