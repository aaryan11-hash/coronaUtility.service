package com.aaryan.coronaUtility.service.Controller.Model.weatherApiModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class main {
    @JsonProperty("temp")
    private int temp;

    @JsonProperty("feels_like")
    private int fees_like;

    @JsonProperty("temp_min")
    private int temp_min;

    @JsonProperty("temp_max")
    private int temp_max;

    @JsonProperty("pressure")
    private int pressure;

    @JsonProperty("humidity")
    private int humidity;
}
