package com.example.smartParking.service;

import com.example.smartParking.model.ReportEntity;
import org.apache.poi.xwpf.usermodel.*;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.swing.*;
import java.io.*;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
public class ReportCreatorService {

    @Value("${upload.report.path}")
    private String uploadPath;

    @Autowired
    Environment environment;


    public boolean createDocxReport(List<ReportEntity> reportEntities, Model model) {
        try {
            // создаем модель docx документа,
            // к которой будем прикручивать наполнение (колонтитулы, текст)
            XWPFDocument docxModel = new XWPFDocument();

            XWPFParagraph bodyParagraph = docxModel.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.CENTER);
            CTSectPr sectPr = docxModel.getDocument().getBody().addNewSectPr();
            CTPageMar pageMar = sectPr.addNewPgMar();
            pageMar.setLeft(BigInteger.valueOf(250L));
            pageMar.setTop(BigInteger.valueOf(1000L));
            pageMar.setRight(BigInteger.valueOf(250L));
            pageMar.setBottom(BigInteger.valueOf(1000L));
            XWPFRun paragraphConfig = bodyParagraph.createRun();
            paragraphConfig.setFontSize(18);
            paragraphConfig.setFontFamily("Times New Roman");
            paragraphConfig.setBold(true);
            paragraphConfig.setText("Отчет о парковочном пространстве СГТУ им. Ю.А. Гагарина");
            paragraphConfig.addBreak();
            XWPFTable table = docxModel.createTable();
            table.setCellMargins(0, 50, 0, 50);
            table.setTableAlignment(TableRowAlign.CENTER);
            table.setWidthType(TableWidthType.DXA);
            XWPFTableRow tableRow = table.getRow(0);
            for (int i = 0; i <= 8; i++) {
                tableRow.createCell();
            }
            widthCellsAcrossRow(table, 0, 0, 4000);
            tableRow.getCell(0).setText("Парковка");
            widthCellsAcrossRow(table, 0, 1, 100);
            tableRow.getCell(1).setText("Номер места");
            widthCellsAcrossRow(table, 0, 2, 100);
            tableRow.getCell(2).setText("Водитель");
            widthCellsAcrossRow(table, 0, 3, 100);
            tableRow.getCell(3).setText("Статус");
            widthCellsAcrossRow(table, 0, 4, 100);
            tableRow.getCell(4).setText("Институт");
            widthCellsAcrossRow(table, 0, 5, 100);
            tableRow.getCell(5).setText("Номер авто");
            widthCellsAcrossRow(table, 0, 6, 100);
            tableRow.getCell(6).setText("Модель авто");
            widthCellsAcrossRow(table, 0, 7, 100);
            tableRow.getCell(7).setText("Дата начала");
            widthCellsAcrossRow(table, 0, 8, 100);
            tableRow.getCell(8).setText("Дата окончания");
            widthCellsAcrossRow(table, 0, 9, 100);
            tableRow.getCell(9).setText("Нарушение");
            int i = 1;
            for (ReportEntity reportEntity : reportEntities) {
                tableRow = table.createRow();
                paragraphConfig = bodyParagraph.createRun();
                paragraphConfig.setFontSize(14);
                paragraphConfig.setFontFamily("Times New Roman");
                generateReportText(reportEntity, paragraphConfig, bodyParagraph, table, tableRow, i);
                i++;
            }

            // сохраняем модель docx документа в файл
            File uploadDir = new File(uploadPath);
            if (!uploadDir.exists()) {
                uploadDir.mkdir();
            }

            String fileName = "report" + UUID.randomUUID().toString() + ".docx";
            String filePath = uploadDir + "/" + fileName;
            FileOutputStream outputStream = new FileOutputStream(filePath);
            docxModel.write(outputStream);
            outputStream.close();
            Path file = Paths.get(uploadPath, fileName);
            if (Files.exists(file)) {
                model.addAttribute("fileName", fileName);
                return true;
            }
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void generateReportText(ReportEntity reportEntity, XWPFRun paragraphConfig, XWPFParagraph bodyParagraph, XWPFTable table, XWPFTableRow row, Integer rowNum) {
        String parkingName = reportEntity.getParkingName();
        String placeNum = null;
        if (reportEntity.getPlaceNum() != null) placeNum = reportEntity.getPlaceNum().toString();
        String autoNum = reportEntity.getAutoNum();
        String personName = reportEntity.getPersonName();
        String startTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(reportEntity.getStartTime());
        String endTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(reportEntity.getEndTime());
        String division = reportEntity.getDivision();
        String autoModel = reportEntity.getAutoModel();
        String state = null;
        if (personName != null && !personName.isBlank()) {
            state = reportEntity.getStudent() ? "Студент" : "Сотрудник";
        }
        String autoViolation = reportEntity.getAutoViolation();
        String passViolation = reportEntity.getPassViolation();
        widthCellsAcrossRow(table, rowNum, 0, 1000);
        if (parkingName != null && !parkingName.isBlank()) {
            row.getCell(0).setText(parkingName);
        } else {
            row.getCell(0).setText("");
        }

        widthCellsAcrossRow(table, rowNum, 1, 100);
        if (placeNum != null && !placeNum.isBlank()) {
            row.getCell(1).setText(placeNum);
        } else {
            row.getCell(1).setText("");
        }

        widthCellsAcrossRow(table, rowNum, 2, 100);
        if (personName != null && !personName.isBlank()) {
            row.getCell(2).setText(personName);
        } else {
            row.getCell(2).setText("");
        }

        widthCellsAcrossRow(table, rowNum, 3, 100);
        if (state != null && !state.isBlank()) {
            row.getCell(3).setText(state);
        } else {
            row.getCell(3).setText("");
        }

        widthCellsAcrossRow(table, rowNum, 4, 100);
        if (division != null && !division.isBlank()) {
            row.getCell(4).setText(division);
        } else {
            row.getCell(4).setText("");
        }

        widthCellsAcrossRow(table, rowNum, 5, 100);
        if (autoNum != null && !autoNum.isBlank()) {
            row.getCell(5).setText(autoNum);
        } else {
            row.getCell(5).setText("");
        }

        widthCellsAcrossRow(table, rowNum, 6, 100);
        if (autoModel != null && !autoModel.isBlank()) {
            row.getCell(6).setText(autoModel);
        } else {
            row.getCell(6).setText("");
        }

        widthCellsAcrossRow(table, rowNum, 7, 100);
        row.getCell(7).setText(startTime);
        widthCellsAcrossRow(table, rowNum, 8, 100);
        row.getCell(8).setText(endTime);

        widthCellsAcrossRow(table, rowNum, 9, 100);
        XWPFTableCell cell = table.getRow(rowNum).getCell(9);
        cell.removeParagraph(0);
        XWPFParagraph tempParagraph = cell.addParagraph();
        tempParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = tempParagraph.createRun();
        run.setColor("FF0000");
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
        if (passViolation != null && !passViolation.isBlank()) {
            run.setText(passViolation);
        } else if (autoViolation != null && !autoViolation.isBlank()) {
            run.setText(autoViolation);
        } else {
            row.getCell(9).setText("");
        }
    }

    private void writeLine(String name, String value, XWPFRun paragraphConfig, XWPFParagraph bodyParagraph) {
        paragraphConfig = bodyParagraph.createRun();
        paragraphConfig.setFontSize(14);
        paragraphConfig.setFontFamily("Times New Roman");
        paragraphConfig.addBreak();
        paragraphConfig.setBold(true);
        paragraphConfig.setText(name);
        paragraphConfig = bodyParagraph.createRun();
        paragraphConfig.setFontSize(14);
        paragraphConfig.setFontFamily("Times New Roman");
        paragraphConfig.setBold(false);
        paragraphConfig.setText(value);
    }

    private void writeViolationLine(String name, String value, XWPFRun paragraphConfig, XWPFParagraph bodyParagraph) {
        paragraphConfig = bodyParagraph.createRun();
        paragraphConfig.setFontSize(14);
        paragraphConfig.setFontFamily("Times New Roman");
        paragraphConfig.setColor("FF0000");
        paragraphConfig.addBreak();
        paragraphConfig.setBold(true);
        paragraphConfig.setText(name);
        paragraphConfig = bodyParagraph.createRun();
        paragraphConfig.setFontSize(14);
        paragraphConfig.setFontFamily("Times New Roman");
        paragraphConfig.setColor("FF0000");
        paragraphConfig.setBold(false);
        paragraphConfig.setText(value);
    }

    private void generateViolationText(ReportEntity reportEntity, XWPFRun paragraphConfig, XWPFParagraph bodyParagraph) {
        String passViolation = reportEntity.getPassViolation();
        String statusViolation = reportEntity.getStatusViolation();
        String autoViolation = reportEntity.getAutoViolation();
        String personViolation = reportEntity.getPersonViolation();
        paragraphConfig.setFontSize(14);
        paragraphConfig.setFontFamily("Times New Roman");
        if (passViolation != null || autoViolation != null || personViolation != null) {
            paragraphConfig.setColor("FF0000");
            writeViolationLine("Нарушения: ", "", paragraphConfig, bodyParagraph);
            if (passViolation != null)
                writeViolationLine("", passViolation, paragraphConfig, bodyParagraph);
            if (autoViolation != null)
                writeViolationLine("", autoViolation, paragraphConfig, bodyParagraph);
            if (personViolation != null)
                writeViolationLine("", personViolation, paragraphConfig, bodyParagraph);
//            if (statusViolation != null)
//                writeViolationLine("", statusViolation, paragraphConfig, bodyParagraph);
        }
    }

    private void appendIfNotNull(String name, String value, XWPFRun paragraphConfig, XWPFParagraph bodyParagraph) {
        if (value != null && !value.isBlank()) {
            writeLine(name, value, paragraphConfig, bodyParagraph);
        }
    }

    private static CTP createFooterModel(String footerContent) {
        // создаем футер или нижний колонтитул
        CTP ctpFooterModel = CTP.Factory.newInstance();
        CTR ctrFooterModel = ctpFooterModel.addNewR();
        CTText cttFooter = ctrFooterModel.addNewT();

        cttFooter.setStringValue(footerContent);
        return ctpFooterModel;
    }

    private static CTP createHeaderModel(String headerContent) {
        // создаем хедер или верхний колонтитул
        CTP ctpHeaderModel = CTP.Factory.newInstance();
        CTR ctrHeaderModel = ctpHeaderModel.addNewR();
        CTText cttHeader = ctrHeaderModel.addNewT();

        cttHeader.setStringValue(headerContent);
        return ctpHeaderModel;
    }

    private String chooseSaveFolder() {
        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            return fileToSave.getAbsolutePath();
        }
        return null;
    }

    private static void widthCellsAcrossRow(XWPFTable table, int rowNum, int colNum, int width) {
        XWPFTableCell cell = table.getRow(rowNum).getCell(colNum);
        if (cell.getCTTc().getTcPr() == null)
            cell.getCTTc().addNewTcPr();
        if (cell.getCTTc().getTcPr().getTcW() == null)
            cell.getCTTc().getTcPr().addNewTcW();
        cell.getCTTc().getTcPr().getTcW().setW(BigInteger.valueOf((long) width));
        XWPFParagraph tempParagraph = cell.getParagraphs().get(0);
        tempParagraph.setAlignment(ParagraphAlignment.CENTER);
        XWPFRun run = tempParagraph.createRun();
        run.setFontFamily("Times New Roman");
        run.setFontSize(11);
        cell.setVerticalAlignment(XWPFTableCell.XWPFVertAlign.CENTER);
    }

}
