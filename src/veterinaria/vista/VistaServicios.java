package veterinaria.vista;

import veterinaria.controlador.VeterinariaController;
import veterinaria.modelo.Servicio;

import javax.swing.*;
import java.awt.*;

/**
 * Vista encargada de la interfaz gráfica para gestionar los servicios veterinarios
 * agrupados por categorías, delegando las operaciones al controlador.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class VistaServicios extends JPanel {

    private final VeterinariaController controlador;
    private final JComboBox<String> comboTipos = new JComboBox<>();
    private final DefaultListModel<Servicio> modeloLista = new DefaultListModel<>();
    private final JList<Servicio> listaServicios = new JList<>(modeloLista);
    private final JTextField campoNombre = new JTextField(12);
    private final JTextField campoPrecio = new JTextField(6);

    /**
     * Construye la pantalla de servicios asociándola al controlador.
     * 
     * @param controlador el controlador principal de la aplicación
     */
    public VistaServicios(VeterinariaController controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());
        add(new BarraNavegacion(), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel superior = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        superior.add(new JLabel("Tipo:"));
        superior.add(comboTipos);
        superior.add(new JLabel("Servicio:"));
        superior.add(campoNombre);
        superior.add(new JLabel("Precio ($):"));
        superior.add(campoPrecio);

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

        // Cargar tipos en el combo
        for (String tipo : controlador.getTiposServicios()) {
            comboTipos.addItem(tipo);
        }

        comboTipos.addActionListener(e -> actualizarLista());
        actualizarLista();
    }

    /**
     * Actualiza la lista visual de servicios según el tipo seleccionado en el combo.
     */
    public void actualizarLista() {
        String tipo = (String) comboTipos.getSelectedItem();
        modeloLista.clear();
        if (tipo != null) {
            for (Servicio s : controlador.getServiciosPorTipo(tipo)) {
                modeloLista.addElement(s);
            }
        }
    }

    /**
     * Captura los datos del formulario y solicita al controlador agregar el servicio.
     */
    private void agregarServicio() {
        String tipo = (String) comboTipos.getSelectedItem();
        String nombre = campoNombre.getText().trim();
        String precioTexto = campoPrecio.getText().trim();

        if (tipo == null) {
            return;
        }
        if (nombre.isEmpty() || precioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Completá el nombre y el precio del servicio.");
            return;
        }

        try {
            int precio = Integer.parseInt(precioTexto);
            if (precio < 0) {
                JOptionPane.showMessageDialog(this, "El precio no puede ser negativo.");
                return;
            }
            controlador.agregarServicio(tipo, nombre, precio);
            campoNombre.setText("");
            campoPrecio.setText("");
            actualizarLista();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número entero válido.");
        }
    }

    /**
     * Solicita al controlador eliminar el servicio seleccionado de la categoría actual.
     */
    private void eliminarServicio() {
        int indice = listaServicios.getSelectedIndex();
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un servicio para eliminar.");
            return;
        }
        String tipo = (String) comboTipos.getSelectedItem();
        controlador.eliminarServicio(tipo, indice);
        actualizarLista();
    }

    /**
     * Obtiene el JList de servicios.
     * 
     * @return lista visual de servicios
     */
    public JList<Servicio> getListaServicios() {
        return listaServicios;
    }
}
