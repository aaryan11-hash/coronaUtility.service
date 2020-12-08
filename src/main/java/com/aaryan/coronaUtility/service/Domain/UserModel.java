package com.aaryan.coronaUtility.service.Domain;

import lombok.*;

import javax.persistence.*;

@RequiredArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Data
@Builder
@ToString
@Entity
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(updatable = true,nullable = false,columnDefinition = "varchar(30)")
    private String firstName;

    @Column(updatable = true,nullable = false,columnDefinition = "varchar(30)")
    private String lastName;

    @Column(updatable = true,nullable = false,columnDefinition = "varchar(60)")
    private String password;

    @Column(updatable = true,nullable = false,columnDefinition = "varchar(60)")
    private String email;

    @Column(updatable = true,nullable = false,columnDefinition = "varchar(40)")
    private String phoneNumber;


    private String uuid;

    @Column(updatable = true,nullable = false,columnDefinition = "varchar(30)")
    private String pincode;

    @Column(updatable = true,nullable = false,columnDefinition = "varchar(30)")
    private String state;

    @Column(updatable = true,nullable = false,columnDefinition = "varchar(30)")
    private String city;







}
