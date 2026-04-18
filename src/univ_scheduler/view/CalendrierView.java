package univ_scheduler.view;


import univ_scheduler.dao.CreneauDAO;

import univ_scheduler.model.Creneau;
import univ_scheduler.service.ExportExcelservice;
import univ_scheduler.service.ExportPDFService;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Vue du calendrier des creneaux.
 * @author Votre Nom
 * @version 1.0
 */


public class CalendrierView extends JFrame {
    private static final long serialVersionUID = 1L;

    private CreneauDAO        creneauDAO = new CreneauDAO();
    private JTable            tableau;
    private DefaultTableModel modele;

    public CalendrierView() {
        setTitle("UNIV-SCHEDULER — Calendrier");
        setSize(950, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initialiserComposants();
        chargerDonnees();
    }

    private void initialiserComposants() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBackground(new Color(142, 68, 173));
        panelHaut.setPreferredSize(new Dimension(950, 65));
        panelHaut.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JLabel titre = new JLabel("Calendrier des Creneaux");
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setForeground(Color.WHITE);
        panelHaut.add(titre, BorderLayout.WEST);

        JButton btnRetour = new JButton("Retour");
        btnRetour.setBackground(new Color(192, 57, 43));
        btnRetour.setForeground(Color.WHITE);
        btnRetour.setFont(new Font("Arial", Font.BOLD, 13));
        btnRetour.setBorderPainted(false);
        btnRetour.setFocusPainted(false);
        btnRetour.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRetour.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panelHaut.add(btnRetour, BorderLayout.EAST);

        String[] colonnes = {"ID", "Jour", "Heure Debut", "Duree", "ID Cours", "ID Salle"};
        modele  = new DefaultTableModel(colonnes, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tableau = new JTable(modele) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(245, 235, 255));
                }
                return c;
            }
        };
        tableau.setFont(new Font("Arial", Font.PLAIN, 13));
        tableau.setRowHeight(32);
        tableau.setShowGrid(false);
        tableau.setIntercellSpacing(new Dimension(0, 0));
        tableau.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableau.getTableHeader().setBackground(new Color(142, 68, 173));
        tableau.getTableHeader().setForeground(Color.WHITE);
        tableau.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tableau.setSelectionBackground(new Color(142, 68, 173));
        tableau.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tableau);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBas.setBackground(new Color(236, 240, 245));

        JButton btnCalendrierSemaine = new JButton("Vue Semaine");
        btnCalendrierSemaine.setBackground(new Color(41, 128, 185));
        btnCalendrierSemaine.setForeground(Color.WHITE);
        btnCalendrierSemaine.setFont(new Font("Arial", Font.BOLD, 13));
        btnCalendrierSemaine.setBorderPainted(false);
        btnCalendrierSemaine.setFocusPainted(false);
        btnCalendrierSemaine.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnCalendrierSemaine.setPreferredSize(new Dimension(160, 38));
        btnCalendrierSemaine.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                new calendriersemaineView().setVisible(true);
            }
        });
        panelBas.add(btnCalendrierSemaine);

        JButton btnExportPDF = new JButton("Exporter PDF");
        btnExportPDF.setBackground(new Color(192, 57, 43));
        btnExportPDF.setForeground(Color.WHITE);
        btnExportPDF.setFont(new Font("Arial", Font.BOLD, 13));
        btnExportPDF.setBorderPainted(false);
        btnExportPDF.setFocusPainted(false);
        btnExportPDF.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportPDF.setPreferredSize(new Dimension(160, 38));
        btnExportPDF.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exporterPDF();
            }
        });
        panelBas.add(btnExportPDF);

        JButton btnExportExcel = new JButton("Exporter Excel");
        btnExportExcel.setBackground(new Color(39, 174, 96));
        btnExportExcel.setForeground(Color.WHITE);
        btnExportExcel.setFont(new Font("Arial", Font.BOLD, 13));
        btnExportExcel.setBorderPainted(false);
        btnExportExcel.setFocusPainted(false);
        btnExportExcel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnExportExcel.setPreferredSize(new Dimension(160, 38));
        btnExportExcel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exporterExcel();
            }
        });
        panelBas.add(btnExportExcel);

        panelPrincipal.add(panelHaut, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBas, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    private void chargerDonnees() {
        modele.setRowCount(0);
        List<Creneau> creneaux = creneauDAO.listerTous();
        for (Creneau c : creneaux) {
            int heureDebut = c.getHeure_Debut();
            int heures     = heureDebut / 100;
            int minutes    = heureDebut % 100;
            String heure   = String.format("%02d:%02d", heures, minutes);
            modele.addRow(new Object[]{
                c.getId_Creneau(),
                c.getJour().name(),
                heure,
                c.getDuree() + " min",
                c.getId_Cours(),
                c.getId_Salle()
            });
        }
    }

    private void exporterPDF() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer le PDF");
        fileChooser.setSelectedFile(new java.io.File("emploi_du_temps.pdf"));
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String chemin = fileChooser.getSelectedFile().getAbsolutePath();
            ExportPDFService exportService = new ExportPDFService();
            exportService.exporterEmploiDuTemps(chemin);
            JOptionPane.showMessageDialog(this,
                "PDF exporte avec succes !",
                "Export PDF",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private void exporterExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Enregistrer le fichier Excel");
        fileChooser.setSelectedFile(new java.io.File("emploi_du_temps.xlsx"));
        int result = fileChooser.showSaveDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            String chemin = fileChooser.getSelectedFile().getAbsolutePath();
            ExportExcelservice exportService = new ExportExcelservice();
            exportService.exporterEmploiDuTemps(chemin);
            JOptionPane.showMessageDialog(this,
                "Excel exporte avec succes !",
                "Export Excel",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
}