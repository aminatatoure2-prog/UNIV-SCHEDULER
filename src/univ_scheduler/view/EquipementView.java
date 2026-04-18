package univ_scheduler.view;


import univ_scheduler.dao.EquipementDAO;

import univ_scheduler.model.Equipement;
import univ_scheduler.model.Utilisateur;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
/**
 * Vue de gestion des equipements.
 * @author Votre Nom
 * @version 1.0
 */

public class EquipementView extends JFrame {
    private static final long serialVersionUID = 1L;

    private Utilisateur       utilisateur;
    private EquipementDAO     equipementDAO = new EquipementDAO();
    private JTable            tableau;
    private DefaultTableModel modele;

    public EquipementView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        setTitle("UNIV-SCHEDULER — Equipements");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initialiserComposants();
        chargerDonnees();
    }

    private void initialiserComposants() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBackground(new Color(52, 73, 94));
        panelHaut.setPreferredSize(new Dimension(800, 65));
        panelHaut.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JLabel titre = new JLabel("Gestion des Equipements");
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

        String[] colonnes = {"ID", "Nom", "Type"};
        modele  = new DefaultTableModel(colonnes, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tableau = new JTable(modele) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(235, 240, 245));
                }
                return c;
            }
        };
        tableau.setFont(new Font("Arial", Font.PLAIN, 13));
        tableau.setRowHeight(32);
        tableau.setShowGrid(false);
        tableau.setIntercellSpacing(new Dimension(0, 0));
        tableau.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableau.getTableHeader().setBackground(new Color(52, 73, 94));
        tableau.getTableHeader().setForeground(Color.WHITE);
        tableau.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tableau.setSelectionBackground(new Color(52, 73, 94));
        tableau.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tableau);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBas.setBackground(new Color(236, 240, 245));

        if (utilisateur.peutModifier()) {
            JButton btnAjouter = new JButton("Ajouter");
            btnAjouter.setBackground(new Color(39, 174, 96));
            btnAjouter.setForeground(Color.WHITE);
            btnAjouter.setFont(new Font("Arial", Font.BOLD, 13));
            btnAjouter.setBorderPainted(false);
            btnAjouter.setFocusPainted(false);
            btnAjouter.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnAjouter.setPreferredSize(new Dimension(140, 38));
            btnAjouter.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    ajouterEquipement();
                }
            });
            panelBas.add(btnAjouter);

            JButton btnSupprimer = new JButton("Supprimer");
            btnSupprimer.setBackground(new Color(192, 57, 43));
            btnSupprimer.setForeground(Color.WHITE);
            btnSupprimer.setFont(new Font("Arial", Font.BOLD, 13));
            btnSupprimer.setBorderPainted(false);
            btnSupprimer.setFocusPainted(false);
            btnSupprimer.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnSupprimer.setPreferredSize(new Dimension(140, 38));
            btnSupprimer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    supprimerEquipement();
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
        List<Equipement> equipements = equipementDAO.listerTous();
        for (Equipement e : equipements) {
            modele.addRow(new Object[]{
                e.getId_Equipement(),
                e.getNom(),
                e.getType()
            });
        }
    }

    private void ajouterEquipement() {
        JTextField champNom  = new JTextField();
        JTextField champType = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 10));
        panel.add(new JLabel("Nom :"));
        panel.add(champNom);
        panel.add(new JLabel("Type :"));
        panel.add(champType);

        int result = JOptionPane.showConfirmDialog(this, panel,
            "Nouvel Equipement", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Equipement e = new Equipement(
                0,
                champNom.getText(),
                champType.getText()
            );
            equipementDAO.ajouter(e);
            chargerDonnees();
            JOptionPane.showMessageDialog(this, "Equipement ajoute !");
        }
    }

    private void supprimerEquipement() {
        int ligne = tableau.getSelectedRow();
        if (ligne == -1) {
            JOptionPane.showMessageDialog(this, "Selectionnez un equipement !");
            return;
        }
        int id = (int) modele.getValueAt(ligne, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Supprimer cet equipement ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            equipementDAO.supprimer(id);
            chargerDonnees();
        }
    }
}