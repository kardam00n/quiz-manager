package quizmanager.utils;

import quizmanager.model.Record;
import quizmanager.model.prize.Prize;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class FileManager {

    private FileManager() {
    }

    public static List<Record> importFile(File file) throws IOException {
        List<Record> recordSet = new ArrayList<>();
        FileInputStream fileStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileStream);

        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            recordSet.add(new Record(
                    row.getCell(13).getStringCellValue(),
                    Timestamp.from(
                            Instant.ofEpochMilli(row.getCell(1).getDateCellValue().getTime())),
                    Timestamp.from(
                            Instant.ofEpochMilli(row.getCell(2).getDateCellValue().getTime())),
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
        Cell startTimestampCell = header.createCell(1);
        startTimestampCell.setCellValue("startTimestamp");

        sheet.setColumnWidth(2, 4000);
        Cell endTimestampCell = header.createCell(2);
        endTimestampCell.setCellValue("endTimestamp");

        sheet.setColumnWidth(3, 1000);
        Cell scoreCell = header.createCell(3);
        scoreCell.setCellValue("score");

        sheet.setColumnWidth(4, 2000);
        Cell prizeCell = header.createCell(4);
        prizeCell.setCellValue("prize");

        sheet.setColumnWidth(5, 6000);
        Cell prizeListCell = header.createCell(5);
        prizeListCell.setCellValue("prize list");

        int rowNum = 1;
        for (Record record : recordSet) {
            Row row = sheet.createRow(rowNum);
            nicknameCell = row.createCell(0);
            nicknameCell.setCellValue(record.getNickname());

            startTimestampCell = row.createCell(1);
            startTimestampCell.setCellValue(record.getStartTimestamp());
            startTimestampCell.setCellStyle(dateCellStyle);

            endTimestampCell = row.createCell(1);
            endTimestampCell.setCellValue(record.getEndTimestamp());
            endTimestampCell.setCellStyle(dateCellStyle);

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
