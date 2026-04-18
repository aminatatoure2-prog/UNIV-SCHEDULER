
package univ_scheduler.view;
import univ_scheduler.dao.BatimentDAO;
import univ_scheduler.model.Batiment;
import univ_scheduler.model.Utilisateur;
import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.util.List;

/**

* Vue de gestion des bâtiments améliorée (ajout, suppression, modification, contrôles de saisie).
* @author Votre Nom
* @version 1.1
*/
public class BatimentView extends JFrame {
private static final long serialVersionUID = 1L;
private Utilisateur       utilisateur;
private BatimentDAO       batimentDAO = new BatimentDAO();
private JTable            tableau;
private DefaultTableModel modele;
 public BatimentView(Utilisateur utilisateur) {
this.utilisateur = utilisateur;
setTitle("UNIV-SCHEDULER — Bâtiments");
setSize(800, 500);
setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
setLocationRelativeTo(null);
initialiserComposants();
chargerDonnees();
}
 private void initialiserComposants() {
JPanel panelPrincipal = new JPanel(new BorderLayout());

 JPanel panelHaut = new JPanel(new BorderLayout());
 panelHaut.setBackground(new Color(142, 68, 173));
 panelHaut.setPreferredSize(new Dimension(800, 65));
 panelHaut.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 15));

 JLabel titre = new JLabel("Gestion des Bâtiments");
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
 btnRetour.addActionListener(e -> dispose());
 panelHaut.add(btnRetour, BorderLayout.EAST);

 String[] colonnes = {"ID", "Nom", "Localisation", "Nb Etages"};
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

 if (utilisateur.peutModifier()) {
     JButton btnAjouter = new JButton("Ajouter");
     btnAjouter.setBackground(new Color(39, 174, 96));
     btnAjouter.setForeground(Color.WHITE);
     btnAjouter.setFont(new Font("Arial", Font.BOLD, 13));
     btnAjouter.setBorderPainted(false);
     btnAjouter.setFocusPainted(false);
     btnAjouter.setCursor(new Cursor(Cursor.HAND_CURSOR));
     btnAjouter.setPreferredSize(new Dimension(140, 38));
     btnAjouter.addActionListener(e -> ajouterBatiment());
     panelBas.add(btnAjouter);

     JButton btnModifier = new JButton("Modifier");
     btnModifier.setBackground(new Color(52, 152, 219));
     btnModifier.setForeground(Color.WHITE);
     btnModifier.setFont(new Font("Arial", Font.BOLD, 13));
     btnModifier.setBorderPainted(false);
     btnModifier.setFocusPainted(false);
     btnModifier.setCursor(new Cursor(Cursor.HAND_CURSOR));
     btnModifier.setPreferredSize(new Dimension(140, 38));
     btnModifier.addActionListener(e -> modifierBatiment());
     panelBas.add(btnModifier);

     JButton btnSupprimer = new JButton("Supprimer");
     btnSupprimer.setBackground(new Color(192, 57, 43));
     btnSupprimer.setForeground(Color.WHITE);
     btnSupprimer.setFont(new Font("Arial", Font.BOLD, 13));
     btnSupprimer.setBorderPainted(false);
     btnSupprimer.setFocusPainted(false);
     btnSupprimer.setCursor(new Cursor(Cursor.HAND_CURSOR));
     btnSupprimer.setPreferredSize(new Dimension(140, 38));
     btnSupprimer.addActionListener(e -> supprimerBatiment());
     panelBas.add(btnSupprimer);
 }

 panelPrincipal.add(panelHaut, BorderLayout.NORTH);
 panelPrincipal.add(scrollPane, BorderLayout.CENTER);
 panelPrincipal.add(panelBas, BorderLayout.SOUTH);
 add(panelPrincipal);

 }
