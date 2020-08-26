package com.aaryan.coronaUtility.service.Controller.Model.IndiaStateCasesModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class DistrictData {

    private String id;

    private String name;

    private Integer confirmed;

    private Integer recovered;

    private Integer deaths;

}
