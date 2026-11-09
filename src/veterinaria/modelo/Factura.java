package veterinaria.modelo;

import java.util.List;

/**
 * Representa una factura emitida para un paciente con un conjunto de servicios seleccionados.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class Factura {

    private Long id;
    private Paciente paciente;
    private List<Servicio> servicios;
    private int total;

    /**
     * Construye una nueva Factura sin ID (para inserciones).
     * 
     * @param paciente  el paciente asociado a la factura
     * @param servicios la lista de servicios incluidos
     */
    public Factura(Paciente paciente, List<Servicio> servicios) {
        this.paciente = paciente;
        this.servicios = servicios;
        calcularTotal();
    }

    /**
     * Construye una nueva Factura con ID (para registros existentes de la BD).
     * 
     * @param id        el identificador único de la factura
     * @param paciente  el paciente asociado a la factura
     * @param servicios la lista de servicios incluidos
     */
    public Factura(Long id, Paciente paciente, List<Servicio> servicios) {
        this.id = id;
        this.paciente = paciente;
        this.servicios = servicios;
        calcularTotal();
    }

    /**
     * Calcula el monto total sumando los precios de todos los servicios.
     */
    private void calcularTotal() {
        this.total = 0;
        if (servicios != null) {
            for (Servicio servicio : servicios) {
                this.total += servicio.getPrecio();
            }
        }
    }

    /**
     * Obtiene el identificador de la factura.
     * 
     * @return el ID
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la factura.
     * 
     * @param id el nuevo ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el paciente de la factura.
     * 
     * @return el paciente
     */
    public Paciente getPaciente() {
        return paciente;
    }

    /**
     * Establece el paciente de la factura.
     * 
     * @param paciente el nuevo paciente
     */
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }

    /**
     * Obtiene la lista de servicios incluidos.
     * 
     * @return lista de servicios
     */
    public List<Servicio> getServicios() {
        return servicios;
    }

    /**
     * Establece la lista de servicios y recalcula el total.
     * 
     * @param servicios la nueva lista de servicios
     */
    public void setServicios(List<Servicio> servicios) {
        this.servicios = servicios;
        calcularTotal();
    }

    /**
     * Obtiene el monto total de la factura.
     * 
     * @return el total
     */
    public int getTotal() {
        return total;
    }

    /**
     * Genera un formato de texto detallado de la factura.
     * 
     * @return texto estructurado de la factura
     */
    public String generarTextoFactura() {
        StringBuilder sb = new StringBuilder("FACTURA\n");
        if (id != null) {
            sb.append("Nº Factura: ").append(id).append("\n");
        }
        sb.append("Paciente: ").append(paciente != null ? paciente.toString() : "Ninguno").append("\n");
        if (servicios != null) {
            for (Servicio s : servicios) {
                sb.append(s.getNombre()).append(" .......... $").append(s.getPrecio()).append("\n");
            }
        }
        sb.append("TOTAL: $").append(total);
        return sb.toString();
    }
}
