package utils;

import model.Record;
import model.prize.Prize;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;


public class FileManager {

    private FileManager() {
    }

    public static TreeSet<Record> importFile(String filePath) throws IOException {
        TreeSet<Record> recordSet = new TreeSet<>();
        FileInputStream file = new FileInputStream(new File(filePath));
        Workbook workbook = new XSSFWorkbook(file);

        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            recordSet.add(new Record(
                    row.getCell(13).getStringCellValue(),
                    Instant.ofEpochMilli(row.getCell(2).getDateCellValue().getTime())
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime(),
                    (int) row.getCell(5).getNumericCellValue(),
                    parsePrizeString(row.getCell(16).getStringCellValue())
            ));
        }
        return recordSet;
    }

    public static void exportFile(Set<Record> recordSet, String filePath) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat((short) 22);

        int columns = 5;
        Sheet sheet = workbook.createSheet("quiz results");

        Row header = sheet.createRow(0);
        sheet.setColumnWidth(0, 4000);
        Cell nicknameCell = header.createCell(0);
        nicknameCell.setCellValue("nickname");

        sheet.setColumnWidth(1, 4000);
        Cell timestampCell = header.createCell(1);
        timestampCell.setCellValue("timestamp");

        sheet.setColumnWidth(2, 1000);
        Cell scoreCell = header.createCell(2);
        scoreCell.setCellValue("score");

        sheet.setColumnWidth(3, 2000);
        Cell prizeCell = header.createCell(3);
        prizeCell.setCellValue("prize");

        sheet.setColumnWidth(4, 6000);
        Cell prizeListCell = header.createCell(4);
        prizeListCell.setCellValue("prize list");

        int rowNum = 1;
        for (Record record : recordSet) {
            Row row = sheet.createRow(rowNum);
            nicknameCell = row.createCell(0);
            nicknameCell.setCellValue(record.getNickname());
            timestampCell = row.createCell(1);
            timestampCell.setCellValue(java.util.Date
                    .from(record.getTimestamp().atZone(ZoneId.systemDefault()).toInstant()));
            timestampCell.setCellStyle(dateCellStyle);
            scoreCell = row.createCell(2);
            scoreCell.setCellValue(record.getScore());
            prizeCell = row.createCell(3);
            Prize prize = record.getPrize();
            prizeCell.setCellValue(prize == null ? "no prize" : prize.toString());
            prizeListCell = row.createCell(4);
            prizeListCell.setCellValue(prizeListToString(record.getPrizeList()));
            rowNum++;
        }

        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);
    }

    private static List<Prize> parsePrizeString(String prizeString) {
        //TODO implement parsing prize string into list of prizes
        return new ArrayList<Prize>();
    }

    public static String prizeListToString(List<Prize> prizeList) {
        StringBuilder result = new StringBuilder();
        for (Prize prize : prizeList) {
            result.append(prize.toString());
            result.append("; ");
        }
        return result.toString();
    }

}
