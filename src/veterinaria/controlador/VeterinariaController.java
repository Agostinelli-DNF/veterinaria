package veterinaria.controlador;

import veterinaria.dao.FacturaDAO;
import veterinaria.dao.PacienteDAO;
import veterinaria.dao.ServicioDAO;
import veterinaria.dao.mysql.FacturaDAOMySQL;
import veterinaria.dao.mysql.PacienteDAOMySQL;
import veterinaria.dao.mysql.ServicioDAOMySQL;
import veterinaria.excepcion.DaoException;
import veterinaria.modelo.Factura;
import veterinaria.modelo.Paciente;
import veterinaria.modelo.Servicio;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Controlador principal que gestiona la lógica de negocio conectándose
 * exclusivamente a los DAOs de la base de datos MySQL, sin almacenar datos en memoria.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class VeterinariaController {

    private final PacienteDAO pacienteDAO;
    private final ServicioDAO servicioDAO;
    private final FacturaDAO facturaDAO;

    /**
     * Inicializa el controlador instanciando las implementaciones DAO de MySQL.
     */
    public VeterinariaController() {
        this.pacienteDAO = new PacienteDAOMySQL();
        this.servicioDAO = new ServicioDAOMySQL();
        this.facturaDAO = new FacturaDAOMySQL();
    }

    /**
     * Obtiene todos los pacientes desde la base de datos.
     * 
     * @return lista de pacientes
     * @throws DaoException si ocurre un error de persistencia
     */
    public List<Paciente> getListaPacientes() throws DaoException {
        return pacienteDAO.findAll();
    }

    /**
     * Agrega un nuevo paciente en la base de datos.
     * 
     * @param nombre  nombre del paciente
     * @param especie especie del paciente
     * @throws DaoException si ocurre un error de persistencia
     */
    public void agregarPaciente(String nombre, String especie) throws DaoException {
        if (nombre != null && !nombre.trim().isEmpty()) {
            Paciente paciente = new Paciente(nombre.trim(), especie.trim());
            pacienteDAO.insert(paciente);
        }
    }

    /**
     * Actualiza un paciente existente en la base de datos.
     * 
     * @param id      identificador del paciente
     * @param nombre  nuevo nombre
     * @param especie nueva especie
     * @throws DaoException si ocurre un error de persistencia
     */
    public void actualizarPaciente(Long id, String nombre, String especie) throws DaoException {
        if (id != null && nombre != null && !nombre.trim().isEmpty()) {
            Paciente paciente = new Paciente(id, nombre.trim(), especie.trim());
            pacienteDAO.update(paciente);
        }
    }

    /**
     * Elimina un paciente de la base de datos por su ID.
     * 
     * @param id identificador del paciente
     * @throws DaoException si ocurre un error de persistencia
     */
    public void eliminarPaciente(Long id) throws DaoException {
        if (id != null) {
            pacienteDAO.delete(id);
        }
    }

    /**
     * Obtiene los tipos de servicios disponibles desde la base de datos.
     * 
     * @return conjunto de tipos de servicios
     * @throws DaoException si ocurre un error de persistencia
     */
    public Set<String> getTiposServicios() throws DaoException {
        Set<String> tipos = new LinkedHashSet<>();
        tipos.add("Consulta");
        tipos.add("Vacunación");
        tipos.add("Cirugía");
        tipos.add("Estética");
        for (Servicio s : servicioDAO.findAll()) {
            if (s.getTipo() != null) {
                tipos.add(s.getTipo());
            }
        }
        return tipos;
    }

    /**
     * Obtiene los servicios filtrados por tipo desde la base de datos.
     * 
     * @param tipo categoría del servicio
     * @return lista de servicios
     * @throws DaoException si ocurre un error de persistencia
     */
    public List<Servicio> getServiciosPorTipo(String tipo) throws DaoException {
        return servicioDAO.findByTipo(tipo);
    }

    /**
     * Obtiene todos los servicios desde la base de datos.
     * 
     * @return lista de servicios
     * @throws DaoException si ocurre un error de persistencia
     */
    public List<Servicio> getTodosLosServicios() throws DaoException {
        return servicioDAO.findAll();
    }

    /**
     * Agrega un nuevo servicio en la base de datos.
     * 
     * @param tipo   categoría del servicio
     * @param nombre nombre del servicio
     * @param precio costo del servicio
     * @throws DaoException si ocurre un error de persistencia
     */
    public void agregarServicio(String tipo, String nombre, int precio) throws DaoException {
        if (tipo != null && nombre != null && !nombre.trim().isEmpty()) {
            Servicio servicio = new Servicio(tipo, nombre.trim(), precio);
            servicioDAO.insert(servicio);
        }
    }

    /**
     * Actualiza un servicio existente en la base de datos.
     * 
     * @param id     identificador del servicio
     * @param tipo   nueva categoría
     * @param nombre nuevo nombre
     * @param precio nuevo precio
     * @throws DaoException si ocurre un error de persistencia
     */
    public void actualizarServicio(Long id, String tipo, String nombre, int precio) throws DaoException {
        if (id != null && tipo != null && nombre != null && !nombre.trim().isEmpty()) {
            Servicio servicio = new Servicio(id, tipo, nombre.trim(), precio);
            servicioDAO.update(servicio);
        }
    }

    /**
     * Elimina un servicio de la base de datos por su ID.
     * 
     * @param id identificador del servicio
     * @throws DaoException si ocurre un error de persistencia
     */
    public void eliminarServicio(Long id) throws DaoException {
        if (id != null) {
            servicioDAO.delete(id);
        }
    }

    /**
     * Genera un objeto factura y lo guarda en la base de datos a través del DAO.
     * 
     * @param paciente  el paciente seleccionado
     * @param servicios lista de servicios elegidos
     * @return la Factura creada y guardada
     * @throws DaoException si ocurre un error de persistencia
     */
    public Factura generarYGuardarFactura(Paciente paciente, List<Servicio> servicios) throws DaoException {
        Factura factura = new Factura(paciente, servicios);
        facturaDAO.insert(factura);
        return factura;
    }
}
