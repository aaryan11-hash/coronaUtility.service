package com.aaryan.coronaUtility.service.Service;

import com.aaryan.coronaUtility.service.Controller.Mappers.ModelMapper;
import com.aaryan.coronaUtility.service.Controller.Model.UserModelDto;
import com.aaryan.coronaUtility.service.Domain.UserModel;
import com.aaryan.coronaUtility.service.Repository.UserModelRepo;
import lombok.RequiredArgsConstructor;
import org.hibernate.Session;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@RequiredArgsConstructor
public class userDataJPAImpl {


    @Autowired
    private EntityManager entityManager;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private UserModelRepo userModelRepo;

    @Transactional
    public void saveUserData(UserModelDto userModelDto){

//        Session session = entityManager.unwrap(Session.class);
//        session.beginTransaction();
//        session.save(modelMapper.userModelDtoconvertuserModel(userModelDto));
//        session.close();

        userModelRepo.save(modelMapper.userModelDtoconvertuserModel(userModelDto));

    }

    @Transactional
    public List<UserModel> getAllUsers(){

        Session session = entityManager.unwrap(Session.class);
       List<UserModel> list = session.createQuery("from UserModel").getResultList();

       return list;
    }





}
