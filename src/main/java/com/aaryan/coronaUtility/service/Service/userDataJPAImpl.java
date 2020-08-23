package com.aaryan.coronaUtility.service.Service;

import com.aaryan.coronaUtility.service.Domain.UserModel;
import com.aaryan.coronaUtility.service.Repository.UserModelRepo;
import lombok.RequiredArgsConstructor;
import org.apache.catalina.User;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class userDataJPAImpl {

    @Autowired
    private UserModelRepo userModelRepo;

    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void saveUserData(UserModel userModel){

        userModelRepo.save(userModel);

    }

    @Transactional
    public List<UserModel> getAllUsers(){

        Session session = entityManager.unwrap(Session.class);
       List<UserModel> list = session.createQuery("from UserModel").getResultList();

       return list;
    }

    @Transactional
    public List<UserModel> getAllUserJPA(){

        return new ArrayList<>(userModelRepo.findAll());
    }


}
