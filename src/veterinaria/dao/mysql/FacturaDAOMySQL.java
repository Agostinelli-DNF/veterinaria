package veterinaria.dao.mysql;

import veterinaria.dao.FacturaDAO;
import veterinaria.excepcion.DaoException;
import veterinaria.modelo.Factura;
import veterinaria.modelo.Paciente;
import veterinaria.modelo.Servicio;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Implementación en MySQL de FacturaDAO utilizando JDBC y try-with-resources.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class FacturaDAOMySQL implements FacturaDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/veterinaria?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    private static final String USUARIO = "root";
    private static final String CLAVE = "";

    @Override
    public void insert(Factura entity) throws DaoException {
        String sqlFactura = "INSERT INTO facturas (paciente_id, fecha, total) VALUES (?, ?, ?)";
        String sqlItem = "INSERT INTO factura_servicios (factura_id, servicio_id) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, CLAVE)) {
            conn.setAutoCommit(false);
            try (PreparedStatement stmtFactura = conn.prepareStatement(sqlFactura, Statement.RETURN_GENERATED_KEYS)) {
                
                stmtFactura.setLong(1, entity.getPaciente().getId());
                stmtFactura.setDate(2, Date.valueOf(LocalDate.now()));
                stmtFactura.setInt(3, entity.getTotal());
                stmtFactura.executeUpdate();

                try (ResultSet generatedKeys = stmtFactura.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        entity.setId(generatedKeys.getLong(1));
                    }
                }

                if (entity.getServicios() != null) {
                    try (PreparedStatement stmtItem = conn.prepareStatement(sqlItem)) {
                        for (Servicio servicio : entity.getServicios()) {
                            stmtItem.setLong(1, entity.getId());
                            stmtItem.setLong(2, servicio.getId());
                            stmtItem.addBatch();
                        }
                        stmtItem.executeBatch();
                    }
                }

                conn.commit();
            } catch (SQLException e) {
                conn.rollback();
                e.printStackTrace();
                throw new DaoException("Error al insertar la factura en la base de datos", e);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error de conexión al insertar la factura", e);
        }
    }

    @Override
    public Factura findById(Long id) throws DaoException {
        String sql = "SELECT f.id AS factura_id, f.total, p.id AS paciente_id, p.nombre AS paciente_nombre, p.especie AS paciente_especie " +
                     "FROM facturas f JOIN pacientes p ON f.paciente_id = p.id WHERE f.id = ?";
        String sqlServicios = "SELECT s.id, s.tipo, s.nombre, s.precio FROM servicios s " +
                              "JOIN factura_servicios fs ON s.id = fs.servicio_id WHERE fs.factura_id = ?";

        try (Connection conn = DriverManager.getConnection(URL, USUARIO, CLAVE);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Paciente paciente = new Paciente(
                        rs.getLong("paciente_id"),
                        rs.getString("paciente_nombre"),
                        rs.getString("paciente_especie")
                    );
                    
                    List<Servicio> servicios = new ArrayList<>();
                    try (PreparedStatement stmtServ = conn.prepareStatement(sqlServicios)) {
                        stmtServ.setLong(1, id);
                        try (ResultSet rsServ = stmtServ.executeQuery()) {
                            while (rsServ.next()) {
                                servicios.add(new Servicio(
                                    rsServ.getLong("id"),
                                    rsServ.getString("tipo"),
                                    rsServ.getString("nombre"),
                                    rsServ.getInt("precio")
                                ));
                            }
                        }
                    }

                    return new Factura(rs.getLong("factura_id"), paciente, servicios);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error al buscar la factura por ID: " + id, e);
        }
        return null;
    }

    @Override
    public List<Factura> findAll() throws DaoException {
        List<Factura> lista = new ArrayList<>();
        String sql = "SELECT id FROM facturas";
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, CLAVE);
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Factura f = findById(rs.getLong("id"));
                if (f != null) {
                    lista.add(f);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error al listar todas las facturas", e);
        }
        return lista;
    }

    @Override
    public void update(Factura entity) throws DaoException {
        throw new UnsupportedOperationException("Modificación de facturas no soportada.");
    }

    @Override
    public void delete(Long id) throws DaoException {
        String sql = "DELETE FROM facturas WHERE id = ?";
        try (Connection conn = DriverManager.getConnection(URL, USUARIO, CLAVE);
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DaoException("Error al eliminar la factura con ID: " + id, e);
        }
    }
}
