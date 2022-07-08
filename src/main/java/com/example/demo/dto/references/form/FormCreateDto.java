package com.example.demo.dto.references.form;

import com.example.demo.dto.BaseReferenceDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormCreateDto extends BaseReferenceDto {
    private String hrefAddress;

    private Integer parentFormId;
}
