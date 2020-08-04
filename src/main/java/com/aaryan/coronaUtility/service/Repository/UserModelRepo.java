package com.aaryan.coronaUtility.service.Repository;

import com.aaryan.coronaUtility.service.Domain.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserModelRepo extends JpaRepository<UserModel, Integer> {


}
