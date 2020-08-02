package com.aaryan.coronaUtility.service.Repository;

import com.aaryan.coronaUtility.service.Domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface covidDatabaseRepo extends JpaRepository<UserModel,Integer> {

}