private void chargerDonnees() {
try {
modele.setRowCount(0);
List<Batiment> batiments = batimentDAO.listerTous();
for (Batiment b : batiments) {
modele.addRow(new Object[]{
b.getId_Batiment(),
b.getNom(),
b.getLocalisation(),
b.getNb_etage()
});
}
} catch (Exception ex) {
JOptionPane.showMessageDialog(this, "Erreur lors du chargement : " + ex.getMessage());
}
}
 private void ajouterBatiment() {
JTextField champNom   = new JTextField();
JTextField champLoc   = new JTextField();
JTextField champEtage = new JTextField();

 JPanel panel = new JPanel(new GridLayout(3, 2, 5, 10));
 panel.add(new JLabel("Nom :"));
 panel.add(champNom);
 panel.add(new JLabel("Localisation :"));
 panel.add(champLoc);
 panel.add(new JLabel("Nb Etages :"));
 panel.add(champEtage);

 int result = JOptionPane.showConfirmDialog(this, panel,
     "Nouveau Bâtiment", JOptionPane.OK_CANCEL_OPTION);

 if (result == JOptionPane.OK_OPTION) {
     String nom = champNom.getText().trim();
     String loc = champLoc.getText().trim();
     String etageStr = champEtage.getText().trim();
     if (nom.isEmpty() || loc.isEmpty() || etageStr.isEmpty()) {
         JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires !");
         return;
     }
     int nbEtages;
     try {
         nbEtages = Integer.parseInt(etageStr);
         if (nbEtages < 0) throw new NumberFormatException();
     } catch (NumberFormatException ex) {
         JOptionPane.showMessageDialog(this, "Veuillez saisir un nombre d'étages valide !");
         return;
     }
     try {
         Batiment b = new Batiment(0, nom, loc, nbEtages);
         batimentDAO.ajouter(b);
         chargerDonnees();
         JOptionPane.showMessageDialog(this, "Bâtiment ajouté !");
     } catch (Exception ex) {
         JOptionPane.showMessageDialog(this, "Erreur lors de l'ajout : " + ex.getMessage());
     }
 }

 }
 private void modifierBatiment() {
int ligne = tableau.getSelectedRow();
if (ligne == -1) {
JOptionPane.showMessageDialog(this,"Sélectionnez un bâtiment à modifier !");
return;
}
int id = (int) modele.getValueAt(ligne, 0);
String nomActuel = (String) modele.getValueAt(ligne, 1);
String locActuelle = (String) modele.getValueAt(ligne, 2);
int etageActuel = (int) modele.getValueAt(ligne, 3);

 JTextField champNom = new JTextField(nomActuel);
 JTextField champLoc = new JTextField(locActuelle);
 JTextField champEtage = new JTextField(String.valueOf(etageActuel));

 JPanel panel = new JPanel(new GridLayout(3, 2, 5, 10));
 panel.add(new JLabel("Nom :"));
 panel.add(champNom);
 panel.add(new JLabel("Localisation :"));
 panel.add(champLoc);
 panel.add(new JLabel("Nb Etages :"));
 panel.add(champEtage);

 int result = JOptionPane.showConfirmDialog(this, panel,
     "Modifier Bâtiment", JOptionPane.OK_CANCEL_OPTION);

 if (result == JOptionPane.OK_OPTION) {
     String nom = champNom.getText().trim();
     String loc = champLoc.getText().trim();
     String etageStr = champEtage.getText().trim();
     if (nom.isEmpty() || loc.isEmpty() || etageStr.isEmpty()) {
         JOptionPane.showMessageDialog(this, "Tous les champs sont obligatoires !");
         return;
     }
     int nbEtages;
     try {
         nbEtages = Integer.parseInt(etageStr);
         if (nbEtages < 0) throw new NumberFormatException();
     } catch (NumberFormatException ex) {
         JOptionPane.showMessageDialog(this, "Veuillez saisir un nombre d'étages valide !");
         return;
     }
     try {
         Batiment b = new Batiment(id, nom, loc, nbEtages);
         batimentDAO.modifier(b);
         chargerDonnees();
         JOptionPane.showMessageDialog(this, "Bâtiment modifié !");
     } catch (Exception ex) {
         JOptionPane.showMessageDialog(this, "Erreur lors de la modification : " + ex.getMessage());
     }
 }

 }
 private void supprimerBatiment() {
int ligne = tableau.getSelectedRow();
if (ligne == -1) {
JOptionPane.showMessageDialog(this, "Sélectionnez un bâtiment !");
return;
}
int id = (int) modele.getValueAt(ligne, 0);
int confirm = JOptionPane.showConfirmDialog(this,
"Supprimer ce bâtiment ?", "Confirmation", JOptionPane.YES_NO_OPTION);
if (confirm == JOptionPane.YES_OPTION) {
try {
batimentDAO.supprimer(id);
chargerDonnees();
} catch (Exception ex) {
JOptionPane.showMessageDialog(this, "Erreur lors de la suppression : " + ex.getMessage());
}
}
}
}