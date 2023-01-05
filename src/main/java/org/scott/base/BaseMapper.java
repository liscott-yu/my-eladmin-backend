package org.scott.base;

import java.util.List;

/**
 * project name  my-eladmin-backend-v2
 * filename  BaseMapper
 * @author liscott
 * @date 2023/1/4 17:34
 * description  类 转换，D-> DTO, E->Entity
 */
public interface BaseMapper<D, E> {
    /**
     * DTO转Entity
     * @param dto DTO
     * @return  Entity
     */
    E toEntity(D dto);

    /**
     * Entity转DTO
     * @param entity Entity
     * @return DTO
     */
    D toDto(E entity);

    /**
     * DTO集合转Entity集合
     * @param dtoList List<DTO>
     * @return List<Entity>
     */
    List<E> toEntity(List<D> dtoList);

    /**
     * Entity集合转DTO集合
     * @param entityList List<Entity>
     * @return List<DTO>
     */
    List <D> toDto(List<E> entityList);
}
