package com.aaryan.coronaUtility.service.Controller.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class WORLDCases {

    private String state;
    private String country;
    private int day1;
    private int day2;
    private int day3;
    private int day4;
    private int day5;

}
