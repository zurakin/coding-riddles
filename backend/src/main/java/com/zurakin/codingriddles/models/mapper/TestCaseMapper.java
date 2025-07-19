package com.zurakin.codingriddles.models.mapper;

import com.zurakin.codingriddles.models.dto.TestCaseDto;
import com.zurakin.codingriddles.models.entity.TestCaseEntity;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper()
public interface TestCaseMapper {
    TestCaseMapper INSTANCE = Mappers.getMapper(TestCaseMapper.class);

    TestCaseDto toDto(TestCaseEntity entity);
    TestCaseEntity toEntity(TestCaseDto dto);
}
