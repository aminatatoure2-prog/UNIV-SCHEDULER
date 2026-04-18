package univ_scheduler.view;


import univ_scheduler.dao.SignalementDAO;

import univ_scheduler.model.Signalement;
import univ_scheduler.model.Utilisateur;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Vue de gestion des signalements.
 * @author Votre Nom
 * @version 1.0
 */

public class SignalementView extends JFrame {
    private static final long serialVersionUID = 1L;

    private Utilisateur       utilisateur;
    private SignalementDAO    signalementDAO = new SignalementDAO();
    private JTable            tableau;
    private DefaultTableModel modele;

    public SignalementView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        setTitle("UNIV-SCHEDULER — Signalements");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initialiserComposants();
        chargerDonnees();
    }

    private void initialiserComposants() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBackground(new Color(192, 57, 43));
        panelHaut.setPreferredSize(new Dimension(900, 65));
        panelHaut.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JLabel titre = new JLabel("⚠️  Gestion des Signalements");
        titre.setFont(new Font("Arial", Font.BOLD, 20));
        titre.setForeground(Color.WHITE);
        panelHaut.add(titre, BorderLayout.WEST);

        JButton btnRetour = new JButton("← Retour");
        btnRetour.setBackground(new Color(123, 36, 28));
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

        String[] colonnes = {"ID", "Statut", "Date Signalement", "Date Resolution", "ID Salle", "ID Utilisateur"};
        modele  = new DefaultTableModel(colonnes, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tableau = new JTable(modele) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(255, 235, 235));
                }
                return c;
            }
        };
        tableau.setFont(new Font("Arial", Font.PLAIN, 13));
        tableau.setRowHeight(32);
        tableau.setShowGrid(false);
        tableau.setIntercellSpacing(new Dimension(0, 0));
        tableau.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableau.getTableHeader().setBackground(new Color(192, 57, 43));
        tableau.getTableHeader().setForeground(Color.WHITE);
        tableau.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tableau.setSelectionBackground(new Color(192, 57, 43));
        tableau.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tableau);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBas.setBackground(new Color(236, 240, 245));

        JButton btnAjouter = new JButton("➕  Nouveau Signalement");
        btnAjouter.setBackground(new Color(192, 57, 43));
        btnAjouter.setForeground(Color.WHITE);
        btnAjouter.setFont(new Font("Arial", Font.BOLD, 13));
        btnAjouter.setBorderPainted(false);
        btnAjouter.setFocusPainted(false);
        btnAjouter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnAjouter.setPreferredSize(new Dimension(200, 38));
        btnAjouter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nouveauSignalement();
            }
        });
        panelBas.add(btnAjouter);

        if (utilisateur.peutModifier()) {
            JButton btnSupprimer = new JButton("🗑  Supprimer");
            btnSupprimer.setBackground(new Color(123, 36, 28));
            btnSupprimer.setForeground(Color.WHITE);
            btnSupprimer.setFont(new Font("Arial", Font.BOLD, 13));
            btnSupprimer.setBorderPainted(false);
            btnSupprimer.setFocusPainted(false);
            btnSupprimer.setCursor(new Cursor(Cursor.HAND_CURSOR));
            btnSupprimer.setPreferredSize(new Dimension(140, 38));
            btnSupprimer.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    supprimerSignalement();
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
        List<Signalement> signalements = signalementDAO.listerTous();
        for (Signalement s : signalements) {
            modele.addRow(new Object[]{
                s.getId_Signalement(),
                s.getStatut().name(),
                s.getDate_Signalement(),
                s.getDate_Resolution(),
                s.getId_Salle(),
                s.getId_Utilisateur()
            });
        }
    }

    private void nouveauSignalement() {
        JTextField champDate  = new JTextField();
        JTextField champSalle = new JTextField();

        JPanel panel = new JPanel(new GridLayout(2, 2, 5, 10));
        panel.add(new JLabel("Date (YYYY-MM-DD) :"));
        panel.add(champDate);
        panel.add(new JLabel("ID Salle :"));
        panel.add(champSalle);

        int result = JOptionPane.showConfirmDialog(this, panel,
            "Nouveau Signalement", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            Signalement s = new Signalement(
                0,
                "OUVERT",
                champDate.getText(),
                null,
                Integer.parseInt(champSalle.getText()),
                utilisateur.getId_Utilisateur()
            );
            signalementDAO.ajouter(s);
            chargerDonnees();
            JOptionPane.showMessageDialog(this, "Signalement ajoute !");
        }
    }

    private void supprimerSignalement() {
        int ligne = tableau.getSelectedRow();
        if (ligne == -1) {
            JOptionPane.showMessageDialog(this, "Selectionnez un signalement !");
            return;
        }
        int id = (int) modele.getValueAt(ligne, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Supprimer ce signalement ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            signalementDAO.supprimer(id);
            chargerDonnees();
        }
    }
}