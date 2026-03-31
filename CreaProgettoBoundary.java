package org.uninabiogarden.oobd68.boundary;

import org.uninabiogarden.oobd68.controller.Controller;
import org.uninabiogarden.oobd68.entity.Coltura;
import org.uninabiogarden.oobd68.entity.Lotto;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class CreaProgettoBoundary extends JFrame {

    private Controller controller;
    private JTextField txtIdLotto, txtSuperficie, txtOrtaggio;
    private DefaultListModel<String> listModel;
    private List<Coltura> listaColtureTemp;

    public CreaProgettoBoundary(Controller controller) {
        this.controller = controller;
        this.listaColtureTemp = new ArrayList<>();

        setTitle("Crea Nuovo Progetto - Unina BioGarden");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelInput = new JPanel(new GridLayout(4, 2, 5, 5));
        panelInput.setBorder(BorderFactory.createEmptyBorder(10, 10, 0, 10));

        panelInput.add(new JLabel("ID Lotto (Numerico):"));
        txtIdLotto = new JTextField();
        panelInput.add(txtIdLotto);

        panelInput.add(new JLabel("Superficie (mq):"));
        txtSuperficie = new JTextField();
        panelInput.add(txtSuperficie);

        panelInput.add(new JLabel("Aggiungi Ortaggio (es. Pomodoro):"));
        JPanel panelOrtaggio = new JPanel(new BorderLayout());
        txtOrtaggio = new JTextField();
        JButton btnAddOrtaggio = new JButton("AGGIUNGI");
        panelOrtaggio.add(txtOrtaggio, BorderLayout.CENTER);
        panelOrtaggio.add(btnAddOrtaggio, BorderLayout.EAST);
        panelInput.add(panelOrtaggio);

        add(panelInput, BorderLayout.NORTH);

        listModel = new DefaultListModel<>();
        JList<String> listaVisuale = new JList<>(listModel);
        listaVisuale.setBorder(BorderFactory.createTitledBorder("Colture Selezionate:"));
        add(new JScrollPane(listaVisuale), BorderLayout.CENTER);

        JButton btnConferma = new JButton("SALVA PROGETTO");
        btnConferma.setBackground(new Color(100, 200, 100));
        btnConferma.setFont(new Font("Arial", Font.BOLD, 14));
        JPanel panelBtn = new JPanel(new BorderLayout());
        panelBtn.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelBtn.add(btnConferma, BorderLayout.CENTER);
        add(panelBtn, BorderLayout.SOUTH);

        btnAddOrtaggio.addActionListener(e -> {
            String nome = txtOrtaggio.getText().trim();
            if (!nome.isEmpty()) {
                Coltura c = new Coltura();
                c.setTipoOrtaggio(nome);

                listaColtureTemp.add(c);
                listModel.addElement(nome);

                txtOrtaggio.setText("");
            }
        });

        btnConferma.addActionListener(e -> {
            try {
                Lotto lotto = new Lotto();
                lotto.setIdLotto(Integer.parseInt(txtIdLotto.getText()));
                lotto.setSuperficie(Double.parseDouble(txtSuperficie.getText()));

                if (listaColtureTemp.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Inserisci almeno una coltura!", "Attenzione", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                boolean esito = controller.CreaProgettoStagionale(lotto, listaColtureTemp);

                if (esito) {
                    JOptionPane.showMessageDialog(this, "Progetto creato con successo!");
                    dispose(); // Chiude la finestra
                } else {
                    JOptionPane.showMessageDialog(this, "Errore: Terreno non adatto o Lotto non valido.", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Inserisci valori numerici validi per ID e Superficie.", "Errore Input", JOptionPane.WARNING_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore generico: " + ex.getMessage(), "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}
