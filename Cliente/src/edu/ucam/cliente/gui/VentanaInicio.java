package edu.ucam.cliente.gui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import edu.ucam.cliente.ClienteERP;

public class VentanaInicio extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtPassword;
    private ClienteERP cliente;

    public VentanaInicio() {

        try {
            cliente = new ClienteERP();
        } catch (IOException e) {
            JOptionPane.showMessageDialog(
                this,
                "No se pudo conectar con el servidor.\n" + e.getMessage(),
                "Error de conexión",
                JOptionPane.ERROR_MESSAGE
            );
            System.exit(1);
            return;
        }

        setTitle("Login - Cliente ERP");
        setSize(350, 200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 2, 10, 10));

        add(new JLabel("Usuario:"));
        txtUsuario = new JTextField();
        add(txtUsuario);

        add(new JLabel("Password:"));
        txtPassword = new JPasswordField();
        add(txtPassword);

        JButton btnLogin = new JButton("Entrar");
        add(new JLabel());
        add(btnLogin);

        btnLogin.addActionListener(e -> autenticar());

        setVisible(true);
    }

    private void autenticar() {
        String user = txtUsuario.getText();
        String pass = new String(txtPassword.getPassword());

        try {
            if (cliente.autenticar(user, pass)) {
                dispose();
                new VentanaPrincipal(cliente);
            } else {
                JOptionPane.showMessageDialog(
                    this,
                    "Credenciales incorrectas",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(
                this,
                "Error de comunicación con el servidor:\n" + ex.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE
            );
        }
    }
}
