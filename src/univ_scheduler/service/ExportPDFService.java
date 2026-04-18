package univ_scheduler.service;

import com.itextpdf.text.Document;

import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import univ_scheduler.dao.CreneauDAO;
import univ_scheduler.model.Creneau;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Service d'export de l'emploi du temps en PDF.
 * @author Votre Nom
 * @version 1.0
 */

public class ExportPDFService {

    private CreneauDAO creneauDAO = new CreneauDAO();

    public void exporterEmploiDuTemps(String cheminFichier) {
        try {
            Document document = new Document();
            PdfWriter.getInstance(document, new FileOutputStream(cheminFichier));
            document.open();

            Font fontTitre = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Font fontSousTitre = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14);
            Font fontNormal = FontFactory.getFont(FontFactory.HELVETICA, 12);

            Paragraph titre = new Paragraph("UNIV-SCHEDULER", fontTitre);
            titre.setAlignment(Element.ALIGN_CENTER);
            document.add(titre);

            Paragraph sousTitre = new Paragraph("Emploi du Temps", fontSousTitre);
            sousTitre.setAlignment(Element.ALIGN_CENTER);
            sousTitre.setSpacingAfter(20);
            document.add(sousTitre);

            PdfPTable table = new PdfPTable(5);
            table.setWidthPercentage(100);

            String[] colonnes = {"ID", "Jour", "Heure Debut", "Duree", "ID Salle"};
            for (String col : colonnes) {
                PdfPCell cell = new PdfPCell(new Phrase(col, FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, Font.BOLD)));
                cell.setBackgroundColor(new BaseColor(25, 42, 86));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setPadding(8);
                cell.getPhrase().getFont().setColor(BaseColor.WHITE);
                table.addCell(cell);
            }

            List<Creneau> creneaux = creneauDAO.listerTous();
            boolean pair = true;
            for (Creneau c : creneaux) {
                int heureDebut = c.getHeure_Debut();
                int heures     = heureDebut / 100;
                int minutes    = heureDebut % 100;
                String heure   = String.format("%02d:%02d", heures, minutes);

                BaseColor couleurLigne = pair ? BaseColor.WHITE : new BaseColor(235, 242, 255);

                String[] valeurs = {
                    String.valueOf(c.getId_Creneau()),
                    c.getJour().name(),
                    heure,
                    c.getDuree() + " min",
                    String.valueOf(c.getId_Salle())
                };

                for (String val : valeurs) {
                    PdfPCell cell = new PdfPCell(new Phrase(val, fontNormal));
                    cell.setBackgroundColor(couleurLigne);
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setPadding(6);
                    table.addCell(cell);
                }
                pair = !pair;
            }

            document.add(table);
            document.close();

            System.out.println("PDF exporte avec succes : " + cheminFichier);

        } catch (Exception e) {
            System.out.println("Erreur export PDF : " + e.getMessage());
        }
    }
}