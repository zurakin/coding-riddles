package com.zurakin.codingriddles.models.mapper;

import com.zurakin.codingriddles.models.dto.UserProfileDto;
import com.zurakin.codingriddles.models.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface UserProfileMapper {
    UserProfileMapper INSTANCE = Mappers.getMapper(UserProfileMapper.class);

    @Mapping(target = "solvedRiddlesCount", expression = "java(entity.getSolvedRiddles() == null ? 0 : entity.getSolvedRiddles().size())")
    UserProfileDto toDto(UserEntity entity);

    @Named("solvedRiddlesSize")
    public static int solvedRiddlesSize(java.util.Set<?> solvedRiddles) {
        return solvedRiddles == null ? 0 : solvedRiddles.size();
    }
}
