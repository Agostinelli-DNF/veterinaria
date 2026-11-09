package veterinaria.dao;

import veterinaria.excepcion.DaoException;
import java.util.List;

/**
 * Interfaz genérica DAO (Data Access Object) que define las operaciones CRUD básicas.
 * 
 * @param <T>  el tipo de entidad que maneja el DAO
 * @param <ID> el tipo de dato del identificador (clave primaria)
 * @author Veterinaria App
 * @version 1.0
 */
public interface DAO<T, ID> {

    /**
     * Inserta una nueva entidad en la base de datos.
     * 
     * @param entity la entidad a insertar
     * @throws DaoException si ocurre un error de acceso a datos
     */
    void insert(T entity) throws DaoException;

    /**
     * Busca una entidad por su identificador único.
     * 
     * @param id el identificador de la entidad
     * @return la entidad encontrada o null si no existe
     * @throws DaoException si ocurre un error de acceso a datos
     */
    T findById(ID id) throws DaoException;

    /**
     * Obtiene una lista con todas las entidades almacenadas.
     * 
     * @return lista de entidades
     * @throws DaoException si ocurre un error de acceso a datos
     */
    List<T> findAll() throws DaoException;

    /**
     * Actualiza una entidad existente en la base de datos.
     * 
     * @param entity la entidad con los datos actualizados
     * @throws DaoException si ocurre un error de acceso a datos
     */
    void update(T entity) throws DaoException;

    /**
     * Elimina una entidad de la base de datos por su identificador.
     * 
     * @param id el identificador de la entidad a eliminar
     * @throws DaoException si ocurre un error de acceso a datos
     */
    void delete(ID id) throws DaoException;
}
