package univ_scheduler.view;

import univ_scheduler.dao.CoursDAO;

import univ_scheduler.model.Cours;
import univ_scheduler.model.Utilisateur;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
/**
 * Vue de gestion des cours.
 * @author Votre Nom
 * @version 1.0
 */


public class CoursView extends JFrame {
    private static final long serialVersionUID = 1L;

    private Utilisateur       utilisateur;
    private CoursDAO          coursDAO = new CoursDAO();
    private JTable            tableau;
    private DefaultTableModel modele;

    public CoursView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        setTitle("UNIV-SCHEDULER — Cours");
        setSize(850, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initialiserComposants();
        chargerDonnees();
    }

    private void initialiserComposants() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBackground(new Color(25, 42, 86));
        panelHaut.setPreferredSize(new Dimension(850, 65));
        panelHaut.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JLabel titre = new JLabel("📚  Gestion des Cours");
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

        String[] colonnes = {"ID", "Semestre", "ID Matiere", "ID Enseignant", "ID Classe", "ID Groupe"};
        modele  = new DefaultTableModel(colonnes, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tableau = new JTable(modele) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(235, 242, 255));
                }
                return c;
            }
        };
        tableau.setFont(new Font("Arial", Font.PLAIN, 13));
        tableau.setRowHeight(32);
        tableau.setShowGrid(false);
        tableau.setIntercellSpacing(new Dimension(0, 0));
        tableau.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableau.getTableHeader().setBackground(new Color(25, 42, 86));
        tableau.getTableHeader().setForeground(Color.WHITE);
        tableau.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tableau.setSelectionBackground(new Color(41, 128, 185));
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
                    supprimerCours();
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
        List<Cours> cours = coursDAO.listerTous();
        for (Cours c : cours) {
            modele.addRow(new Object[]{
                c.getId_Cours(),
                c.getSemestre(),
                c.getId_Matiere(),
                c.getId_Enseignant(),
                c.getId_Class(),
                c.getId_Groupe()
            });
        }
    }

    private void supprimerCours() {
        int ligne = tableau.getSelectedRow();
        if (ligne == -1) {
            JOptionPane.showMessageDialog(this, "Selectionnez un cours !");
            return;
        }
        int id = (int) modele.getValueAt(ligne, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Supprimer ce cours ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            coursDAO.supprimer(id);
            chargerDonnees();
        }
    }
}