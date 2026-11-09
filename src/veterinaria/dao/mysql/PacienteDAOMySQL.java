package veterinaria.dao.mysql;

import veterinaria.dao.PacienteDAO;
import veterinaria.excepcion.DaoException;
import veterinaria.modelo.Paciente;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
/**
 * Implementación en MySQL de PacienteDAO utilizando JDBC.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class PacienteDAOMySQL implements PacienteDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/veterinaria?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    /**
     * Obtiene una conexión a la base de datos MySQL.
     * 
     * @return objeto Connection
     * @throws SQLException si ocurre un error de conexión
     */


    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, CLAVE);
    }

    @Override
    public void insert(Paciente entity) throws DaoException {
        String sql = "INSERT INTO pacientes (nombre, especie) VALUES (?, ?)";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, entity.getNombre());
            stmt.setString(2, entity.getEspecie());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error al insertar el paciente en la base de datos", e);
        }
    }

    @Override
    public Paciente findById(Long id) throws DaoException {
        String sql = "SELECT id, nombre, especie FROM pacientes WHERE id = ?";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Paciente(
                        rs.getLong("id"),
                        rs.getString("nombre"),
                        rs.getString("especie")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error al buscar el paciente por ID: " + id, e);
        }
        return null;
    }

    @Override
    public List<Paciente> findAll() throws DaoException {
        List<Paciente> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, especie FROM pacientes";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new Paciente(
                    rs.getLong("id"),
                    rs.getString("nombre"),
                    rs.getString("especie")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error al listar todos los pacientes", e);
        }
        return lista;
    }

    @Override
    public void update(Paciente entity) throws DaoException {
        String sql = "UPDATE pacientes SET nombre = ?, especie = ? WHERE id = ?";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, entity.getNombre());
            stmt.setString(2, entity.getEspecie());
            stmt.setLong(3, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error al actualizar el paciente con ID: " + entity.getId(), e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "DELETE FROM pacientes WHERE id = ?";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error al eliminar el paciente con ID: " + id, e);
        }
    }
}
