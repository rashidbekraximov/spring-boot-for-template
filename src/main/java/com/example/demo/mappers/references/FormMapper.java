package com.example.demo.mappers.references;

import com.example.demo.dto.references.FormDto;
import com.example.demo.entity.references.Form;
import com.example.demo.mappers.BaseReferenceMapper;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
@Component
public interface FormMapper extends BaseReferenceMapper<
        Form,
        FormDto> {

    @Override
    FormDto toDto(Form form);

    @Override
    List<FormDto> toDto(List<Form> e);

    @Override
    List<Form> fromListCreateDto(List<FormDto> formDtos);
}
