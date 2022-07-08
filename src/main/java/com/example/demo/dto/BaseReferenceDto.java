package com.example.demo.dto;

import com.example.demo.entity.types.Nls;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseReferenceDto extends GenericDto implements Dto {

    @Transient
    private Nls name;

    private boolean status;
}
