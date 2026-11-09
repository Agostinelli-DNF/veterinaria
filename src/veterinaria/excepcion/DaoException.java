package veterinaria.excepcion;

/**
 * Excepción personalizada verificada (checked exception) para encapsular
 * los errores ocurridos en la capa de persistencia (DAO).
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class DaoException extends Exception {

    /**
     * Construye una nueva DaoException con el mensaje especificado.
     * 
     * @param message el mensaje descriptivo del error
     */
    public DaoException(String message) {
        super(message);
    }

    /**
     * Construye una nueva DaoException con el mensaje especificado y la causa original.
     * 
     * @param message el mensaje descriptivo del error
     * @param cause   la excepción original (SQLException u otra) que causó este error
     */
    public DaoException(String message, Throwable cause) {
        super(message, cause);
    }
}
