package com.looptracker.looptracker.mapper;

import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;
import java.util.List;

@Component
public interface BaseMapper<E, D> {
    E toEntitySkipID(D dto, @MappingTarget E e);

    D toDtoSkipID(D dto, @MappingTarget D e);

    E toEntity(D dto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    E toEntity(D dto, @MappingTarget E e);

    D toDto(E entity);

    List<E> toListEntity(List<D> dtoList);

    List<D> toListDto(List<E> entities);

    default Page<D> toPageDto(Page<E> entities) {
        return entities.map(this::toDto);
    }

    default Page<D> toPageDto(List<E> entities) {
        List<D> dtos = toListDto(entities);
        Integer size = dtos.size();
        return new PageImpl<D>(dtos, PageRequest.of(0, size > 0 ? size : 1), size);
    }

    default Page<D> toPageDtoFull(List<D> entities) {
        Integer size = entities.size();
        return new PageImpl<D>(entities, PageRequest.of(0, size > 0 ? size : 1), size);
    }

}
