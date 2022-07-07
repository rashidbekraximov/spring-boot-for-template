package com.example.demo.entity.references;

import com.example.demo.entity.base.BaseReferenceModel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "r_forms", schema = "reference")
public class Form extends BaseReferenceModel {
    @Column(nullable = false,unique = true)
    private String urlNameForm;
}
