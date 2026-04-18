package univ_scheduler.view;

import univ_scheduler.dao.CreneauDAO;

import univ_scheduler.model.Creneau;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
/**
 * Vue du calendrier en mode semaine.

 * @author Votre Nom
 * @version 1.0
 */

public class calendriersemaineView extends JFrame {
    private static final long serialVersionUID = 1L;

    private CreneauDAO creneauDAO = new CreneauDAO();

    public calendriersemaineView() {
        setTitle("UNIV-SCHEDULER — Calendrier Semaine");
        setSize(1100, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        initialiserComposants();
    }

    private void initialiserComposants() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBackground(new Color(25, 42, 86));
        panelHaut.setPreferredSize(new Dimension(1100, 65));
        panelHaut.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

        JLabel titre = new JLabel("Calendrier — Vue Semaine");
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

        JPanel panelCalendrier = new JPanel(new GridLayout(0, 7, 2, 2));
        panelCalendrier.setBackground(new Color(236, 240, 245));
        panelCalendrier.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        String[] jours = {"LUNDI", "MARDI", "MERCREDI", "JEUDI", "VENDREDI", "SAMEDI", "DIMANCHE"};
        Color[]  couleurs = {
            new Color(41, 128, 185), new Color(39, 174, 96),
            new Color(142, 68, 173), new Color(230, 126, 34),
            new Color(192, 57, 43),  new Color(22, 160, 133),
            new Color(52, 73, 94)
        };

        List<Creneau> tousCreneaux = creneauDAO.listerTous();

        for (int i = 0; i < jours.length; i++) {
            JPanel panelJour = new JPanel(new BorderLayout());
            panelJour.setBackground(Color.WHITE);
            panelJour.setBorder(BorderFactory.createLineBorder(couleurs[i], 2));

            JLabel labelJour = new JLabel(jours[i], SwingConstants.CENTER);
            labelJour.setFont(new Font("Arial", Font.BOLD, 13));
            labelJour.setForeground(Color.WHITE);
            labelJour.setBackground(couleurs[i]);
            labelJour.setOpaque(true);
            labelJour.setBorder(BorderFactory.createEmptyBorder(8, 0, 8, 0));
            panelJour.add(labelJour, BorderLayout.NORTH);

            JPanel panelCreneaux = new JPanel();
            panelCreneaux.setLayout(new BoxLayout(panelCreneaux, BoxLayout.Y_AXIS));
            panelCreneaux.setBackground(Color.WHITE);

            boolean found = false;
            final Color couleurJour = couleurs[i];

            for (Creneau c : tousCreneaux) {
                if (c.getJour().name().equals(jours[i])) {
                    found = true;
                    int heureDebut = c.getHeure_Debut();
                    int heures     = heureDebut / 100;
                    int minutes    = heureDebut % 100;
                    String heure   = String.format("%02d:%02d", heures, minutes);

                    JPanel panelCreneau = new JPanel(new BorderLayout());
                    panelCreneau.setBackground(new Color(235, 245, 255));
                    panelCreneau.setBorder(BorderFactory.createCompoundBorder(
                        BorderFactory.createEmptyBorder(3, 3, 3, 3),
                        BorderFactory.createLineBorder(couleurJour, 1)
                    ));

                    JLabel labelHeure = new JLabel(heure + " - " + c.getDuree() + "min", SwingConstants.CENTER);
                    labelHeure.setFont(new Font("Arial", Font.BOLD, 11));
                    labelHeure.setForeground(couleurJour);

                    JLabel labelCours = new JLabel("Cours " + c.getId_Cours(), SwingConstants.CENTER);
                    labelCours.setFont(new Font("Arial", Font.PLAIN, 11));
                    labelCours.setForeground(new Color(80, 80, 80));

                    JLabel labelSalle = new JLabel("Salle " + c.getId_Salle(), SwingConstants.CENTER);
                    labelSalle.setFont(new Font("Arial", Font.PLAIN, 10));
                    labelSalle.setForeground(new Color(120, 120, 120));

                    panelCreneau.add(labelHeure, BorderLayout.NORTH);
                    panelCreneau.add(labelCours, BorderLayout.CENTER);
                    panelCreneau.add(labelSalle, BorderLayout.SOUTH);

                    panelCreneaux.add(panelCreneau);
                }
            }

            if (!found) {
                JLabel labelVide = new JLabel("Aucun cours", SwingConstants.CENTER);
                labelVide.setFont(new Font("Arial", Font.ITALIC, 12));
                labelVide.setForeground(new Color(180, 180, 180));
                panelCreneaux.add(labelVide);
            }

            JScrollPane scroll = new JScrollPane(panelCreneaux);
            scroll.setBorder(null);
            panelJour.add(scroll, BorderLayout.CENTER);
            panelCalendrier.add(panelJour);
        }

        panelPrincipal.add(panelHaut, BorderLayout.NORTH);
        panelPrincipal.add(panelCalendrier, BorderLayout.CENTER);
        add(panelPrincipal);
    }
}