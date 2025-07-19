package com.zurakin.codingriddles.models.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import com.zurakin.codingriddles.models.entity.TestCaseEntity;
import com.zurakin.codingriddles.models.dto.TestCaseDto;

@Mapper()
public interface TestCaseMapper {
    TestCaseMapper INSTANCE = Mappers.getMapper(TestCaseMapper.class);

    TestCaseDto toDto(TestCaseEntity entity);
    TestCaseEntity toEntity(TestCaseDto dto);
}
