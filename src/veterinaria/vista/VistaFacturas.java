package veterinaria.vista;

import veterinaria.controlador.VeterinariaController;
import veterinaria.excepcion.DaoException;
import veterinaria.modelo.Factura;
import veterinaria.modelo.Paciente;
import veterinaria.modelo.Servicio;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Vista encargada de la interfaz gráfica para la facturación, interactuando
 * con el controlador y manejando excepciones de persistencia.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class VistaFacturas extends JPanel {

    private final VeterinariaController controlador;
    private final JComboBox<Paciente> comboPacientes = new JComboBox<>();
    private final DefaultListModel<Servicio> modeloServicios = new DefaultListModel<>();
    private final JList<Servicio> listaServicios = new JList<>(modeloServicios);
    private final JTextArea areaFactura = new JTextArea(8, 24);

    /**
     * Construye la pantalla de facturas asociándola al controlador.
     * 
     * @param controlador el controlador principal de la aplicación
     */
    public VistaFacturas(VeterinariaController controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());
        add(new BarraNavegacion(), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel superior = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        superior.add(new JLabel("Paciente:"));
        superior.add(comboPacientes);

        JButton botonGenerar = new JButton("Generar factura");
        botonGenerar.addActionListener(e -> generarFactura());
        superior.add(botonGenerar);

        listaServicios.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        JScrollPane scrollServicios = new JScrollPane(listaServicios);
        scrollServicios.setBorder(BorderFactory.createTitledBorder("Servicios (Ctrl/Shift para varios)"));

        JScrollPane scrollFactura = new JScrollPane(areaFactura);
        scrollFactura.setBorder(BorderFactory.createTitledBorder("Factura"));
        scrollFactura.setPreferredSize(new Dimension(260, 200));
        areaFactura.setEditable(false);
        areaFactura.setFont(new Font("Monospaced", Font.PLAIN, 12));

        contenido.add(superior, BorderLayout.NORTH);
        contenido.add(scrollServicios, BorderLayout.CENTER);
        contenido.add(scrollFactura, BorderLayout.EAST);

        add(contenido, BorderLayout.CENTER);

        actualizarDatos();
    }

    /**
     * Actualiza los combos y listas consultando al controlador.
     */
    public void actualizarDatos() {
        try {
            comboPacientes.removeAllItems();
            for (Paciente p : controlador.getListaPacientes()) {
                comboPacientes.addItem(p);
            }

            modeloServicios.clear();
            for (Servicio s : controlador.getTodosLosServicios()) {
                modeloServicios.addElement(s);
            }
        } catch (DaoException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos para factura: " + e.getMessage(),
                    "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Solicita al controlador generar la factura con los elementos seleccionados.
     */
    private void generarFactura() {
        Paciente paciente = (Paciente) comboPacientes.getSelectedItem();
        List<Servicio> elegidos = listaServicios.getSelectedValuesList();

        if (paciente == null || elegidos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Elegí un paciente y por lo menos un servicio.");
            return;
        }

        Factura factura = controlador.generarFactura(paciente, elegidos);
        areaFactura.setText(factura.generarTextoFactura());
    }

    /**
     * Obtiene el JComboBox de pacientes.
     * 
     * @return combo box de pacientes
     */
    public JComboBox<Paciente> getComboPacientes() {
        return comboPacientes;
    }
}
