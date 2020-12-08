package com.aaryan.coronaUtility.service.Controller.Model.UserProcessModelDto;

import lombok.*;

import java.util.List;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserModelStatsDto2 {
    private String city;
    private String state;
    private Integer stateConfirmedCases;
    private Integer stateActiveCases;
    private Integer stateRecoveredCases;
    private Integer stateDeathCases;
    private List<DistinctDateCases> distinctDateCases;
}
