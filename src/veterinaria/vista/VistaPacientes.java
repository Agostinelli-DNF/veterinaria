package veterinaria.vista;

import veterinaria.controlador.VeterinariaController;
import veterinaria.modelo.Paciente;

import javax.swing.*;
import java.awt.*;

/**
 * Vista encargada de la interfaz gráfica para registrar y eliminar pacientes,
 * delegando la lógica de datos al controlador.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class VistaPacientes extends JPanel {

    private final VeterinariaController controlador;
    private final JTextField campoNombre = new JTextField(15);
    private final JTextField campoEspecie = new JTextField(15);
    private final DefaultListModel<Paciente> modeloLista = new DefaultListModel<>();
    private final JList<Paciente> listaPacientes = new JList<>(modeloLista);

    /**
     * Construye la pantalla de pacientes asociándola al controlador.
     * 
     * @param controlador el controlador principal de la aplicación
     */
    public VistaPacientes(VeterinariaController controlador) {
        this.controlador = controlador;
        setLayout(new BorderLayout());
        add(new BarraNavegacion(), BorderLayout.NORTH);

        JPanel contenido = new JPanel(new BorderLayout(10, 10));
        contenido.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JPanel formulario = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        formulario.add(new JLabel("Nombre:"));
        formulario.add(campoNombre);
        formulario.add(new JLabel("Especie:"));
        formulario.add(campoEspecie);

        JButton botonAgregar = new JButton("Agregar");
        JButton botonEliminar = new JButton("Eliminar");
        botonAgregar.addActionListener(e -> agregarPaciente());
        botonEliminar.addActionListener(e -> eliminarPaciente());
        formulario.add(botonAgregar);
        formulario.add(botonEliminar);

        JScrollPane scroll = new JScrollPane(listaPacientes);
        scroll.setBorder(BorderFactory.createTitledBorder("Pacientes"));

        contenido.add(formulario, BorderLayout.NORTH);
        contenido.add(scroll, BorderLayout.CENTER);

        add(contenido, BorderLayout.CENTER);

        actualizarLista();
    }

    /**
     * Actualiza el modelo de la lista visual con los pacientes del controlador.
     */
    public void actualizarLista() {
        modeloLista.clear();
        for (Paciente p : controlador.getListaPacientes()) {
            modeloLista.addElement(p);
        }
    }

    /**
     * Captura los datos del formulario y solicita al controlador agregar el paciente.
     */
    private void agregarPaciente() {
        String nombre = campoNombre.getText().trim();
        String especie = campoEspecie.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresá un nombre para el paciente.");
            return;
        }
        controlador.agregarPaciente(nombre, especie);
        campoNombre.setText("");
        campoEspecie.setText("");
        actualizarLista();
    }

    /**
     * Solicita al controlador eliminar el paciente seleccionado en la lista.
     */
    private void eliminarPaciente() {
        int indice = listaPacientes.getSelectedIndex();
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un paciente para eliminar.");
            return;
        }
        controlador.eliminarPaciente(indice);
        actualizarLista();
    }

    /**
     * Obtiene el componente JList de pacientes.
     * 
     * @return lista visual de pacientes
     */
    public JList<Paciente> getListaPacientes() {
        return listaPacientes;
    }
}
