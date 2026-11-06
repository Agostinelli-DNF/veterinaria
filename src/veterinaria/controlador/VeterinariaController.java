package veterinaria.controlador;

import veterinaria.modelo.Factura;
import veterinaria.modelo.Paciente;
import veterinaria.modelo.Servicio;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Controlador principal que gestiona la lógica de negocio y los datos en memoria
 * para pacientes, servicios y facturación de la veterinaria.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class VeterinariaController {

    private final List<Paciente> listaPacientes;
    private final Map<String, List<Servicio>> serviciosPorTipo;

    /**
     * Inicializa el controlador cargando datos de ejemplo (mock data).
     */
    public VeterinariaController() {
        this.listaPacientes = new ArrayList<>();
        this.serviciosPorTipo = new LinkedHashMap<>();
        cargarDatosDeEjemplo();
    }

    /**
     * Carga datos iniciales de ejemplo para pacientes y servicios.
     */
    private void cargarDatosDeEjemplo() {
        // Pacientes de ejemplo
        listaPacientes.add(new Paciente("Rex", "Perro"));
        listaPacientes.add(new Paciente("Michi", "Gato"));
        listaPacientes.add(new Paciente("Piolín", "Canario"));

        // Servicios por tipo con sus precios reales
        List<Servicio> consultas = new ArrayList<>();
        consultas.add(new Servicio("Consulta", "Consulta general", 5000));
        consultas.add(new Servicio("Consulta", "Control de salud", 4000));
        serviciosPorTipo.put("Consulta", consultas);

        List<Servicio> vacunacion = new ArrayList<>();
        vacunacion.add(new Servicio("Vacunación", "Vacuna antirrábica", 8000));
        vacunacion.add(new Servicio("Vacunación", "Quíntuple", 10000));
        serviciosPorTipo.put("Vacunación", vacunacion);

        List<Servicio> cirugia = new ArrayList<>();
        cirugia.add(new Servicio("Cirugía", "Castración", 45000));
        cirugia.add(new Servicio("Cirugía", "Limpieza dental", 25000));
        serviciosPorTipo.put("Cirugía", cirugia);

        List<Servicio> estetica = new ArrayList<>();
        estetica.add(new Servicio("Estética", "Baño", 9000));
        estetica.add(new Servicio("Estética", "Corte de uñas", 1500));
        serviciosPorTipo.put("Estética", estetica);
    }

    /**
     * Obtiene la lista actual de pacientes.
     * 
     * @return lista de pacientes
     */
    public List<Paciente> getListaPacientes() {
        return listaPacientes;
    }

    /**
     * Agrega un nuevo paciente a la lista.
     * 
     * @param nombre  nombre del paciente
     * @param especie especie del paciente
     */
    public void agregarPaciente(String nombre, String especie) {
        if (nombre != null && !nombre.trim().isEmpty()) {
            listaPacientes.add(new Paciente(nombre.trim(), especie.trim()));
        }
    }

    /**
     * Elimina un paciente de la lista por su índice.
     * 
     * @param indice posición del paciente en la lista
     */
    public void eliminarPaciente(int indice) {
        if (indice >= 0 && indice < listaPacientes.size()) {
            listaPacientes.remove(indice);
        }
    }

    /**
     * Obtiene los tipos de servicios disponibles.
     * 
     * @return conjunto con los nombres de los tipos de servicios
     */
    public Set<String> getTiposServicios() {
        return serviciosPorTipo.keySet();
    }

    /**
     * Obtiene la lista de servicios para un tipo específico.
     * 
     * @param tipo categoría del servicio
     * @return lista de servicios de esa categoría
     */
    public List<Servicio> getServiciosPorTipo(String tipo) {
        return serviciosPorTipo.getOrDefault(tipo, new ArrayList<>());
    }

    /**
     * Obtiene todos los servicios disponibles (aplanados en una sola lista).
     * 
     * @return lista de todos los servicios
     */
    public List<Servicio> getTodosLosServicios() {
        List<Servicio> todos = new ArrayList<>();
        for (List<Servicio> lista : serviciosPorTipo.values()) {
            todos.addAll(lista);
        }
        return todos;
    }

    /**
     * Agrega un nuevo servicio a una categoría específica con su precio.
     * 
     * @param tipo   categoría del servicio
     * @param nombre nombre del servicio
     * @param precio costo del servicio
     */
    public void agregarServicio(String tipo, String nombre, int precio) {
        if (tipo != null && nombre != null && !nombre.trim().isEmpty()) {
            List<Servicio> lista = serviciosPorTipo.computeIfAbsent(tipo, k -> new ArrayList<>());
            lista.add(new Servicio(tipo, nombre.trim(), precio));
        }
    }

    /**
     * Elimina un servicio de una categoría por su índice.
     * 
     * @param tipo   categoría del servicio
     * @param indice posición en la lista de esa categoría
     */
    public void eliminarServicio(String tipo, int indice) {
        List<Servicio> lista = serviciosPorTipo.get(tipo);
        if (lista != null && indice >= 0 && indice < lista.size()) {
            lista.remove(indice);
        }
    }

    /**
     * Genera una factura para el paciente y los servicios seleccionados.
     * 
     * @param paciente  el paciente seleccionado
     * @param servicios lista de servicios elegidos
     * @return objeto Factura creado
     */
    public Factura generarFactura(Paciente paciente, List<Servicio> servicios) {
        return new Factura(paciente, servicios);
    }
}
