import veterinaria.controlador.VeterinariaController;
import veterinaria.vista.VistaFacturas;
import veterinaria.vista.VistaPacientes;
import veterinaria.vista.VistaServicios;

import javax.swing.*;
import java.awt.*;

/**
 * Clase principal que inicializa el controlador y la interfaz gráfica con CardLayout
 * para la aplicación de la veterinaria.
 *
 * @author Veterinaria App
 * @version 1.0
 */
public class Main {

    /**
     * Punto de entrada principal de la aplicación.
     *
     * @param args argumentos de la línea de comandos
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            VeterinariaController controlador = new VeterinariaController();

            JFrame ventana = new JFrame("Veterinaria - Sistema de Gestión");
            ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            JPanel central = new JPanel(new CardLayout());
            VistaPacientes vistaPacientes = new VistaPacientes(controlador);
            VistaServicios vistaServicios = new VistaServicios(controlador);
            VistaFacturas vistaFacturas = new VistaFacturas(controlador);

            central.add(vistaPacientes, "Pacientes");
            central.add(vistaServicios, "Servicios");
            central.add(vistaFacturas, "Factura");

            ventana.add(central);
            ventana.setMinimumSize(new Dimension(750, 550));
            ventana.setSize(750, 550);
            ventana.setLocationRelativeTo(null);
            ventana.setVisible(true);
        });
    }
}
