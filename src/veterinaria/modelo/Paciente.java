package veterinaria.modelo;

/**
 * Representa un paciente (mascota) atendido en la veterinaria.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class Paciente {

    private Long id;
    private String nombre;
    private String especie;

    /**
     * Construye un nuevo Paciente sin ID (para inserciones).
     * 
     * @param nombre  el nombre del paciente
     * @param especie la especie del paciente
     */
    public Paciente(String nombre, String especie) {
        this.nombre = nombre;
        this.especie = especie;
    }

    /**
     * Construye un nuevo Paciente con ID (para registros existentes de la BD).
     * 
     * @param id      el identificador único del paciente
     * @param nombre  el nombre del paciente
     * @param especie la especie del paciente
     */
    public Paciente(Long id, String nombre, String especie) {
        this.id = id;
        this.nombre = nombre;
        this.especie = especie;
    }

    /**
     * Obtiene el identificador del paciente.
     * 
     * @return el ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del paciente.
     * 
     * @param id el nuevo ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del paciente.
     * 
     * @return el nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del paciente.
     * 
     * @param nombre el nuevo nombre
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene la especie del paciente.
     * 
     * @return la especie
     */
    public String getEspecie() {
        return especie;
    }

    /**
     * Establece la especie del paciente.
     * 
     * @param especie la nueva especie
     */
    public void setEspecie(String especie) {
        this.especie = especie;
    }

    @Override
    public String toString() {
        String especieTexto = (especie == null || especie.trim().isEmpty()) ? "sin especie" : especie;
        return nombre + " (" + especieTexto + ")";
    }
}
