package com.aaryan.coronaUtility.service.Controller.Model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class LoginUser {

    @NotNull(message = "entry cannot be null")
    @NotBlank(message = "entry cannot be blank")
    private String firstname;

    @NotNull(message = "entry cannot be null")
    @NotBlank(message = "entry cannot be blank")
    private String password;

}
