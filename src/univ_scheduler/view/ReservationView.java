package univ_scheduler.view;

import univ_scheduler.dao.ReservationDAO;

import univ_scheduler.dao.SalleDAO;
import univ_scheduler.model.Reservation;
import univ_scheduler.model.Salle;
import univ_scheduler.model.Utilisateur;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Vue du calendrier en mode semaine.
 * @author Votre Nom
 * @version 1.0
 */


public class ReservationView extends JFrame {
    private static final long serialVersionUID = 1L;

    private Utilisateur       utilisateur;
    private ReservationDAO    reservationDAO = new ReservationDAO();
    private SalleDAO          salleDAO       = new SalleDAO();
    private JTable            tableau;
    private DefaultTableModel modele;

    public ReservationView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        setTitle("UNIV-SCHEDULER — Reservations");
        setSize(900, 550);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initialiserComposants();
        chargerDonnees();
    }

    private void initialiserComposants() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBackground(new Color(230, 126, 34));
        panelHaut.setPreferredSize(new Dimension(900, 65));
        panelHaut.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JLabel titre = new JLabel("🗓  Gestion des Reservations");
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

        String[] colonnes = {"ID", "Date", "Motif", "Statut", "ID Salle", "ID Utilisateur"};
        modele  = new DefaultTableModel(colonnes, 0) {
            public boolean isCellEditable(int row, int col) { return false; }
        };
        tableau = new JTable(modele) {
            public Component prepareRenderer(TableCellRenderer renderer, int row, int col) {
                Component c = super.prepareRenderer(renderer, row, col);
                if (!isRowSelected(row)) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : new Color(255, 243, 230));
                }
                return c;
            }
        };
        tableau.setFont(new Font("Arial", Font.PLAIN, 13));
        tableau.setRowHeight(32);
        tableau.setShowGrid(false);
        tableau.setIntercellSpacing(new Dimension(0, 0));
        tableau.getTableHeader().setFont(new Font("Arial", Font.BOLD, 13));
        tableau.getTableHeader().setBackground(new Color(230, 126, 34));
        tableau.getTableHeader().setForeground(Color.WHITE);
        tableau.getTableHeader().setPreferredSize(new Dimension(0, 40));
        tableau.setSelectionBackground(new Color(230, 126, 34));
        tableau.setSelectionForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(tableau);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        panelBas.setBackground(new Color(236, 240, 245));

        JButton btnNouvelle = new JButton("➕  Nouvelle Reservation");
        btnNouvelle.setBackground(new Color(39, 174, 96));
        btnNouvelle.setForeground(Color.WHITE);
        btnNouvelle.setFont(new Font("Arial", Font.BOLD, 13));
        btnNouvelle.setBorderPainted(false);
        btnNouvelle.setFocusPainted(false);
        btnNouvelle.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btnNouvelle.setPreferredSize(new Dimension(200, 38));
        btnNouvelle.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                nouvelleReservation();
            }
        });
        panelBas.add(btnNouvelle);

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
                    supprimerReservation();
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
        List<Reservation> reservations = reservationDAO.listerTous();
        for (Reservation r : reservations) {
            modele.addRow(new Object[]{
                r.getId_Reservation(),
                r.getDate_Reservation(),
                r.getMotif(),
                r.getStatut().name(),
                r.getId_Salle(),
                r.getId_Utilisateur()
            });
        }
    }

    private void nouvelleReservation() {
        JTextField champDate  = new JTextField();
        JTextField champMotif = new JTextField();

        List<Salle> salles = salleDAO.listerTous();
        JComboBox<String> champSalle = new JComboBox<String>();
        for (Salle s : salles) {
            champSalle.addItem(s.getId_Salle() + " - " + s.getNumero());
        }

        JPanel panel = new JPanel(new GridLayout(3, 2, 5, 10));
        panel.add(new JLabel("Date (YYYY-MM-DD) :"));
        panel.add(champDate);
        panel.add(new JLabel("Motif :"));
        panel.add(champMotif);
        panel.add(new JLabel("Salle :"));
        panel.add(champSalle);

        int result = JOptionPane.showConfirmDialog(this, panel,
            "Nouvelle Reservation", JOptionPane.OK_CANCEL_OPTION);

        if (result == JOptionPane.OK_OPTION) {
            String salleSelectionnee = (String) champSalle.getSelectedItem();
            int idSalle = Integer.parseInt(salleSelectionnee.split(" - ")[0]);
            Reservation r = new Reservation(
                0,
                champDate.getText(),
                champMotif.getText(),
                "EN_ATTENTE",
                idSalle,
                utilisateur.getId_Utilisateur()
            );
            reservationDAO.ajouter(r);
            chargerDonnees();
            JOptionPane.showMessageDialog(this, "Reservation ajoutee !");
        }
    }

    private void supprimerReservation() {
        int ligne = tableau.getSelectedRow();
        if (ligne == -1) {
            JOptionPane.showMessageDialog(this, "Selectionnez une reservation !");
            return;
        }
        int id = (int) modele.getValueAt(ligne, 0);
        int confirm = JOptionPane.showConfirmDialog(this,
            "Supprimer cette reservation ?", "Confirmation", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            reservationDAO.supprimer(id);
            chargerDonnees();
        }
    }
}