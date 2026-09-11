package veterinaria;

import javax.swing.*;
import java.awt.*;

/*Permite registrar y eliminar pacientes de una lista.
Solo se encarga de la interfaz; los datos de ejemplo viven acá.
 */
public class VistaPacientes extends JPanel {

    private final JTextField campoNombre = new JTextField(15);
    private final JTextField campoEspecie = new JTextField(15);
    private final DefaultListModel<String> modeloLista = new DefaultListModel<>();
    private final JList<String> listaPacientes = new JList<>(modeloLista);

    /*Construye la pantalla de pacientes y carga datos de ejemplo. */
    public VistaPacientes() {
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

        cargarDatosDeEjemplo();
    }

    /*Agrega el paciente escrito en el formulario a la lista. */
    private void agregarPaciente() {
        String nombre = campoNombre.getText().trim();
        String especie = campoEspecie.getText().trim();
        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingresá un nombre para el paciente.");
            return;
        }
        modeloLista.addElement(nombre + " (" + (especie.isEmpty() ? "sin especie" : especie) + ")");
        campoNombre.setText("");
        campoEspecie.setText("");
    }

    /*Elimina el paciente seleccionado de la lista. */
    private void eliminarPaciente() {
        int indice = listaPacientes.getSelectedIndex();
        if (indice == -1) {
            JOptionPane.showMessageDialog(this, "Seleccioná un paciente para eliminar.");
            return;
        }
        modeloLista.remove(indice);
    }

    private void cargarDatosDeEjemplo() {
        modeloLista.addElement("Rex (Perro)");
        modeloLista.addElement("Michi (Gato)");
        modeloLista.addElement("Piolín (Canario)");
    }

    /*Getter para que el controlador pueda acceder a la lista. */
    public JList<String> getListaPacientes() {
        return listaPacientes;
    }
}