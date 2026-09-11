import veterinaria.VistaFacturas;
import veterinaria.VistaPacientes;
import veterinaria.VistaServicios;

import javax.swing.*;
import java.awt.*;

public class Main {

    public static void main(String[] args) {
        //Lo puedo crear acá pero no me gusta crear cosas en el main xd
        SwingUtilities.invokeLater(() -> {
            JFrame ventana = new JFrame("Veterinaria");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel central = new JPanel(new CardLayout());
            central.add(new VistaPacientes(), "Pacientes");
            central.add(new VistaServicios(), "Servicios");
            central.add(new VistaFacturas(), "Factura");

            ventana.add(central);
            ventana.setMinimumSize(new java.awt.Dimension(700, 500));
            ventana.setSize(700, 500);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}