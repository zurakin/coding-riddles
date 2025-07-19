package com.zurakin.codingriddles.models.mapper;

import com.zurakin.codingriddles.models.dto.UserProfileDto;
import com.zurakin.codingriddles.models.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface UserProfileMapper {
    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    UserProfileDto toDto(UserEntity entity);
    UserEntity toEntity(UserProfileDto dto);
}
