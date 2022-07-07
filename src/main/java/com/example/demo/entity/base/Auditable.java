package com.example.demo.entity.base;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
@MappedSuperclass
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Auditable implements BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique = true,updatable = false,nullable = false)
    private Integer id;

    @CreatedBy
    @Column(name = "created_by",updatable = false)
    private Integer createdBy;

    @LastModifiedBy
    @Column(name = "updated_by")
    private Integer updatedBy;

    @CreatedDate
    @CreationTimestamp
    @Column(name = "created_at",updatable = false, columnDefinition = "timestamp default now()")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @UpdateTimestamp
    @Column(name = "updated_at",nullable = false)
    private LocalDateTime updatedAt;
}
