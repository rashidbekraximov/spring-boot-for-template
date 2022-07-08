package com.example.demo.dto.references;

import com.example.demo.dto.BaseReferenceDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class FormDto extends BaseReferenceDto {
    private String urlNameForm;

    private Integer parentFormId;
}
