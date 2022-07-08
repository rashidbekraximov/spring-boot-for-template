package com.example.demo.dto.references.form;

import com.example.demo.dto.BaseReferenceDto;
import com.example.demo.entity.references.Form;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormDto extends BaseReferenceDto {
    private String hrefAddress;

    private Form parentForm;
}
