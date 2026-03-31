package org.uninabiogarden.oobd68.boundary;

import org.uninabiogarden.oobd68.controller.Controller;
import org.uninabiogarden.oobd68.entity.Attivita;
import org.uninabiogarden.oobd68.entity.Categoria;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class VisualizzaProgettoBoundary extends JFrame {

    private Controller controller;
    private JTextField txtIdProgetto;
    private JTable tabella;
    private DefaultTableModel tableModel;

    public VisualizzaProgettoBoundary(Controller controller) {
        this.controller = controller;

        setTitle("Stato Progetto - Unina BioGarden");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        JPanel panelSearch = new JPanel();
        panelSearch.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        panelSearch.add(new JLabel("Inserisci ID Coltivazione:"));
        txtIdProgetto = new JTextField(10);
        panelSearch.add(txtIdProgetto);

        JButton btnCerca = new JButton("VISUALIZZA STATO");
        btnCerca.setBackground(new Color(100, 150, 255));
        btnCerca.setForeground(Color.WHITE);
        panelSearch.add(btnCerca);

        add(panelSearch, BorderLayout.NORTH);

        String[] colonne = {"ID Attività", "Tipo Operazione", "Stato", "Data Inizio", "Data Fine/Prevista"};

        tableModel = new DefaultTableModel(colonne, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tabella = new JTable(tableModel);
        tabella.setRowHeight(25);
        add(new JScrollPane(tabella), BorderLayout.CENTER);

        btnCerca.addActionListener(e -> {
            try {
                String input = txtIdProgetto.getText().trim();
                if (input.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Inserisci un numero ID.", "Errore", JOptionPane.WARNING_MESSAGE);
                    return;
                }

                int id = Integer.parseInt(input);

                List<Attivita> listaAttivita = controller.RecuperaAttivitaPerProgetto(id);

                tableModel.setRowCount(0);

                if (listaAttivita == null || listaAttivita.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Nessuna attività trovata per il progetto ID: " + id);
                } else {
                    for (Attivita a : listaAttivita) {

                        String tipo = "Generico";
                        String dataInizio = "N/D";
                        String dataFine = "N/D";

                        if (a.getTipoCategoria() != null) {
                            tipo = a.getTipoCategoria().toString();

                            if (a.getTipoCategoria() == Categoria.SEMINA) {
                                dataInizio = String.valueOf(a.getinizioSemina());
                                dataFine = String.valueOf(a.getfineSemina());
                            } else if (a.getTipoCategoria() == Categoria.RACCOLTA) {
                                dataInizio = String.valueOf(a.getinizioRaccolta());
                                dataFine = String.valueOf(a.getfineRaccolta());
                            } else if (a.getTipoCategoria() == Categoria.IRRIGAZIONE) {
                                dataInizio = String.valueOf(a.getinizioIrrigazione());
                                dataFine = String.valueOf(a.getfineIrrigazione());
                            }
                        }

                        Object[] riga = {
                                a.getidAttivita(),
                                tipo,
                                a.getTipoStato(),
                                dataInizio,
                                dataFine
                        };
                        tableModel.addRow(riga);
                    }
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "L'ID deve essere un numero intero.", "Errore Input", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Errore durante la ricerca: " + ex.getMessage());
            }
        });
    }
}
