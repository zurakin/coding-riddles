package com.zurakin.codingriddles.models.mapper;

import com.zurakin.codingriddles.models.dto.RiddleDetailsDto;
import com.zurakin.codingriddles.models.dto.RiddleDto;
import com.zurakin.codingriddles.models.dto.RiddleRequestDto;
import com.zurakin.codingriddles.models.entity.RiddleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(uses = {TestCaseMapper.class, UserProfileMapper.class}, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface RiddleMapper {
    RiddleMapper INSTANCE = Mappers.getMapper(RiddleMapper.class);

    RiddleDetailsDto toDetailsDto(RiddleEntity entity);
    RiddleEntity toEntity(RiddleRequestDto dto);
    RiddleDto toDto(RiddleEntity entity);
    RiddleEntity toEntity(RiddleDto dto);
}
