package univ_scheduler.view;


import univ_scheduler.dao.SalleDAO;

import univ_scheduler.model.Salle;
import univ_scheduler.model.Utilisateur;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Vue de gestion des salles.
 * @author Votre Nom
 * @version 1.0
 */


public class SalleView extends JFrame {
    private static final long serialVersionUID = 1L;

    private Utilisateur       utilisateur;
    private SalleDAO          salleDAO = new SalleDAO();
    private JTable            tableau;
    private DefaultTableModel modele;

    public SalleView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        setTitle("UNIV-SCHEDULER — Salles");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initialiserComposants();
        chargerDonnees();
    }

    private void initialiserComposants() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBackground(new Color(39, 174, 96));
        panelHaut.setPreferredSize(new Dimension(850, 65));
        panelHaut.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JLabel titre = new JLabel("🏫  Gestion des Salles");
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setForeground(Color.WHITE);
        panelHaut.add(titre, BorderLayout.WEST);

        JButton btnRetour = new JButton("← Retour");
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

        String[] colonnes = {"ID", "Numero", "Capacite", "Type", "ID Batiment"};
        modele  = new DefaultTableModel(colonnes, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tableau = new JTable(modele) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(232, 248, 232));
                }
                return c;
            }
        };
        tableau.setFont(new Font("Arial", Font.PLAIN, 13));
        tableau.setRowHeight(32);
        tableau.setShowGrid(false);
        tableau.setIntercellSpacing(new Dimension(0, 0));
        tableau.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableau.getTableHeader().setBackground(new Color(39, 174, 96));
        tableau.getTableHeader().setForeground(Color.WHITE);
        tableau.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tableau.setSelectionBackground(new Color(39, 174, 96));
        tableau.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tableau);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBas.setBackground(new Color(236, 240, 245));

        if (utilisateur.peutModifier()) {
            JButton btnSupprimer = new JButton("🗑  Supprimer");
            btnSupprimer.setBackground(new Color(192, 57, 43));
            btnSupprimer.setForeground(Color.WHITE);
            btnSupprimer.setFont(new Font("Arial", Font.BOLD, 13));
            btnSupprimer.setBorderPainted(false);
            btnSupprimer.setFocusPainted(false);
            btnSupprimer.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnSupprimer.setPreferredSize(new Dimension(140, 38));
            btnSupprimer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    supprimerSalle();
                }
            });
            panelBas.add(btnSupprimer);
        }

        panelPrincipal.add(panelHaut, BorderLayout.NORTH);
        panelPrincipal.add(scrollPane, BorderLayout.CENTER);
        panelPrincipal.add(panelBas, BorderLayout.SOUTH);
        add(panelPrincipal);
    }

    private void chargerDonnees() {
        modele.setRowCount(0);
        List<Salle> salles = salleDAO.listerTous();
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

    private void supprimerSalle() {
        int ligne = tableau.getSelectedRow();
        if (ligne == -1) {
            JOptionPane.showMessageDialog(this, "Selectionnez une salle !");
            return;
        }
        int id = (int) modele.getValueAt(ligne, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Supprimer cette salle ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            salleDAO.supprimer(id);
            chargerDonnees();
        }
    }
}