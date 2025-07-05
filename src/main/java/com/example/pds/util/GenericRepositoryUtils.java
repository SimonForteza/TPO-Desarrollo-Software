package com.example.pds.util;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Component;

import java.util.function.Supplier;

@Component
public class GenericRepositoryUtils {

    /**
     * Busca una entidad por ID con excepción personalizada
     * @param repository Repositorio a usar
     * @param id ID de la entidad
     * @param exceptionSupplier Proveedor de la excepción personalizada
     * @param <T> Tipo de la entidad
     * @return Entidad encontrada
     */
    public <T> T findByIdOrThrow(JpaRepository<T, Long> repository, Long id, 
                                Supplier<? extends RuntimeException> exceptionSupplier) {
        return repository.findById(id)
            .orElseThrow(exceptionSupplier);
    }

    /**
     * Valida que un ID no sea nulo
     * @param id ID a validar
     * @param entityName Nombre de la entidad para el mensaje de error
     * @throws IllegalArgumentException si el ID es nulo
     */
    public void validateIdNotNull(Long id, String entityName) {
        if (id == null) {
            throw new IllegalArgumentException("El ID del " + entityName + " no puede ser nulo");
        }
    }

    /**
     * Valida que un objeto no sea nulo
     * @param obj Objeto a validar
     * @param entityName Nombre de la entidad para el mensaje de error
     * @throws IllegalArgumentException si el objeto es nulo
     */
    public void validateNotNull(Object obj, String entityName) {
        if (obj == null) {
            throw new IllegalArgumentException("El " + entityName + " no puede ser nulo");
        }
    }

    /**
     * Valida que una cadena no sea nula, vacía o contenga solo espacios en blanco
     * @param str Cadena a validar
     * @param fieldName Nombre del campo para el mensaje de error
     * @throws IllegalArgumentException si la cadena es nula, vacía o contiene solo espacios en blanco
     */
    public void validateStringNotNullOrEmpty(String str, String fieldName) {
        if (str == null || str.isBlank()) {
            throw new IllegalArgumentException("El " + fieldName + " no puede ser nulo o vacío");
        }
    }

    /**
     * Valida múltiples IDs no nulos
     * @param entityName Nombre de la entidad para el mensaje de error
     * @param ids IDs a validar
     * @throws IllegalArgumentException si algún ID es nulo
     */
    public void validateIdsNotNull(String entityName, Long... ids) {
        for (Long id : ids) {
            validateIdNotNull(id, entityName);
        }
    }
} 