package veterinaria.vista;

import veterinaria.controlador.VeterinariaController;
import veterinaria.excepcion.DaoException;
import veterinaria.modelo.Servicio;

import javax.swing.*;
import java.awt.*;

/**
 * Vista encargada de la interfaz gráfica para gestionar, modificar y eliminar servicios veterinarios,
 * interactuando con el controlador y manejando excepciones de persistencia.
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
        JButton botonModificar = new JButton("Modificar");
        JButton botonEliminar = new JButton("Eliminar");

        botonAgregar.addActionListener(e -> agregarServicio());
        botonModificar.addActionListener(e -> modificarServicio());
        botonEliminar.addActionListener(e -> eliminarServicio());

        superior.add(botonAgregar);
        superior.add(botonModificar);
        superior.add(botonEliminar);

        JScrollPane scroll = new JScrollPane(listaServicios);
        scroll.setBorder(BorderFactory.createTitledBorder("Servicios (Base de Datos)"));

        contenido.add(superior, BorderLayout.NORTH);
        contenido.add(scroll, BorderLayout.CENTER);

        add(contenido, BorderLayout.CENTER);

        try {
            for (String tipo : controlador.getTiposServicios()) {
                comboTipos.addItem(tipo);
            }
        } catch (DaoException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tipos de servicios: " + e.getMessage(),
                    "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }

        comboTipos.addActionListener(e -> actualizarLista());

        // Al seleccionar un servicio, cargar sus datos en los campos
        listaServicios.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Servicio seleccionado = listaServicios.getSelectedValue();
                if (seleccionado != null) {
                    campoNombre.setText(seleccionado.getNombre());
                    campoPrecio.setText(String.valueOf(seleccionado.getPrecio()));
                }
            }
        });

        actualizarLista();
    }

    /**
     * Actualiza la lista visual de servicios según el tipo seleccionado.
     */
    public void actualizarLista() {
        String tipo = (String) comboTipos.getSelectedItem();
        modeloLista.clear();
        if (tipo != null) {
            try {
                for (Servicio s : controlador.getServiciosPorTipo(tipo)) {
                    modeloLista.addElement(s);
                }
                campoNombre.setText("");
                campoPrecio.setText("");
            } catch (DaoException e) {
                JOptionPane.showMessageDialog(this, "Error al cargar servicios: " + e.getMessage(),
                        "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
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
            actualizarLista();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número entero válido.");
        } catch (DaoException e) {
            JOptionPane.showMessageDialog(this, "Error al guardar servicio: " + e.getMessage(),
                    "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Modifica el servicio seleccionado con los datos de los campos de texto.
     */
    private void modificarServicio() {
        Servicio seleccionado = listaServicios.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccioná un servicio de la lista para modificar.");
            return;
        }
        String tipo = (String) comboTipos.getSelectedItem();
        String nombre = campoNombre.getText().trim();
        String precioTexto = campoPrecio.getText().trim();

        if (nombre.isEmpty() || precioTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El nombre y el precio no pueden estar vacíos.");
            return;
        }

        try {
            int precio = Integer.parseInt(precioTexto);
            if (precio < 0) {
                JOptionPane.showMessageDialog(this, "El precio no puede ser negativo.");
                return;
            }
            controlador.actualizarServicio(seleccionado.getId(), tipo, nombre, precio);
            actualizarLista();
            JOptionPane.showMessageDialog(this, "Servicio modificado con éxito.");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "El precio debe ser un número entero válido.");
        } catch (DaoException e) {
            JOptionPane.showMessageDialog(this, "Error al modificar servicio: " + e.getMessage(),
                    "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Solicita al controlador eliminar el servicio seleccionado por su ID.
     */
    private void eliminarServicio() {
        Servicio seleccionado = listaServicios.getSelectedValue();
        if (seleccionado == null) {
            JOptionPane.showMessageDialog(this, "Seleccioná un servicio para eliminar.");
            return;
        }
        try {
            controlador.eliminarServicio(seleccionado.getId());
            actualizarLista();
        } catch (DaoException e) {
            JOptionPane.showMessageDialog(this, "Error al eliminar servicio: " + e.getMessage(),
                    "Error de Base de Datos", JOptionPane.ERROR_MESSAGE);
        }
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
