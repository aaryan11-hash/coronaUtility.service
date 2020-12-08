package com.aaryan.coronaUtility.service.Controller.Model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
public class UserModelDto implements Serializable {

    static final long  serialNumber=-1214124L;

    @JsonProperty("firstname")
    @NotBlank
    private String firstName;

    @JsonProperty("lastname")
    @NotBlank
    private String lastName;

    @JsonProperty("password")
    private String password;

    @JsonProperty("email")
    @NotBlank
    @Pattern(regexp = "^[a-zA-Z0-9.]{5,40}@(gmail.com|yahoo.com|hotmail.com|outlook.com|sitpune.edu.in)$",message = "please make sure that you are entering only gmail and valid credentials")
    private String email;

    @JsonProperty("phonenumber")
    @Pattern(regexp = "^[0-9]{10}",message ="phone number cannot contain characters!!")
    private String phoneNumber;

    @JsonProperty("pincode")
    @Pattern(regexp = "^[0-9]{6}",message = "pincodes are built of exactly 6 characters")
    private String pincode;

    @JsonProperty("uuid")
    private String uuid;

    @JsonProperty("state")
    @NotNull
    private String state;

    @JsonProperty("city")
    @NotNull
    private String city;

    @NotNull
    private String token;
}
