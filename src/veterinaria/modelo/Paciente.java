package veterinaria.modelo;

/**
 * Representa un paciente (mascota) atendido en la veterinaria.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class Paciente {

    private String nombre;
    private String especie;

    /**
     * Construye un nuevo Paciente con su nombre y especie.
     * 
     * @param nombre  el nombre del paciente
     * @param especie la especie del paciente (ej. Perro, Gato)
     */
    public Paciente(String nombre, String especie) {
        this.nombre = nombre;
        this.especie = especie;
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
