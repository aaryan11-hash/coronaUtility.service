package com.aaryan.coronaUtility.service.Controller.Model.weatherApiModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@JsonIgnoreProperties(ignoreUnknown = true)
public class ZIP {
   @JsonProperty(value = "weather")
    private List<Weather> weather;

   @JsonProperty("main")
    private main main;

   @JsonProperty("timezone")
   private Double timezone;

   @JsonProperty("id")
   private Integer id;

   @JsonProperty("name")
   private String name;

   @JsonProperty("cod")
   private Integer cod;

}
