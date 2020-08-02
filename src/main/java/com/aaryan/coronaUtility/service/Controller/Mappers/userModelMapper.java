package com.aaryan.coronaUtility.service.Controller.Mappers;

import com.aaryan.coronaUtility.service.Controller.Model.UserModelDto;
import com.aaryan.coronaUtility.service.Domain.UserModel;
import org.mapstruct.Mapper;

@Mapper
public interface userModelMapper {

    UserModel userModelDtoToUserModel(UserModelDto userModelDto);

    UserModelDto userModelToUserModelDto(UserModel userModel);
}
