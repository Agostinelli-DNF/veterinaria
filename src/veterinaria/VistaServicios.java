package veterinaria;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

/*Un JComboBox de tipos de servicio decide que servicios se muestran en el JList.
 */
public class VistaServicios extends JPanel {

    private final JComboBox<String> comboTipos = new JComboBox<>();
    private final DefaultListModel<String> modeloLista = new DefaultListModel<>();
    private final JList<String> listaServicios = new JList<>(modeloLista);
    private final JTextField campoNombre = new JTextField(15);

    private final Map<String, java.util.List<String>> datos =
            new LinkedHashMap<>();

    /*Construye la pantalla de servicios y carga los datos de ejemplo. */
    public VistaServicios() {
        setLayout(new BorderLayout());
        add(new BarraNavegacion(), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        cargarDatos();

        JPanel superior = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        superior.add(new JLabel("Tipo:"));
        superior.add(comboTipos);
        superior.add(new JLabel("Nuevo servicio:"));
        superior.add(campoNombre);

        JButton botonAgregar = new JButton("Agregar");
        JButton botonEliminar = new JButton("Eliminar");
        botonAgregar.addActionListener(e -> agregarServicio());
        botonEliminar.addActionListener(e -> eliminarServicio());
        superior.add(botonAgregar);
        superior.add(botonEliminar);

        JScrollPane scroll = new JScrollPane(listaServicios);
        scroll.setBorder(BorderFactory.createTitledBorder("Servicios"));

        contenido.add(superior, BorderLayout.NORTH);
        contenido.add(scroll, BorderLayout.CENTER);

        add(contenido, BorderLayout.CENTER);

        //El combo decide, la lista muestra (patrón del material).
        datos.keySet().forEach(comboTipos::addItem);
        comboTipos.addActionListener(e -> actualizarLista());
        actualizarLista();
    }

    private void cargarDatos() {
        datos.put("Consulta", java.util.List.of("Consulta general", "Control de salud"));
        datos.put("Vacunación", java.util.List.of("Antirrábica", "Quíntuple"));
        datos.put("Cirugía", java.util.List.of("Castración", "Limpieza dental"));
        datos.put("Estética", java.util.List.of("Baño", "Corte de uñas"));
    }

    private void actualizarLista() {
        String tipo = (String) comboTipos.getSelectedItem();
        modeloLista.clear();
        datoDe(tipo).forEach(modeloLista::addElement);
    }

    /*Agrega un servicio nuevo al tipo seleccionado. */
    private void agregarServicio() {
        String tipo = (String) comboTipos.getSelectedItem();
        String nombre = campoNombre.getText().trim();
        if (tipo == null) {
            return;
        }
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Escribí el nombre del servicio.");
            return;
        }
        java.util.List<String> servicios = new java.util.ArrayList<>(datoDe(tipo));
        servicios.add(nombre);
        datos.put(tipo, servicios);
        campoNombre.setText("");
        actualizarLista();
    }

    /*Elimina el servicio seleccionado de la lista. */
    private void eliminarServicio() {
        int indice = listaServicios.getSelectedIndex();
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un servicio para eliminar.");
            return;
        }
        String tipo = (String) comboTipos.getSelectedItem();
        java.util.List<String> servicios = new java.util.ArrayList<>(datoDe(tipo));
        servicios.remove(indice);
        datos.put(tipo, servicios);
        actualizarLista();
    }

    private java.util.List<String> datoDe(String tipo) {
        return datos.getOrDefault(tipo, java.util.List.of());
    }

    /*Getter para que el controlador pueda escuchar el combo. */
    public JComboBox<String> getComboTipos() {
        return comboTipos;
    }

    /*Getter para que el controlador pueda repoblar la lista. */
    public DefaultListModel<String> getModeloLista() {
        return modeloLista;
    }
}