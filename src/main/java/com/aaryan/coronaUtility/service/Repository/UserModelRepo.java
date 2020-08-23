package com.aaryan.coronaUtility.service.Repository;

import com.aaryan.coronaUtility.service.Domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserModelRepo extends JpaRepository<UserModel, Integer> {

    @Query("select n from  UserModel n")
    List<UserModel> getAllUserData();
}
