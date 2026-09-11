package veterinaria;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/*Vista de la entidad Factura.
 */
public class VistaFacturas extends JPanel {

    /*Servicio con su precio real, evita parsear texto para calcular. */
    private record ServicioCosto(String nombre, int precio) {
        @Override
        public String toString() {
            return nombre + " — $" + precio;
        }
    }

    private final JComboBox<String> comboPacientes = new JComboBox<>();
    private final DefaultListModel<ServicioCosto> modeloServicios = new DefaultListModel<>();
    private final JList<ServicioCosto> listaServicios = new JList<>(modeloServicios);
    private final JTextArea areaFactura = new JTextArea(8, 24);

    /*Construye la pantalla de facturas y carga datos de ejemplo. */
    public VistaFacturas() {
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

        cargarDatosDeEjemplo();
    }

    private void cargarDatosDeEjemplo() {
        comboPacientes.addItem("Rex (Perro)");
        comboPacientes.addItem("Michi (Gato)");
        comboPacientes.addItem("Piolín (Canario)");
        modeloServicios.addElement(new ServicioCosto("Consulta general", 5000));
        modeloServicios.addElement(new ServicioCosto("Vacuna antirrábica", 8000));
        modeloServicios.addElement(new ServicioCosto("Castración", 45000));
        modeloServicios.addElement(new ServicioCosto("Baño", 9000));
        modeloServicios.addElement(new ServicioCosto("Corte de uñas", 1500));
    }

    /*Arma una factura con el paciente y los servicios elegidos.*/
    private void generarFactura() {
        String paciente = (String) comboPacientes.getSelectedItem();
        List<ServicioCosto> elegidos = listaServicios.getSelectedValuesList();
        if (paciente == null || elegidos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Elegí un paciente y por lo menos un servicio.");
            return;
        }

        StringBuilder factura = new StringBuilder("FACTURA\nPaciente: " + paciente + "\n");
        int total = 0;
        for (ServicioCosto s : elegidos) {
            factura.append(s.nombre()).append(" .......... $").append(s.precio()).append('\n');
            total += s.precio();
        }
        factura.append("TOTAL: $").append(total);
        areaFactura.setText(factura.toString());
    }

    /*Getter para que el controlador pueda consultar la selección. */
    public JComboBox<String> getComboPacientes() {
        return comboPacientes;
    }
}