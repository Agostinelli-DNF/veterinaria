package veterinaria.dao.mysql;

import veterinaria.dao.ServicioDAO;
import veterinaria.excepcion.DaoException;
import veterinaria.modelo.Servicio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import com.mysql.cj.jdbc.Driver;
/**
 * Implementación en MySQL de ServicioDAO utilizando JDBC.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class ServicioDAOMySQL implements ServicioDAO {

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
    public void insert(Servicio entity) throws DaoException {
        String sql = "INSERT INTO servicios (nombre, tipo, precio) VALUES (?, ?, ?)";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, entity.getNombre());
            stmt.setString(2, entity.getTipo());
            stmt.setInt(3, entity.getPrecio());
            stmt.executeUpdate();

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    entity.setId(generatedKeys.getLong(1));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error al insertar el servicio en la base de datos", e);
        }
    }

    @Override
    public Servicio findById(Long id) throws DaoException {
        String sql = "SELECT id, nombre, tipo, precio FROM servicios WHERE id = ?";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Servicio(
                        rs.getLong("id"),
                        rs.getString("tipo"),
                        rs.getString("nombre"),
                        rs.getInt("precio")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error al buscar el servicio por ID: " + id, e);
        }
        return null;
    }

    @Override
    public List<Servicio> findAll() throws DaoException {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, tipo, precio FROM servicios";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                lista.add(new Servicio(
                    rs.getLong("id"),
                    rs.getString("tipo"),
                    rs.getString("nombre"),
                    rs.getInt("precio")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error al listar todos los servicios", e);
        }
        return lista;
    }

    @Override
    public List<Servicio> findByTipo(String tipo) throws DaoException {
        List<Servicio> lista = new ArrayList<>();
        String sql = "SELECT id, nombre, tipo, precio FROM servicios WHERE tipo = ?";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, tipo);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    lista.add(new Servicio(
                        rs.getLong("id"),
                        rs.getString("tipo"),
                        rs.getString("nombre"),
                        rs.getInt("precio")
                    ));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error al buscar servicios por tipo: " + tipo, e);
        }
        return lista;
    }

    @Override
    public void update(Servicio entity) throws DaoException {
        String sql = "UPDATE servicios SET nombre = ?, tipo = ?, precio = ? WHERE id = ?";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, entity.getNombre());
            stmt.setString(2, entity.getTipo());
            stmt.setInt(3, entity.getPrecio());
            stmt.setLong(4, entity.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error al actualizar el servicio con ID: " + entity.getId(), e);
        }
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "DELETE FROM servicios WHERE id = ?";
        try (Connection conn = conectar();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error al eliminar el servicio con ID: " + id, e);
        }
    }
}
