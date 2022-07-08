package com.example.demo.entity.references;

import com.example.demo.entity.base.BaseReferenceModel;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "r_forms", schema = "reference")
public class Form extends BaseReferenceModel {
    @Column(nullable = false,unique = true)
    private String hrefAddress;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id")
    private Form parentForm;

    @OneToMany(mappedBy = "parentForm", cascade = CascadeType.PERSIST)
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Form> childForms;

}
