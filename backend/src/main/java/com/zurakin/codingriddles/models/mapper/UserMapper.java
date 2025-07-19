package com.zurakin.codingriddles.models.mapper;

import com.zurakin.codingriddles.models.domain.User;
import com.zurakin.codingriddles.models.dto.UserProfileDto;
import com.zurakin.codingriddles.models.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    UserEntity toEntity(User user);
    UserEntity toEntity(UserProfileDto userDTO);

    User toDomain(UserEntity userEntity);
    UserProfileDto toDto(UserEntity userEntity);
}
