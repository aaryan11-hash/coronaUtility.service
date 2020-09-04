package com.aaryan.coronaUtility.service.Controller.Mappers;


import com.aaryan.coronaUtility.service.Controller.Model.UserModelDto;
import com.aaryan.coronaUtility.service.Domain.UserModel;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface ModelMapper {

    UserModel userModelDtoconvertuserModel(UserModelDto userModelDto);
    UserModelDto userModeltoUserModelDto(UserModel userModel);
}
