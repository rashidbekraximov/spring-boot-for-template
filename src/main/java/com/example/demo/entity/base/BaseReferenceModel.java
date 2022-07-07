package com.example.demo.entity.base;

import com.example.demo.entity.types.Nls;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@MappedSuperclass
public abstract class BaseReferenceModel extends Auditable {

    @Type(type = "com.example.demo.entity.types.Nls")
    @Column(columnDefinition = "t_nls", name = "name", nullable = false)
    private Nls name;

    private boolean status;
}
