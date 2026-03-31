package org.uninabiogarden.oobd68.boundary;

import org.uninabiogarden.oobd68.entity.ReportDati;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class ReportBoundary extends JFrame {

    public ReportBoundary(List<ReportDati> dati, int idLotto) {
        setTitle("Report Raccolti - Lotto " + idLotto);
        setSize(900, 700);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        String[] colonne = {"Coltura", "N. Raccolte", "Media (kg)", "Min (kg)", "Max (kg)"};
        DefaultTableModel tableModel = new DefaultTableModel(colonne, 0);

        for (ReportDati d : dati) {
            dataset.addValue(d.getMedia(), "Media Raccolto", d.getNomeColtura());
            dataset.addValue(d.getMax(), "Max Raccolto", d.getNomeColtura());

            Object[] riga = {
                    d.getNomeColtura(),
                    d.getNumeroRaccolte(),
                    String.format("%.2f", d.getMedia()),
                    d.getMin(),
                    d.getMax()
            };
            tableModel.addRow(riga);
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Statistiche Raccolta per Coltura",
                "Tipo di Coltura",
                "Quantità (kg)",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false
        );

        ChartPanel chartPanel = new ChartPanel(barChart);
        chartPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        chartPanel.setBackground(Color.WHITE);

        JTable table = new JTable(tableModel);
        JScrollPane tableScroll = new JScrollPane(table);
        tableScroll.setPreferredSize(new Dimension(800, 150));
        tableScroll.setBorder(BorderFactory.createTitledBorder("Dettaglio Numerico"));

        add(chartPanel, BorderLayout.CENTER);
        add(tableScroll, BorderLayout.SOUTH);
    }
}
