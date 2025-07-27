package com.zurakin.codingriddles.models.mapper;

import com.zurakin.codingriddles.models.dto.AuthorDto;
import com.zurakin.codingriddles.models.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface AuthorMapper {
    AuthorMapper INSTANCE = Mappers.getMapper(AuthorMapper.class);

    AuthorDto toDto(UserEntity entity);
}
