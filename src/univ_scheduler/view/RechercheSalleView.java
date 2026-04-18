package univ_scheduler.view;


import univ_scheduler.model.Salle;

import univ_scheduler.model.Salle.typeSalle;
import univ_scheduler.model.Utilisateur;
import univ_scheduler.service.RechercheSalleService;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Vue de gestion des notifications.
 * @author Votre Nom
 * @version 1.0
 */

public class RechercheSalleView extends JFrame {
    private static final long serialVersionUID = 1L;

  
    private RechercheSalleService rechercheService = new RechercheSalleService();
    private JTable                tableau;
    private DefaultTableModel     modele;

    private JComboBox<String> champJour;
    private JTextField        champHeure;
    private JTextField        champDuree;
    private JComboBox<String> champType;
    private JTextField        champCapacite;

    public RechercheSalleView(Utilisateur utilisateur) {
        setTitle("UNIV-SCHEDULER — Recherche de Salles");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initialiserComposants();
    }

    private void initialiserComposants() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBackground(new Color(22, 160, 133));
        panelHaut.setPreferredSize(new Dimension(900, 65));
        panelHaut.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JLabel titre = new JLabel("Recherche de Salles Disponibles");
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

        JPanel panelFiltre = new JPanel(new GridLayout(2, 6, 10, 10));
        panelFiltre.setBackground(new Color(236, 240, 245));
        panelFiltre.setBorder(BorderFactory.createEmptyBorder(15, 20, 15, 20));

        panelFiltre.add(new JLabel("Jour :"));
        panelFiltre.add(new JLabel("Heure debut :"));
        panelFiltre.add(new JLabel("Duree (h) :"));
        panelFiltre.add(new JLabel("Type :"));
        panelFiltre.add(new JLabel("Capacite min :"));
        panelFiltre.add(new JLabel(""));

        String[] jours = {"LUNDI", "MARDI", "MERCREDI", "JEUDI", "VENDREDI", "SAMEDI"};
        champJour = new JComboBox<String>(jours);
        champJour.setBackground(Color.WHITE);

        champHeure    = new JTextField("8");
        champDuree    = new JTextField("2");

        String[] types = {"Tous", "TD", "TP", "AMPHI"};
        champType = new JComboBox<String>(types);
        champType.setBackground(Color.WHITE);

        champCapacite = new JTextField("0");

        JButton btnRechercher = new JButton("Rechercher");
        btnRechercher.setBackground(new Color(22, 160, 133));
        btnRechercher.setForeground(Color.WHITE);
        btnRechercher.setFont(new Font("Arial", Font.BOLD, 13));
        btnRechercher.setBorderPainted(false);
        btnRechercher.setFocusPainted(false);
        btnRechercher.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnRechercher.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                rechercher();
            }
        });

        panelFiltre.add(champJour);
        panelFiltre.add(champHeure);
        panelFiltre.add(champDuree);
        panelFiltre.add(champType);
        panelFiltre.add(champCapacite);
        panelFiltre.add(btnRechercher);

        String[] colonnes = {"ID", "Numero", "Capacite", "Type", "ID Batiment"};
        modele  = new DefaultTableModel(colonnes, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tableau = new JTable(modele) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(230, 255, 250));
                }
                return c;
            }
        };
        tableau.setFont(new Font("Arial", Font.PLAIN, 13));
        tableau.setRowHeight(32);
        tableau.setShowGrid(false);
        tableau.setIntercellSpacing(new Dimension(0, 0));
        tableau.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableau.getTableHeader().setBackground(new Color(22, 160, 133));
        tableau.getTableHeader().setForeground(Color.WHITE);
        tableau.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tableau.setSelectionBackground(new Color(22, 160, 133));
        tableau.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tableau);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel panelInfo = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        panelInfo.setBackground(new Color(236, 240, 245));
        JLabel labelInfo = new JLabel("Entrez les criteres et cliquez Rechercher");
        labelInfo.setFont(new Font("Arial", Font.ITALIC, 13));
        labelInfo.setForeground(new Color(100, 100, 100));
        panelInfo.add(labelInfo);

        panelPrincipal.add(panelHaut, BorderLayout.NORTH);
        panelPrincipal.add(panelFiltre, BorderLayout.CENTER);
        panelPrincipal.add(scrollPane, BorderLayout.SOUTH);
        panelPrincipal.add(panelInfo, BorderLayout.EAST);

        JPanel panelContenu = new JPanel(new BorderLayout());
        panelContenu.add(panelFiltre, BorderLayout.NORTH);
        panelContenu.add(scrollPane, BorderLayout.CENTER);

        panelPrincipal.add(panelHaut, BorderLayout.NORTH);
        panelPrincipal.add(panelContenu, BorderLayout.CENTER);

        add(panelPrincipal);
    }

    private void rechercher() {
        modele.setRowCount(0);

        String jour       = (String) champJour.getSelectedItem();
        String typeChoisi = (String) champType.getSelectedItem();
        int    capacite   = 0;
        int    heure      = 8;
        int    duree      = 2;

        try {
            heure    = Integer.parseInt(champHeure.getText().trim());
            duree    = Integer.parseInt(champDuree.getText().trim());
            capacite = Integer.parseInt(champCapacite.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Veuillez entrer des nombres valides !",
                "Erreur", JOptionPane.ERROR_MESSAGE);
            return;
        }

        List<Salle> salles;

        if (typeChoisi.equals("Tous") && capacite == 0) {
            salles = rechercheService.sallesDisponibles(jour, heure, duree);
        } else if (!typeChoisi.equals("Tous") && capacite == 0) {
            salles = rechercheService.sallesDisponiblesParType(
                jour, heure, duree, typeSalle.valueOf(typeChoisi));
        } else if (typeChoisi.equals("Tous") && capacite > 0) {
            salles = rechercheService.sallesDisponiblesParCapacite(
                jour, heure, duree, capacite);
        } else {
            salles = rechercheService.rechercherSalle(
                jour, heure, duree, typeSalle.valueOf(typeChoisi), capacite);
        }

        if (salles.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                "Aucune salle disponible pour ces criteres !",
                "Resultat", JOptionPane.INFORMATION_MESSAGE);
        } else {
            for (Salle s : salles) {
                modele.addRow(new Object[]{
                    s.getId_Salle(),
                    s.getNumero(),
                    s.getCapacite(),
                    s.getType().name(),
                    s.getId_Batiment()
                });
            }
        }
    }
}