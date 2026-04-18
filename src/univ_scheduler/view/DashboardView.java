package univ_scheduler.view;

import univ_scheduler.dao.BatimentDAO;
import univ_scheduler.dao.CoursDAO;
import univ_scheduler.dao.NotificationDAO;
import univ_scheduler.dao.ReservationDAO;
import univ_scheduler.dao.SalleDAO;
import univ_scheduler.dao.SignalementDAO;
import univ_scheduler.dao.UtilisateurDAO;
import univ_scheduler.model.Reservation;
import univ_scheduler.model.Utilisateur;
import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Vue principale du tableau de bord.
 * @author Votre Nom
 * @version 1.0
 */
public class DashboardView extends JFrame {
    private static final long serialVersionUID = 1L;

    private Utilisateur     utilisateur;
    private SalleDAO        salleDAO        = new SalleDAO();
    private CoursDAO        coursDAO        = new CoursDAO();
    private ReservationDAO  reservationDAO  = new ReservationDAO();
    private BatimentDAO     batimentDAO     = new BatimentDAO();
    private UtilisateurDAO  utilisateurDAO  = new UtilisateurDAO();
    private SignalementDAO  signalementDAO  = new SignalementDAO();
    private NotificationDAO notificationDAO = new NotificationDAO();

    private JLabel lblSalles, lblCours, lblReservations, lblEnAttente;
    private JLabel lblBatiments, lblUtilisateurs, lblSignalements, lblNotifications;
    private JPanel panelStats;

    public DashboardView(Utilisateur utilisateur) {
        this.utilisateur = utilisateur;
        setTitle("UNIV-SCHEDULER — Dashboard");
        setSize(900, 750);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        initialiserComposants();
        setVisible(true);
    }

    private void rafraichirStats() {
        try {
            List<Reservation> reservations = reservationDAO.listerTous();
            int nbEnAttente = 0;
            for (Reservation r : reservations) {
                if (r.estEnAttente()) nbEnAttente++;
            }
            lblSalles.setText(String.valueOf(salleDAO.listerTous().size()));
            lblCours.setText(String.valueOf(coursDAO.listerTous().size()));
            lblReservations.setText(String.valueOf(reservations.size()));
            lblEnAttente.setText(String.valueOf(nbEnAttente));
            lblBatiments.setText(String.valueOf(batimentDAO.listerTous().size()));
            lblUtilisateurs.setText(String.valueOf(utilisateurDAO.listerTous().size()));
            lblSignalements.setText(String.valueOf(signalementDAO.listerTous().size()));
            lblNotifications.setText(String.valueOf(notificationDAO.listerTous().size()));
        } catch (Exception ex) {
            System.err.println("Erreur dashboard : " + ex.getMessage());
        }
    }

