package com.aaryan.coronaUtility.service.Domain;

import lombok.*;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;
import javax.validation.constraints.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
@Entity
public class UserModel {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(updatable = true,nullable = false,columnDefinition = "varchar(30)")
    private String firstName;

    @Column(updatable = true,nullable = false,columnDefinition = "varchar(30)")
    private String lastName;

    @Column(updatable = true,nullable = false,columnDefinition = "varchar(30)")
    private String email;

    @Column(updatable = true,nullable = false,columnDefinition = "varchar(40)")
    private String phoneNumber;

    @Column(updatable = true,nullable = false,columnDefinition = "varchar(30)")
    private String pincode;






}
