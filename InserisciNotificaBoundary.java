package org.uninabiogarden.oobd68.boundary;

import org.uninabiogarden.oobd68.controller.Controller;
import org.uninabiogarden.oobd68.entity.Messaggio;

import javax.swing.*;
import java.awt.*;

public class InserisciNotificaBoundary extends JFrame {

    private Controller controller;
    private JTextArea txtMessaggio;
    private JComboBox<Messaggio> comboTipo;
    private JTextField txtIdLotto;
    private JComboBox<String> comboDestinatario;
    private JTextField txtIdSingolo;

    public InserisciNotificaBoundary(Controller controller) {
        this.controller = controller;

        setTitle("Invia Nuova Notifica - Unina BioGarden");
        setSize(450, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelInput = new JPanel(new GridLayout(5, 2, 5, 5));
        panelInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelInput.add(new JLabel("ID Lotto Riferimento:"));
        txtIdLotto = new JTextField();
        panelInput.add(txtIdLotto);

        panelInput.add(new JLabel("Tipo Segnalazione:"));
        comboTipo = new JComboBox<>(Messaggio.values());
        panelInput.add(comboTipo);

        panelInput.add(new JLabel("Destinatari:"));
        String[] opzioni = {"Tutti i Collaboratori", "Singolo Coltivatore (ID)"};
        comboDestinatario = new JComboBox<>(opzioni);
        panelInput.add(comboDestinatario);

        panelInput.add(new JLabel("ID Coltivatore (se singolo):"));
        txtIdSingolo = new JTextField();
        txtIdSingolo.setEnabled(false);
        panelInput.add(txtIdSingolo);

        comboDestinatario.addActionListener(e -> {
            if (comboDestinatario.getSelectedIndex() == 1) {
                txtIdSingolo.setEnabled(true);
                txtIdSingolo.setBackground(Color.WHITE);
            } else {
                txtIdSingolo.setEnabled(false);
                txtIdSingolo.setBackground(new Color(230, 230, 230));
                txtIdSingolo.setText("");
            }
        });

        add(panelInput, BorderLayout.NORTH);

        JPanel panelText = new JPanel(new BorderLayout());
        panelText.setBorder(BorderFactory.createTitledBorder("Contenuto Messaggio"));
        txtMessaggio = new JTextArea(5, 20);
        panelText.add(new JScrollPane(txtMessaggio), BorderLayout.CENTER);

        JPanel containerText = new JPanel(new BorderLayout());
        containerText.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));
        containerText.add(panelText, BorderLayout.CENTER);
        add(containerText, BorderLayout.CENTER);

        JButton btnInvia = new JButton("INVIA NOTIFICA");
        btnInvia.setBackground(new Color(255, 140, 0));
        btnInvia.setForeground(Color.WHITE);
        btnInvia.setFont(new Font("Arial", Font.BOLD, 14));

        JPanel panelBtn = new JPanel(new BorderLayout());
        panelBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBtn.add(btnInvia, BorderLayout.CENTER);
        add(panelBtn, BorderLayout.SOUTH);

        btnInvia.addActionListener(e -> inviaNotificaAction());
    }

    private void inviaNotificaAction() {
        try {
            String idLottoStr = txtIdLotto.getText().trim();
            if(idLottoStr.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Inserisci l'ID del Lotto.");
                return;
            }
            int idLotto = Integer.parseInt(idLottoStr);

            String testo = txtMessaggio.getText().trim();
            if (testo.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Scrivi un messaggio.");
                return;
            }

            Messaggio tipo = (Messaggio) comboTipo.getSelectedItem();

            int idTarget = 0;
            if (comboDestinatario.getSelectedIndex() == 1) {
                String idTargetStr = txtIdSingolo.getText().trim();
                if(idTargetStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Inserisci l'ID del coltivatore.");
                    return;
                }
                idTarget = Integer.parseInt(idTargetStr);
            }

            controller.InviaNotificaManuale(testo, tipo, idLotto, idTarget);

            JOptionPane.showMessageDialog(this, "Notifica inviata con successo!");
            dispose();

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Gli ID devono essere numeri interi.", "Errore", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Errore generico: " + ex.getMessage());
        }
    }
}
