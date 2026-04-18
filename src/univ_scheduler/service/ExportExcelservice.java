package univ_scheduler.service;


import org.apache.poi.ss.usermodel.*;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import univ_scheduler.dao.CreneauDAO;
import univ_scheduler.model.Creneau;
import java.io.FileOutputStream;
import java.util.List;

/**
 * Service d'export de l'emploi du temps en Excel.
 * @author Votre Nom
 * @version 1.0
 */

public class ExportExcelservice {

    private CreneauDAO creneauDAO = new CreneauDAO();

    public void exporterEmploiDuTemps(String cheminFichier) {
        try {
            Workbook workbook = new XSSFWorkbook();
            Sheet sheet = workbook.createSheet("Emploi du Temps");

            CellStyle styleEntete = workbook.createCellStyle();
            styleEntete.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
            styleEntete.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleEntete.setAlignment(HorizontalAlignment.CENTER);
            styleEntete.setBorderBottom(BorderStyle.THIN);
            styleEntete.setBorderTop(BorderStyle.THIN);
            styleEntete.setBorderLeft(BorderStyle.THIN);
            styleEntete.setBorderRight(BorderStyle.THIN);

            Font fontEntete = workbook.createFont();
            fontEntete.setColor(IndexedColors.WHITE.getIndex());
            fontEntete.setBold(true);
            fontEntete.setFontHeightInPoints((short) 12);
            styleEntete.setFont(fontEntete);

            CellStyle stylePair = workbook.createCellStyle();
            stylePair.setFillForegroundColor(IndexedColors.WHITE.getIndex());
            stylePair.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            stylePair.setAlignment(HorizontalAlignment.CENTER);
            stylePair.setBorderBottom(BorderStyle.THIN);
            stylePair.setBorderTop(BorderStyle.THIN);
            stylePair.setBorderLeft(BorderStyle.THIN);
            stylePair.setBorderRight(BorderStyle.THIN);

            CellStyle styleImpair = workbook.createCellStyle();
            styleImpair.setFillForegroundColor(IndexedColors.LIGHT_BLUE.getIndex());
            styleImpair.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            styleImpair.setAlignment(HorizontalAlignment.CENTER);
            styleImpair.setBorderBottom(BorderStyle.THIN);
            styleImpair.setBorderTop(BorderStyle.THIN);
            styleImpair.setBorderLeft(BorderStyle.THIN);
            styleImpair.setBorderRight(BorderStyle.THIN);

            Row rowTitre = sheet.createRow(0);
            Cell cellTitre = rowTitre.createCell(0);
            cellTitre.setCellValue("UNIV-SCHEDULER — Emploi du Temps");
            CellStyle styleTitre = workbook.createCellStyle();
            Font fontTitre = workbook.createFont();
            fontTitre.setBold(true);
            fontTitre.setFontHeightInPoints((short) 16);
            styleTitre.setFont(fontTitre);
            styleTitre.setAlignment(HorizontalAlignment.CENTER);
            cellTitre.setCellStyle(styleTitre);
            sheet.addMergedRegion(new org.apache.poi.ss.util.CellRangeAddress(0, 0, 0, 5));

            Row rowEntete = sheet.createRow(2);
            String[] colonnes = {"ID", "Jour", "Heure Debut", "Duree", "ID Cours", "ID Salle"};
            for (int i = 0; i < colonnes.length; i++) {
                Cell cell = rowEntete.createCell(i);
                cell.setCellValue(colonnes[i]);
                cell.setCellStyle(styleEntete);
            }

            List<Creneau> creneaux = creneauDAO.listerTous();
            int rowNum = 3;
            for (Creneau c : creneaux) {
                Row row = sheet.createRow(rowNum);
                CellStyle style = rowNum % 2 == 0 ? stylePair : styleImpair;

                int heureDebut = c.getHeure_Debut();
                int heures     = heureDebut / 100;
                int minutes    = heureDebut % 100;
                String heure   = String.format("%02d:%02d", heures, minutes);

                Cell cell0 = row.createCell(0);
                cell0.setCellValue(c.getId_Creneau());
                cell0.setCellStyle(style);

                Cell cell1 = row.createCell(1);
                cell1.setCellValue(c.getJour().name());
                cell1.setCellStyle(style);

                Cell cell2 = row.createCell(2);
                cell2.setCellValue(heure);
                cell2.setCellStyle(style);

                Cell cell3 = row.createCell(3);
                cell3.setCellValue(c.getDuree() + " min");
                cell3.setCellStyle(style);

                Cell cell4 = row.createCell(4);
                cell4.setCellValue(c.getId_Cours());
                cell4.setCellStyle(style);

                Cell cell5 = row.createCell(5);
                cell5.setCellValue(c.getId_Salle());
                cell5.setCellStyle(style);

                rowNum++;
            }

            for (int i = 0; i < colonnes.length; i++) {
                sheet.autoSizeColumn(i);
            }

            FileOutputStream fileOut = new FileOutputStream(cheminFichier);
            workbook.write(fileOut);
            fileOut.close();
            workbook.close();

            System.out.println("Excel exporte avec succes : " + cheminFichier);

        } catch (Exception e) {
            System.out.println("Erreur export Excel : " + e.getMessage());
        }
    }
}