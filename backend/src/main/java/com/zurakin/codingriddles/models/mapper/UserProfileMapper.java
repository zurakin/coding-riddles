package com.zurakin.codingriddles.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.zurakin.codingriddles.models.entity.UserEntity;
import com.zurakin.codingriddles.models.dto.UserProfileDto;

@Mapper()
public interface UserProfileMapper {
    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    UserProfileDto toDto(UserEntity entity);
    UserEntity toEntity(UserProfileDto dto);
}
