package org.uninabiogarden.oobd68.boundary;

import org.uninabiogarden.oobd68.controller.Controller;

import javax.swing.*;
import java.awt.*;

public class MenuPrincipaleBoundary extends JFrame {

    private Controller controller;

    public MenuPrincipaleBoundary(Controller controller) {
        this.controller = controller;

        setTitle("Menu Principale - Unina BioGarden");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setLayout(new GridLayout(5, 1, 10, 10));

        JLabel lblTitolo = new JLabel("SELEZIONA UN'OPERAZIONE", SwingConstants.CENTER);
        lblTitolo.setFont(new Font("Arial", Font.BOLD, 14));
        add(lblTitolo);

        JButton btnCrea = new JButton("CREA PROGETTO STAGIONALE");

        btnCrea.addActionListener(e -> {
            new CreaProgettoBoundary(controller).setVisible(true);
        });
        add(btnCrea);

        JButton btnVisualizza = new JButton("VISUALIZZA STATO PROGETTI");
        btnVisualizza.addActionListener(e -> {
            new VisualizzaProgettoBoundary(controller).setVisible(true);
        });
        add(btnVisualizza);

        JButton btnNotifica = new JButton("INVIA NOTIFICA / SEGNALAZIONE");
        btnNotifica.setForeground(new Color(0, 100, 0));
        btnNotifica.addActionListener(e -> {
            new InserisciNotificaBoundary(controller).setVisible(true);
        });
        add(btnNotifica);

        JButton btnReport = new JButton("REPORT STATISTICO LOTTO");
        btnReport.setForeground(new Color(0, 0, 139));
        btnReport.addActionListener(e -> {
            String input = JOptionPane.showInputDialog(this, "Inserisci ID Lotto per il report:");
            if (input != null && !input.isEmpty()) {
                try {
                    int idLotto = Integer.parseInt(input);
                    controller.visualizzaReport(idLotto);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(this, "ID non valido.");
                }
            }
        });
        add(btnReport);
        JButton btnEsci = new JButton("ESCI");
        btnEsci.setBackground(new Color(255, 100, 100));
        btnEsci.addActionListener(e -> {
            System.out.println("Chiusura applicazione.");
            System.exit(0);
        });
        add(btnEsci);
    }

}
