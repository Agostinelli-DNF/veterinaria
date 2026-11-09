package veterinaria.modelo;

/**
 * Representa un servicio ofrecido por la veterinaria, con su tipo, nombre y precio.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class Servicio {

    private Long id;
    private String tipo;
    private String nombre;
    private int precio;

    /**
     * Construye un nuevo Servicio sin ID (para inserciones).
     * 
     * @param tipo   la categoría o tipo de servicio
     * @param nombre el nombre específico del servicio
     * @param precio el costo del servicio
     */
    public Servicio(String tipo, String nombre, int precio) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Construye un nuevo Servicio con ID (para registros existentes de la BD).
     * 
     * @param id     el identificador único del servicio
     * @param tipo   la categoría o tipo de servicio
     * @param nombre el nombre específico del servicio
     * @param precio el costo del servicio
     */
    public Servicio(Long id, String tipo, String nombre, int precio) {
        this.id = id;
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
    }

    /**
     * Obtiene el identificador del servicio.
     * 
     * @return el ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del servicio.
     * 
     * @param id el nuevo ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el tipo o categoría del servicio.
     * 
     * @return el tipo de servicio
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * Establece el tipo o categoría del servicio.
     * 
     * @param tipo el nuevo tipo
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    /**
     * Obtiene el nombre del servicio.
     * 
     * @return el nombre del servicio
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del servicio.
     * 
     * @param nombre el nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio del servicio.
     * 
     * @return el precio en moneda local
     */
    public int getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del servicio.
     * 
     * @param precio el nuevo precio
     */
    public void setPrecio(int precio) {
        this.precio = precio;
    }

    @Override
    public String toString() {
        return nombre + " (" + tipo + ") — $" + precio;
    }
}
