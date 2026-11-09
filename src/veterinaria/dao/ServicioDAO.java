package veterinaria.dao;

import veterinaria.excepcion.DaoException;
import veterinaria.modelo.Servicio;
import java.util.List;

/**
 * Interfaz específica DAO para la entidad Servicio.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public interface ServicioDAO extends DAO<Servicio, Long> {

    /**
     * Obtiene una lista de servicios filtrados por su tipo o categoría.
     * 
     * @param tipo la categoría del servicio
     * @return lista de servicios de ese tipo
     * @throws DaoException si ocurre un error de acceso a datos
     */
    List<Servicio> findByTipo(String tipo) throws DaoException;
}
