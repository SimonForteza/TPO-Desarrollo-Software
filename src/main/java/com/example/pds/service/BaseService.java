package com.example.pds.service;

import com.example.pds.util.GenericRepositoryUtils;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Clase base abstracta para servicios que proporciona funcionalidades comunes
 * como validación de IDs y búsqueda de entidades.
 */
public abstract class BaseService {

    @Autowired
    protected GenericRepositoryUtils repositoryUtils;

    /**
     * Valida que un ID no sea nulo
     * @param id ID a validar
     * @param entityName Nombre de la entidad para el mensaje de error
     * @throws IllegalArgumentException si el ID es nulo
     */
    protected void validateIdNotNull(Long id, String entityName) {
        repositoryUtils.validateIdNotNull(id, entityName);
    }

    /**
     * Valida que un objeto no sea nulo
     * @param obj Objeto a validar
     * @param entityName Nombre de la entidad para el mensaje de error
     * @throws IllegalArgumentException si el objeto es nulo
     */
    protected void validateNotNull(Object obj, String entityName) {
        repositoryUtils.validateNotNull(obj, entityName);
    }

    /**
     * Valida que una cadena no sea nula o vacía
     * @param str Cadena a validar
     * @param fieldName Nombre del campo para el mensaje de error
     * @throws IllegalArgumentException si la cadena es nula o vacía
     */
    protected void validateStringNotNullOrEmpty(String str, String fieldName) {
        repositoryUtils.validateStringNotNullOrEmpty(str, fieldName);
    }

    /**
     * Valida múltiples IDs no nulos
     * @param entityName Nombre de la entidad para el mensaje de error
     * @param ids IDs a validar
     * @throws IllegalArgumentException si algún ID es nulo
     */
    protected void validateIdsNotNull(String entityName, Long... ids) {
        repositoryUtils.validateIdsNotNull(entityName, ids);
    }
} 