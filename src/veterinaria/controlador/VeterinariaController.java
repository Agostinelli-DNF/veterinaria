package veterinaria.controlador;

import veterinaria.dao.PacienteDAO;
import veterinaria.dao.ServicioDAO;
import veterinaria.dao.mysql.PacienteDAOMySQL;
import veterinaria.dao.mysql.ServicioDAOMySQL;
import veterinaria.excepcion.DaoException;
import veterinaria.modelo.Factura;
import veterinaria.modelo.Paciente;
import veterinaria.modelo.Servicio;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Controlador principal que gestiona la lógica de negocio conectándose
 * a los DAOs de la base de datos MySQL.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class VeterinariaController {

    private final PacienteDAO pacienteDAO;
    private final ServicioDAO servicioDAO;

    /**
     * Inicializa el controlador instanciando las implementaciones DAO para MySQL.
     */
    public VeterinariaController() {
        this.pacienteDAO = new PacienteDAOMySQL();
        this.servicioDAO = new ServicioDAOMySQL();
    }

    /**
     * Obtiene la lista de todos los pacientes desde la base de datos.
     * 
     * @return lista de pacientes
     * @throws DaoException si ocurre un error en la base de datos
     */
    public List<Paciente> getListaPacientes() throws DaoException {
        return pacienteDAO.findAll();
    }

    /**
     * Agrega un nuevo paciente a la base de datos.
     * 
     * @param nombre  nombre del paciente
     * @param especie especie del paciente
     * @throws DaoException si ocurre un error en la base de datos
     */
    public void agregarPaciente(String nombre, String especie) throws DaoException {
        if (nombre != null && !nombre.trim().isEmpty()) {
            Paciente paciente = new Paciente(nombre.trim(), especie.trim());
            pacienteDAO.insert(paciente);
        }
    }

    /**
     * Elimina un paciente de la base de datos por su ID.
     * 
     * @param id identificador único del paciente
     * @throws DaoException si ocurre un error en la base de datos
     */
    public void eliminarPaciente(Long id) throws DaoException {
        if (id != null) {
            pacienteDAO.delete(id);
        }
    }

    /**
     * Obtiene los tipos de servicios disponibles.
     * 
     * @return conjunto de tipos de servicios
     * @throws DaoException si ocurre un error en la base de datos
     */
    public Set<String> getTiposServicios() throws DaoException {
        Set<String> tipos = new LinkedHashSet<>();
        tipos.add("Consulta");
        tipos.add("Vacunación");
        tipos.add("Cirugía");
        tipos.add("Estética");
        
        // También podemos incluir dinámicamente los que estén en la BD
        for (Servicio s : servicioDAO.findAll()) {
            if (s.getTipo() != null) {
                tipos.add(s.getTipo());
            }
        }
        return tipos;
    }

    /**
     * Obtiene la lista de servicios filtrados por tipo desde la base de datos.
     * 
     * @param tipo categoría del servicio
     * @return lista de servicios
     * @throws DaoException si ocurre un error en la base de datos
     */
    public List<Servicio> getServiciosPorTipo(String tipo) throws DaoException {
        return servicioDAO.findByTipo(tipo);
    }

    /**
     * Obtiene todos los servicios registrados en la base de datos.
     * 
     * @return lista de servicios
     * @throws DaoException si ocurre un error en la base de datos
     */
    public List<Servicio> getTodosLosServicios() throws DaoException {
        return servicioDAO.findAll();
    }

    /**
     * Agrega un nuevo servicio a la base de datos.
     * 
     * @param tipo   categoría del servicio
     * @param nombre nombre del servicio
     * @param precio costo del servicio
     * @throws DaoException si ocurre un error en la base de datos
     */
    public void agregarServicio(String tipo, String nombre, int precio) throws DaoException {
        if (tipo != null && nombre != null && !nombre.trim().isEmpty()) {
            Servicio servicio = new Servicio(tipo, nombre.trim(), precio);
            servicioDAO.insert(servicio);
        }
    }

    /**
     * Elimina un servicio de la base de datos por su ID.
     * 
     * @param id identificador único del servicio
     * @throws DaoException si ocurre un error en la base de datos
     */
    public void eliminarServicio(Long id) throws DaoException {
        if (id != null) {
            servicioDAO.delete(id);
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
