package com.example.demo.service;

import com.example.demo.dto.response.AppErrorDto;
import com.example.demo.dto.response.DataDto;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.MappedSuperclass;
import javax.transaction.Transactional;

@MappedSuperclass
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public abstract class AbstractFormService implements AbstractService{
    /* REFERENCE FORM NAME */
    private final String formReferenceName = "Form";

    @Transactional
    public <T> ResponseEntity<DataDto<T>> findErrorById(String formName, Integer id) {
        return new ResponseEntity<>(new DataDto<>(AppErrorDto
                .builder()
                .status(HttpStatus.NOT_FOUND)
                .message(formName + " not found by id : '%s'".formatted(id))
                .build()
        ), HttpStatus.CONFLICT);
    }
}
