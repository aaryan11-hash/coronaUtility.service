package com.aaryan.coronaUtility.service.Controller.Model.UserProcessModelDto;


import lombok.*;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModelStatsDto {

    private String city;
    private String state;
    private Integer stateConfirmedCases;
    private Integer stateActiveCases;
    private Integer stateRecoveredCases;
    private Integer stateDeathCases;
    private Integer districtConfirmedCases;
    private Integer todaysCaseCount;



}
