package com.zurakin.codingriddles.models.mapper;

import com.zurakin.codingriddles.models.dto.SolvedRiddleDto;
import com.zurakin.codingriddles.models.entity.SolvedRiddleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface SolvedRiddleMapper {
    SolvedRiddleMapper INSTANCE = Mappers.getMapper(SolvedRiddleMapper.class);

    @Mapping(target = "riddleId", source = "riddle.id")
    @Mapping(target = "answerCode", source = "answerCode")
    @Mapping(target = "solvedAt", expression = "java(entity.getSolvedAt() != null ? entity.getSolvedAt().toString() : null)")
    SolvedRiddleDto toDto(SolvedRiddleEntity entity);
}

