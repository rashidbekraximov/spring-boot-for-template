package com.example.demo.mappers;

import java.util.List;

public interface BaseReferenceMapper<E, D> extends Mapper {
        D toDto(E e);

        E fromCreateDto(D d);

        List<D> toDto(List<E> e);

        List<E> fromListCreateDto(List<D> ds);
}
