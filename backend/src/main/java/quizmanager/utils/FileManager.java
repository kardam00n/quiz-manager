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
import quizmanager.model.statictics.AllQuestionsStats;
import quizmanager.service.PrizeService;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;
import java.util.stream.Stream;

@Component
public class FileManager {

    private final int NICKNAME_COLUMN = 13;
    private final int START_TIMESTAMP_COLUMN = 1;
    private final int END_TIMESTAMP_COLUMN = 2;
    private final int SCORE_COLUMN = 5;
    private final int PRIZE_COLUMN = 16;

    private final int[][] QUESTION_COLUMNS = {{7, 8}, {10, 11}}; //{{question name column, question score column}, {..., ...}, ...}

    private final PrizeService prizeService;
    private FileManager(PrizeService prizeService) {
        this.prizeService = prizeService;
    }

    //TODO: DO IT SOME PROPER WAY, IT IS SUPER SUPER SUPER TEMPORARY (I know it's terrible, but time... xd)
    public List<Object> importFile(File file) throws IOException {
        List<Record> recordSet = new ArrayList<>();
        FileInputStream fileStream = new FileInputStream(file);
        Workbook workbook = new XSSFWorkbook(fileStream);

        Sheet sheet = workbook.getSheetAt(0);

        Row headers = sheet.getRow(0);
        List <String> questionNames = new ArrayList<>();
        for (int i = 0; i < QUESTION_COLUMNS.length; i++) {
            String questionName = String.valueOf(headers.getCell(QUESTION_COLUMNS[i][0]));
            questionNames.add(questionName);
        }
        AllQuestionsStats allQuestionsStats = new AllQuestionsStats(questionNames);

        for (Row row : sheet) {
            if (row.getRowNum() == 0) {
                continue;
            }
            recordSet.add(recordFromRow(row));
            addRowStats(row, allQuestionsStats, questionNames);
        }
        allQuestionsStats.calculatePercentages();

        return new ArrayList<>(Arrays.asList(recordSet, allQuestionsStats));
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

    private void addRowStats(Row row, AllQuestionsStats allQuestionsStats, List<String> questionNames) {
        Map<String, Integer> response = new HashMap<>();
        for (int i = 0; i < QUESTION_COLUMNS.length; i++) {
            response.put(questionNames.get(i), (int) row.getCell(QUESTION_COLUMNS[i][1]).getNumericCellValue());
        }
        allQuestionsStats.addResponse(response);
    }

    public File exportXlsx(List<Record> recordList) throws IOException {
        Workbook workbook = new XSSFWorkbook();
        CellStyle basicDateCellStyle = workbook.createCellStyle();
        basicDateCellStyle.setDataFormat((short) 22);

        CellStyle winnerCellStyle = workbook.createCellStyle();
        winnerCellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        winnerCellStyle.setFillPattern((short) 1);
        CellStyle winnerDateCellStyle = workbook.createCellStyle();
        winnerDateCellStyle.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        winnerDateCellStyle.setFillPattern((short) 1);
        winnerDateCellStyle.setDataFormat((short) 22);
        CellStyle winnerLighterCellStyle = workbook.createCellStyle();
        winnerLighterCellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        winnerLighterCellStyle.setFillPattern((short) 1);
        CellStyle winnerLighterDateCellStyle = workbook.createCellStyle();
        winnerLighterDateCellStyle.setFillForegroundColor(IndexedColors.GREY_40_PERCENT.getIndex());
        winnerLighterDateCellStyle.setFillPattern((short) 1);
        winnerLighterDateCellStyle.setDataFormat((short) 22);

        CellStyle headerCellStyle = workbook.createCellStyle();
        headerCellStyle.setFillForegroundColor(IndexedColors.GREY_80_PERCENT.getIndex());
        headerCellStyle.setFillPattern((short) 1);
        Font font = workbook.createFont();
        font.setColor(IndexedColors.WHITE.getIndex());
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);
        headerCellStyle.setFont(font);

        Sheet sheet = workbook.createSheet("quiz results");

        Row header = sheet.createRow(0);
        sheet.setColumnWidth(0, 4000);
        Cell nicknameCell = header.createCell(0);
        nicknameCell.setCellValue("nickname");
        nicknameCell.setCellStyle(headerCellStyle);

        sheet.setColumnWidth(1, 4000);
        Cell startTimestampCell = header.createCell(1);
        startTimestampCell.setCellValue("startTimestamp");
        startTimestampCell.setCellStyle(headerCellStyle);

        sheet.setColumnWidth(2, 4000);
        Cell endTimestampCell = header.createCell(2);
        endTimestampCell.setCellValue("endTimestamp");
        endTimestampCell.setCellStyle(headerCellStyle);

        sheet.setColumnWidth(3, 2000);
        Cell scoreCell = header.createCell(3);
        scoreCell.setCellValue("score");
        scoreCell.setCellStyle(headerCellStyle);

        sheet.setColumnWidth(4, 4000);
        Cell prizeCell = header.createCell(4);
        prizeCell.setCellValue("prize");
        prizeCell.setCellStyle(headerCellStyle);

        sheet.setColumnWidth(5, 8000);
        Cell prizeListCell = header.createCell(5);
        prizeListCell.setCellValue("prize list");
        prizeListCell.setCellStyle(headerCellStyle);

        int rowNum = 1;
        for (Record record : recordList) {
            CellStyle cellStyle;
            CellStyle dateCellStyle;
            Row row = sheet.createRow(rowNum);
            Prize prize = record.getPrize();
            if (prize == null || prize.getName().equals("None")) {
                cellStyle = null;
                dateCellStyle = basicDateCellStyle;
            } else {
                if (rowNum % 2 == 0) {
                    cellStyle = winnerCellStyle;
                    dateCellStyle = winnerDateCellStyle;
                } else {
                    cellStyle = winnerLighterCellStyle;
                    dateCellStyle = winnerLighterDateCellStyle;
                }
            }

            nicknameCell = row.createCell(0);
            nicknameCell.setCellValue(record.getNickname());
            nicknameCell.setCellStyle(cellStyle);

            startTimestampCell = row.createCell(1);
            startTimestampCell.setCellValue(record.getStartTimestamp());
            startTimestampCell.setCellStyle(dateCellStyle);

            endTimestampCell = row.createCell(2);
            endTimestampCell.setCellValue(record.getEndTimestamp());
            endTimestampCell.setCellStyle(dateCellStyle);

            scoreCell = row.createCell(3);
            scoreCell.setCellValue(record.getScore());
            scoreCell.setCellStyle(cellStyle);

            prizeCell = row.createCell(4);
            prizeCell.setCellValue(prize == null ? "no prize" : prize.toSimpleString());
            prizeCell.setCellStyle(cellStyle);

            prizeListCell = row.createCell(5);
            prizeListCell.setCellValue(prizeListToString(record.getPrizeList()));
            prizeListCell.setCellStyle(cellStyle);

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

        boolean variableColor = false;
        for (Record record : recordList) {
            addTableRow(table, record, variableColor);
            variableColor = !variableColor;
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

    public void addTableRow(PdfPTable table, Record record, boolean variableColor) {
        Prize prize = record.getPrize();
        BaseColor color = new BaseColor(220,189,81);
        if (prize == null || prize.getName().equals("None")) {
            color = new BaseColor(255, 255, 255);
        }
        if (variableColor) {
            color = color.brighter();
        }
        table.addCell(colorCell(record.getNickname(), color));
        table.addCell(colorCell(record.getStartTimestamp().toString(), color));
        table.addCell(colorCell(record.getEndTimestamp().toString(), color));
        table.addCell(colorCell(Integer.toString(record.getScore()), color));
        table.addCell(colorCell(prize == null ? "no prize" : prize.toSimpleString(), color));
    }

    public PdfPCell colorCell(String text, BaseColor color) {
        PdfPCell cell = new PdfPCell(Phrase.getInstance(text));
        cell.setBackgroundColor(color);
        return cell;
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
