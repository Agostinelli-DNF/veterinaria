package veterinaria.vista;

import javax.swing.*;
import java.awt.*;

/**
 * Barra de navegación superior con botones para alternar entre las vistas
 * de Pacientes, Servicios y Factura mediante CardLayout.
 * 
 * @author Veterinaria App
 * @version 1.0
 */
public class BarraNavegacion extends JPanel {

    private static final String[] NOMBRES = {"Pacientes", "Servicios", "Factura"};

    /**
     * Construye la barra de navegación creando los botones correspondientes.
     */
    public BarraNavegacion() {
        setLayout(new FlowLayout(FlowLayout.LEFT, 0, 0));
        for (String nombre : NOMBRES) {
            JButton boton = new JButton(nombre);
            boton.addActionListener(e -> navegar(nombre));
            add(boton);
        }
    }

    /**
     * Busca el contenedor con CardLayout en la jerarquía superior y muestra la vista solicitada.
     * 
     * @param destino nombre de la vista a mostrar
     */
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
