package com.example.demo.dto;

import com.example.demo.dto.response.AppErrorDto;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MultipleDto< T> implements Serializable {
    protected T data;

    protected AppErrorDto error;

    protected boolean success;

    private Long totalCount;

    public MultipleDto(boolean success) {
        this.success = success;
    }

    public MultipleDto(AppErrorDto error) {
        this.error = error;
        this.success = false;
    }

    public MultipleDto(T data, Long totalCount) {
        this.data = data;
        this.success = true;
        this.totalCount = totalCount;
    }
}
