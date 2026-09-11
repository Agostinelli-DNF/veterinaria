package veterinaria;

import javax.swing.*;
import java.awt.*;

/* botones.. Pacientes  Servicios  Factura.
 */
public class BarraNavegacion extends JPanel {

    private static final String[] NOMBRES = {"Pacientes", "Servicios", "Factura"};

    /*Construye la barra de navegación. */
    public BarraNavegacion() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        for (String nombre : NOMBRES) {
            JButton boton = new JButton(nombre);
            boton.addActionListener(e -> navegar(nombre));
            add(boton);
        }
    }

    /*Busca el contenedor con CardLayout y muestra la vista. */
    private void navegar(String destino) {
        Container contenedor = getParent();
        while (contenedor != null && !(contenedor.getLayout() instanceof CardLayout)) {
            contenedor = contenedor.getParent();
        }
        if (contenedor != null) {
            ((CardLayout) contenedor.getLayout()).show(contenedor, destino);
        }
    }
}