package com.aaryan.coronaUtility.service.Controller.Model.IndiaStateCasesModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class IndianStates {

    @JsonProperty("id")
    private String id;

    @JsonProperty("state")
    private String state;

    @JsonProperty("active")
    private Integer active;

    @JsonProperty("confirmed")
    private Integer confirmed;

    @JsonProperty("recovered")
    private Integer recovered;

    @JsonProperty("deaths")
    private Integer deaths;

    @JsonProperty("districtData")
    private List<DistrictData> districtData;

    public IndianStates setToUpperCase(){
        this.setState(this.getState().toUpperCase());
        return  this;
    }

}
