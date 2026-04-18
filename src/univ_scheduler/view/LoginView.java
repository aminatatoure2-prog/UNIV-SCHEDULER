package univ_scheduler.view;


import univ_scheduler.dao.UtilisateurDAO;

import univ_scheduler.model.Utilisateur;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Interface de connexion de l'application UNIV-SCHEDULER.
 */

public class LoginView extends JFrame {
    private static final long serialVersionUID = 1L;

    private JTextField        champEmail;
    private JComboBox<String> champRole;
    private JButton           boutonConnecter;
    private JLabel            labelErreur;
    private UtilisateurDAO    utilisateurDAO = new UtilisateurDAO();

    public LoginView() {
        setTitle("UNIV-SCHEDULER — Connexion");
        setSize(500, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        initialiserComposants();
    }

    private void initialiserComposants() {

        JPanel panelPrincipal = new JPanel(new BorderLayout());

        JPanel panelHaut = new JPanel(new BorderLayout());
        panelHaut.setBackground(new Color(25, 42, 86));
        panelHaut.setPreferredSize(new Dimension(500, 120));

        JPanel panelLogo = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 15));
        panelLogo.setBackground(new Color(25, 42, 86));

        try {
            ImageIcon iconOriginal = new ImageIcon(
                "C:\\Users\\AMINATA\\eclipse-workspace\\univ_sheduler\\src\\images\\logo.universite.jpeg");
            Image imageReduite = iconOriginal.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
            JLabel labelLogo = new JLabel(new ImageIcon(imageReduite));
            panelLogo.add(labelLogo);
        } catch (Exception e) {
            System.out.println("Logo non trouve");
        }

        JLabel titre = new JLabel("UNIV-SCHEDULER");
        titre.setFont(new Font("Arial", Font.BOLD, 26));
        titre.setForeground(Color.WHITE);
        panelLogo.add(titre);

        panelHaut.add(panelLogo, BorderLayout.CENTER);

        JPanel panelForm = new JPanel(new GridBagLayout());
        panelForm.setBackground(Color.WHITE);
        panelForm.setBorder(BorderFactory.createEmptyBorder(25, 50, 25, 50));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill    = GridBagConstraints.HORIZONTAL;
        gbc.insets  = new Insets(10, 5, 10, 5);
        gbc.weightx = 1.0;

        JLabel labelConnexion = new JLabel("Connexion", SwingConstants.CENTER);
        labelConnexion.setFont(new Font("Arial", Font.BOLD, 18));
        labelConnexion.setForeground(new Color(25, 42, 86));
        gbc.gridx = 0; gbc.gridy = 0;
        panelForm.add(labelConnexion, gbc);

        gbc.gridy = 1;
        JLabel labelEmail = new JLabel("Email :");
        labelEmail.setFont(new Font("Arial", Font.BOLD, 13));
        labelEmail.setForeground(new Color(25, 42, 86));
        panelForm.add(labelEmail, gbc);

        gbc.gridy = 2;
        champEmail = new JTextField();
        champEmail.setPreferredSize(new Dimension(300, 38));
        champEmail.setFont(new Font("Arial", Font.PLAIN, 13));
        champEmail.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(25, 42, 86), 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)));
        panelForm.add(champEmail, gbc);

        gbc.gridy = 3;
        JLabel labelRole = new JLabel("Role :");
        labelRole.setFont(new Font("Arial", Font.BOLD, 13));
        labelRole.setForeground(new Color(25, 42, 86));
        panelForm.add(labelRole, gbc);

        gbc.gridy = 4;
        String[] roles = {"Admin", "Gestionnaire", "Enseignant", "Etudiant"};
        champRole = new JComboBox<String>(roles);
        champRole.setPreferredSize(new Dimension(300, 38));
        champRole.setFont(new Font("Arial", Font.PLAIN, 13));
        champRole.setBackground(Color.WHITE);
        panelForm.add(champRole, gbc);

        gbc.gridy = 5;
        labelErreur = new JLabel("");
        labelErreur.setForeground(Color.RED);
        labelErreur.setFont(new Font("Arial", Font.ITALIC, 12));
        labelErreur.setHorizontalAlignment(SwingConstants.CENTER);
        panelForm.add(labelErreur, gbc);

        gbc.gridy = 6;
        boutonConnecter = new JButton("Se connecter");
        boutonConnecter.setBackground(new Color(25, 42, 86));
        boutonConnecter.setForeground(Color.WHITE);
        boutonConnecter.setFont(new Font("Arial", Font.BOLD, 14));
        boutonConnecter.setPreferredSize(new Dimension(300, 42));
        boutonConnecter.setCursor(new Cursor(Cursor.HAND_CURSOR));
        boutonConnecter.setBorderPainted(false);
        boutonConnecter.setFocusPainted(false);
        boutonConnecter.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                connecter();
            }
        });
        panelForm.add(boutonConnecter, gbc);

        panelPrincipal.add(panelHaut, BorderLayout.NORTH);
        panelPrincipal.add(panelForm, BorderLayout.CENTER);
        add(panelPrincipal);
    }

    private void connecter() {
        String email = champEmail.getText().trim();
        String role  = (String) champRole.getSelectedItem();

        if (email.isEmpty()) {
            labelErreur.setText("Veuillez entrer votre email !");
            return;
        }

        Utilisateur u = utilisateurDAO.connecter(email, role);

        if (u != null) {
            labelErreur.setText("");
            JOptionPane.showMessageDialog(this,
                "Bienvenue " + u.getNomComplet() + " !",
                "Connexion reussie",
                JOptionPane.INFORMATION_MESSAGE);
            new DashboardView(u).setVisible(true);
            this.dispose();
        } else {
            labelErreur.setText("Email ou role incorrect !");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new LoginView().setVisible(true);
            }
        });
    }
}