package edu.ucam.cliente;

import javax.swing.SwingUtilities;
import edu.ucam.cliente.gui.VentanaInicio;

public class MainGUI {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new VentanaInicio();
        });
    }
}
