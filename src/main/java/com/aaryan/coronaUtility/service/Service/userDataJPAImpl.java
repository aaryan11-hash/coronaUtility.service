package com.aaryan.coronaUtility.service.Service;

import com.aaryan.coronaUtility.service.Domain.UserModel;
import com.aaryan.coronaUtility.service.Repository.UserModelRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class userDataJPAImpl {

    @Autowired
    private UserModelRepo userModelRepo;

    @Transactional
    public void saveUserData(UserModel userModel){

        userModelRepo.save(userModel);

    }

}
