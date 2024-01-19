package quizmanager.utils;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.springframework.stereotype.Component;

import quizmanager.model.Record;
import quizmanager.model.prize.Prize;
import quizmanager.service.PrizeService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


@Component
public class FileManager {

    private final int NICKNAME_COLUMN = 13;
    private final int START_TIMESTAMP_COLUMN = 1;
    private final int END_TIMESTAMP_COLUMN = 2;
    private final int SCORE_COLUMN = 5;
    private final int PRIZE_COLUMN = 16;

    private final PrizeService prizeService;
    private FileManager(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    public List<Record> importFile(File file) throws IOException {
        List<Record> recordSet = new ArrayList<>();
        FileInputStream fileStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileStream);

        Sheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            recordSet.add(recordFromRow(row));
        }
        return recordSet;
    }

    private Record recordFromRow(Row row) {
        return new Record(
                row.getCell(NICKNAME_COLUMN).getStringCellValue(),
                Timestamp.from(
                        Instant.ofEpochMilli(row.getCell(START_TIMESTAMP_COLUMN).getDateCellValue().getTime())),
                Timestamp.from(
                        Instant.ofEpochMilli(row.getCell(END_TIMESTAMP_COLUMN).getDateCellValue().getTime())),
                (int) row.getCell(SCORE_COLUMN).getNumericCellValue(),
                parsePrizeString(row.getCell(PRIZE_COLUMN).getStringCellValue())
        );
    }

    public File exportXlsx(List<Record> recordList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        CellStyle dateCellStyle = workbook.createCellStyle();
        dateCellStyle.setDataFormat((short) 22);

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

        sheet.setColumnWidth(3, 2000);
        Cell scoreCell = header.createCell(3);
        scoreCell.setCellValue("score");

        sheet.setColumnWidth(4, 4000);
        Cell prizeCell = header.createCell(4);
        prizeCell.setCellValue("prize");

        sheet.setColumnWidth(5, 8000);
        Cell prizeListCell = header.createCell(5);
        prizeListCell.setCellValue("prize list");

        int rowNum = 1;
        for (Record record : recordList) {
            Row row = sheet.createRow(rowNum);

            nicknameCell = row.createCell(0);
            nicknameCell.setCellValue(record.getNickname());

            startTimestampCell = row.createCell(1);
            startTimestampCell.setCellValue(record.getStartTimestamp());
            startTimestampCell.setCellStyle(dateCellStyle);

            endTimestampCell = row.createCell(2);
            endTimestampCell.setCellValue(record.getEndTimestamp());
            endTimestampCell.setCellStyle(dateCellStyle);

            scoreCell = row.createCell(3);
            scoreCell.setCellValue(record.getScore());

            prizeCell = row.createCell(4);
            Prize prize = record.getPrize();
            prizeCell.setCellValue(prize == null ? "no prize" : prize.simpleToString());

            prizeListCell = row.createCell(5);
            prizeListCell.setCellValue(prizeListToString(record.getPrizeList()));

            rowNum++;
        }

        File file = File.createTempFile("exported", ".xlsx");
        FileOutputStream outputStream = new FileOutputStream(file);
        workbook.write(outputStream);
        outputStream.close();
        return file;
    }

    public File exportPdf(List<Record> recordList) throws IOException, DocumentException {
        Document document = new Document();
        File file = File.createTempFile("exported", ".pdf");
        PdfWriter.getInstance(document, new FileOutputStream(file));

        document.open();

        PdfPTable table = new PdfPTable(5);
        float[] widths = new float[] {20f, 18f, 18f, 10f, 34f};
        table.setWidths(widths);

        addTableHeader(table);

        for (Record record : recordList) {
            addTableRow(table, record);
        }

        document.add(table);
        document.close();
        return file;
    }

    public void addTableHeader(PdfPTable table) throws DocumentException {
        Stream.of("nickname", "start time", "end time", "score", "prize")
                .forEach(columnTitle -> {
                    PdfPCell header = new PdfPCell();
                    header.setBackgroundColor(BaseColor.LIGHT_GRAY);
                    header.setBorderWidth(1.2F);
                    header.setPhrase(new Phrase(columnTitle));
                    table.addCell(header);
                });
    }

    public void addTableRow(PdfPTable table, Record record) {
        table.addCell(record.getNickname());
        table.addCell(record.getStartTimestamp().toString());
        table.addCell(record.getEndTimestamp().toString());
        table.addCell(Integer.toString(record.getScore()));
        Prize prize = record.getPrize();
        table.addCell(prize == null ? "no prize" : prize.simpleToString());
    }

    private List<Prize> parsePrizeString(String prizeString) {
        List<Prize> result = new ArrayList<>();
        String[] prizes = prizeString.split(";");
        for (String prize : prizes) {
            String name = prize.split("\\(")[0];
            name = name.substring(0, name.length() - 1);
            Prize foundPrize = prizeService.getPrizeByName(name);
            if (foundPrize != null) {
                if(!result.contains(foundPrize)){
                    result.add(foundPrize);
                }
            }
        }
        return result;
    }

    public String prizeListToString(List<Prize> prizeList) {
        StringBuilder result = new StringBuilder();
        for (Prize prize : prizeList) {
            result.append(prize.toString());
            result.append("; ");
        }
        return result.toString();
    }


}