    private void initialiserComposants() {
        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBackground(new Color(25, 42, 86));
        panelHaut.setPreferredSize(new Dimension(900, 80));
        panelHaut.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        JPanel panelLogo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        panelLogo.setBackground(new Color(25, 42, 86));

        try {
            ImageIcon iconOriginal = new ImageIcon(
                "C:\\Users\\AMINATA\\eclipse-workspace\\univ_scheduler\\src\\images\\logo.universite.jpeg");
            Image imageReduite = iconOriginal.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            panelLogo.add(new JLabel(new ImageIcon(imageReduite)));
        } catch (Exception e) {
            System.out.println("Logo non trouve : " + e.getMessage());
        }

        JLabel labelTitre = new JLabel("UNIV-SCHEDULER");
        labelTitre.setFont(new Font("Arial", Font.BOLD, 22));
        labelTitre.setForeground(Color.WHITE);
        panelLogo.add(labelTitre);

        JPanel panelUser = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 20));
        panelUser.setBackground(new Color(25, 42, 86));
        JLabel labelUser = new JLabel(
            utilisateur.getNomComplet() + "  |  " + utilisateur.getRole().getlibelle());
        labelUser.setFont(new Font("Arial", Font.PLAIN, 14));
        labelUser.setForeground(new Color(200, 210, 255));
        panelUser.add(labelUser);

        panelHaut.add(panelLogo, BorderLayout.WEST);
        panelHaut.add(panelUser, BorderLayout.EAST);

        JPanel panelMenu = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        panelMenu.setBackground(new Color(41, 128, 185));
        panelMenu.setPreferredSize(new Dimension(900, 45));

        if (utilisateur.isADMIN() || utilisateur.isGESTIONNAIRE()) {
            panelMenu.add(creerBoutonMenu("Cours"));
            panelMenu.add(creerBoutonMenu("Salles"));
            panelMenu.add(creerBoutonMenu("Calendrier"));
            panelMenu.add(creerBoutonMenu("Reservations"));
            panelMenu.add(creerBoutonMenu("Recherche"));
        }
        if (utilisateur.isADMIN()) {
            panelMenu.add(creerBoutonMenu("Equipements"));
        }
        if (utilisateur.isENSEIGNANT()) {
            panelMenu.add(creerBoutonMenu("Calendrier"));
            panelMenu.add(creerBoutonMenu("Reservations"));
            panelMenu.add(creerBoutonMenu("Signalements"));
        }
        if (utilisateur.isETUDIANT()) {
            panelMenu.add(creerBoutonMenu("Calendrier"));
            panelMenu.add(creerBoutonMenu("Recherche"));
        }
        panelMenu.add(creerBoutonMenu("Deconnexion"));

        JPanel panelNord = new JPanel(new BorderLayout());
        panelNord.add(panelHaut, BorderLayout.NORTH);
        panelNord.add(panelMenu, BorderLayout.SOUTH);

        panelStats = creerPanelStats();

        JPanel panelCentre = creerPanelNavigation();

        JPanel panelBas = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelBas.setBackground(new Color(25, 42, 86));
        JLabel labelBas = new JLabel("UNIV-SCHEDULER © 2024 — Tous droits reserves");
        labelBas.setForeground(new Color(200, 210, 255));
        labelBas.setFont(new Font("Arial", Font.ITALIC, 12));
        panelBas.add(labelBas);

        JPanel panelContenu = new JPanel(new BorderLayout());
        panelContenu.setBackground(new Color(236, 240, 245));
        panelContenu.add(panelStats, BorderLayout.NORTH);
        panelContenu.add(panelCentre, BorderLayout.CENTER);

        panelPrincipal.add(panelNord, BorderLayout.NORTH);
        panelPrincipal.add(panelContenu, BorderLayout.CENTER);
        panelPrincipal.add(panelBas, BorderLayout.SOUTH);

        add(panelPrincipal);
        rafraichirStats();
    }

    private JPanel creerPanelStats() {
        JPanel panel = new JPanel(new GridLayout(2, 4, 15, 15));
        panel.setBackground(new Color(236, 240, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        lblSalles        = new JLabel("...", SwingConstants.CENTER);
        lblCours         = new JLabel("...", SwingConstants.CENTER);
        lblReservations  = new JLabel("...", SwingConstants.CENTER);
        lblEnAttente     = new JLabel("...", SwingConstants.CENTER);
        lblBatiments     = new JLabel("...", SwingConstants.CENTER);
        lblUtilisateurs  = new JLabel("...", SwingConstants.CENTER);
        lblSignalements  = new JLabel("...", SwingConstants.CENTER);
        lblNotifications = new JLabel("...", SwingConstants.CENTER);

        panel.add(creerCarteStats(lblSalles,        "Salles",        new Color(41, 128, 185)));
        panel.add(creerCarteStats(lblCours,          "Cours",         new Color(39, 174, 96)));
        panel.add(creerCarteStats(lblReservations,   "Reservations",  new Color(230, 126, 34)));
        panel.add(creerCarteStats(lblEnAttente,      "En attente",    new Color(192, 57, 43)));
        panel.add(creerCarteStats(lblBatiments,      "Batiments",     new Color(142, 68, 173)));
        panel.add(creerCarteStats(lblUtilisateurs,   "Utilisateurs",  new Color(22, 160, 133)));
        panel.add(creerCarteStats(lblSignalements,   "Signalements",  new Color(211, 84, 0)));
        panel.add(creerCarteStats(lblNotifications,  "Notifications", new Color(52, 73, 94)));

        return panel;
    }

    private JPanel creerCarteStats(JLabel labelValeur, String libelle, Color couleur) {
        JPanel carte = new JPanel(new BorderLayout());
        carte.setBackground(Color.WHITE);
        carte.setBorder(BorderFactory.createLineBorder(couleur, 2));

        labelValeur.setFont(new Font("Arial", Font.BOLD, 30));
        labelValeur.setForeground(couleur);
        labelValeur.setBorder(BorderFactory.createEmptyBorder(10, 0, 5, 0));

        JLabel labelNom = new JLabel(libelle, SwingConstants.CENTER);
        labelNom.setFont(new Font("Arial", Font.BOLD, 12));
        labelNom.setForeground(new Color(100, 100, 100));
        labelNom.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        carte.add(labelValeur, BorderLayout.CENTER);
        carte.add(labelNom, BorderLayout.SOUTH);
        return carte;
    }

    private JPanel creerPanelNavigation() {
        JPanel panel = new JPanel();
        panel.setBackground(new Color(236, 240, 245));
        panel.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        if (utilisateur.isADMIN()) {
            panel.setLayout(new GridLayout(2, 4, 20, 20));
            String[] titres  = {"Cours", "Salles", "Batiments", "Equipements",
                                "Reservations", "Signalements", "Notifications", "Recherche"};
            Color[] couleurs = {
                new Color(41, 128, 185), new Color(39, 174, 96),
                new Color(142, 68, 173), new Color(52, 73, 94),
                new Color(230, 126, 34), new Color(192, 57, 43),
                new Color(22, 160, 133), new Color(52, 152, 219)
            };
            for (int i = 0; i < titres.length; i++)
                panel.add(creerCarte(titres[i], couleurs[i]));

        } else if (utilisateur.isGESTIONNAIRE()) {
            panel.setLayout(new GridLayout(1, 5, 20, 20));
            String[] titres  = {"Cours", "Salles", "Calendrier", "Reservations", "Recherche"};
            Color[] couleurs = {
                new Color(41, 128, 185), new Color(39, 174, 96),
                new Color(142, 68, 173), new Color(230, 126, 34),
                new Color(52, 152, 219)
            };
            for (int i = 0; i < titres.length; i++)
                panel.add(creerCarte(titres[i], couleurs[i]));

        } else if (utilisateur.isENSEIGNANT()) {
            panel.setLayout(new GridLayout(1, 3, 20, 20));
            panel.add(creerCarte("Calendrier",   new Color(41, 128, 185)));
            panel.add(creerCarte("Reservations", new Color(39, 174, 96)));
            panel.add(creerCarte("Signalements", new Color(192, 57, 43)));

        } else {
            panel.setLayout(new GridLayout(1, 2, 20, 20));
            panel.add(creerCarte("Calendrier", new Color(41, 128, 185)));
            panel.add(creerCarte("Recherche",  new Color(52, 152, 219)));
        }

        return panel;
    }

    private JPanel creerCarte(String titre, Color couleur) {
        JPanel carte = new JPanel(new BorderLayout());
        carte.setBackground(Color.WHITE);
        carte.setBorder(BorderFactory.createLineBorder(couleur, 2));
        carte.setCursor(new Cursor(Cursor.HAND_CURSOR));

        JPanel panelIcone = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelIcone.setBackground(couleur);
        panelIcone.setPreferredSize(new Dimension(100, 80));
        JLabel labelIcone = new JLabel(titre, SwingConstants.CENTER);
        labelIcone.setFont(new Font("Arial", Font.BOLD, 18));
        labelIcone.setForeground(Color.WHITE);
        panelIcone.add(labelIcone);

        JLabel labelTitre = new JLabel(titre, SwingConstants.CENTER);
        labelTitre.setFont(new Font("Arial", Font.BOLD, 16));
        labelTitre.setForeground(couleur);
        labelTitre.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        carte.add(panelIcone, BorderLayout.CENTER);
        carte.add(labelTitre, BorderLayout.SOUTH);

        carte.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) { naviguer(titre); }
            public void mouseEntered(MouseEvent e) { carte.setBackground(new Color(245, 245, 245)); }
            public void mouseExited(MouseEvent e)  { carte.setBackground(Color.WHITE); }
        });

        return carte;
    }

    private void naviguer(String destination) {
        if (destination.contains("Cours")) {
            new CoursView(utilisateur).setVisible(true);
        } else if (destination.contains("Salles")) {
            new SalleView(utilisateur).setVisible(true);
        } else if (destination.contains("Calendrier")) {
            new CalendrierView().setVisible(true);
        } else if (destination.contains("Reservations")) {
            ReservationView rv = new ReservationView(utilisateur);
            rv.addWindowListener(new java.awt.event.WindowAdapter() {
                public void windowClosed(java.awt.event.WindowEvent e) {
                    rafraichirStats();
                }
            });
            rv.setVisible(true);
            return;
        } else if (destination.contains("Batiments")) {
            new BatimentView(utilisateur).setVisible(true);
        } else if (destination.contains("Equipements")) {
            new EquipementView(utilisateur).setVisible(true);
        } else if (destination.contains("Signalements")) {
            new SignalementView(utilisateur).setVisible(true);
        } else if (destination.contains("Notifications")) {
            new NotificationView(utilisateur).setVisible(true);
        } else if (destination.contains("Recherche")) {
            new RechercheSalleView(utilisateur).setVisible(true);
        } else if (destination.contains("Deconnexion")) {
            new LoginView().setVisible(true);
            this.dispose();
            return;
        }
        rafraichirStats();
    }

    private JButton creerBoutonMenu(String nom) {
        JButton btn = new JButton(nom);
        btn.setBackground(nom.equals("Deconnexion") ? new Color(192, 57, 43) : new Color(52, 73, 94));
        btn.setForeground(Color.WHITE);
        btn.setFont(new Font("Arial", Font.BOLD, 13));
        btn.setBorderPainted(false);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(130, 45));
        btn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                naviguer(nom);
            }
        });
        return btn;
    }
}