package org.uninabiogarden.oobd68.boundary;

import org.uninabiogarden.oobd68.controller.Controller;
import org.uninabiogarden.oobd68.entity.Utente;

import javax.swing.*;
import java.awt.*;

public class LoginBoundary extends JFrame {

    private Controller controller;
    private JTextField userField;
    private JPasswordField passField;

    public LoginBoundary() {
        super("Login - Unina BioGarden");

        this.controller = new Controller();

        setSize(350, 250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(4, 1, 10, 10));

        JLabel titleLabel = new JLabel("ACCESSO SISTEMA", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        add(titleLabel);

        JPanel userPanel = new JPanel();
        userPanel.add(new JLabel("Username:"));
        userField = new JTextField(15);
        userPanel.add(userField);
        add(userPanel);

        JPanel passPanel = new JPanel();
        passPanel.add(new JLabel("Password:"));
        passField = new JPasswordField(15);
        passPanel.add(passField);
        add(passPanel);

        JPanel btnPanel = new JPanel();
        JButton btnLogin = new JButton("ACCEDI");
        btnPanel.add(btnLogin);
        add(btnPanel);

        btnLogin.addActionListener(e -> eseguiLogin());
    }

    private void eseguiLogin() {
        String user = userField.getText();
        String pass = new String(passField.getPassword());

        System.out.println(user + pass);
        Utente utente = controller.PublicUtenteNome(user, pass);

        if (utente != null) {
            JOptionPane.showMessageDialog(this, "Benvenuto " + utente.getNome());

            new MenuPrincipaleBoundary(controller).setVisible(true);

            this.dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Credenziali Errate! Riprova.", "Errore Accesso", JOptionPane.ERROR_MESSAGE);
        }
    }
}
