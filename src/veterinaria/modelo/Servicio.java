package veterinaria.modelo;

/**
 * Representa un servicio ofrecido por la veterinaria, con su tipo, nombre y precio.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class Servicio {

    private String tipo;
    private String nombre;
    private int precio;

    /**
     * Construye un nuevo Servicio.
     * 
     * @param tipo   la categoría o tipo de servicio (ej. Consulta, Vacunación)
     * @param nombre el nombre específico del servicio
     * @param precio el costo del servicio
     */
    public Servicio(String tipo, String nombre, int precio) {
        this.tipo = tipo;
        this.nombre = nombre;
        this.precio = precio;
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
        return nombre + " — $" + precio;
    }
}
