package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    private static String filePath;

    public ExcelReader(String filePath) {
        ExcelReader.filePath = filePath;
    }



    
    public static Object[][] readExcelData(String filePath, String sheetName) {
        try {
            // Load from classpath (src/test/resources)
            InputStream inputStream = ExcelReader.class.getClassLoader().getResourceAsStream(filePath);
            if (inputStream == null) {
                throw new RuntimeException("Excel file not found in classpath: " + filePath);
            }

            Workbook workbook = new XSSFWorkbook(inputStream);
            Sheet sheet = workbook.getSheet(sheetName);

            int rowCount = sheet.getPhysicalNumberOfRows();
            int colCount = sheet.getRow(0).getLastCellNum();

            Object[][] data = new Object[rowCount - 1][colCount];

            for (int i = 1; i < rowCount; i++) {
                Row row = sheet.getRow(i);
                for (int j = 0; j < colCount; j++) {
                    DataFormatter formatter = new DataFormatter();
                    data[i - 1][j] = formatter.formatCellValue(row.getCell(j));
                }
            }

            workbook.close();
            return data;

        } catch (Exception e) {
            e.printStackTrace();
            return new Object[0][0];
        }
    }


    public Object[][] readSheetWithColumns(String sheetName, List<String> columns) throws IOException {
        FileInputStream fis = new FileInputStream(new File(filePath));
        Workbook workbook = WorkbookFactory.create(fis);
        Sheet sheet = workbook.getSheet(sheetName);

        if (sheet == null) {
            workbook.close();
            throw new IllegalArgumentException("Sheet " + sheetName + " not found in the file: " + filePath);
        }

        Row headerRow = sheet.getRow(0);
        if (headerRow == null) {
            workbook.close();
            throw new IllegalArgumentException("No header row found in the sheet: " + sheetName);
        }

        List<Integer> columnIndices = getColumnIndices(headerRow, columns);

        List<Object[]> dataList = new ArrayList<>();
        for (int i = 1; i <= sheet.getLastRowNum(); i++) {
            Row row = sheet.getRow(i);

            if (row == null || isRowEmpty(row)) {
                continue;
            }

            Object[] rowData = new Object[columnIndices.size()];
            for (int j = 0; j < columnIndices.size(); j++) {
                Cell cell = row.getCell(columnIndices.get(j));
                rowData[j] = getCellValue(cell);
            }
            dataList.add(rowData);
        }

        workbook.close();

        Object[][] data = new Object[dataList.size()][columnIndices.size()];
        return dataList.toArray(data);
    }

    private static boolean isRowEmpty(Row row) {
        for (int j = 0; j < row.getLastCellNum(); j++) {
            Cell cell = row.getCell(j);
            if (cell != null && cell.getCellType() != CellType.BLANK && getCellValue(cell).toString().trim().length() > 0) {
                return false;
            }
        }
        return true;
    }

    private static Object getCellValue(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue();
                } else {
                    return cell.getNumericCellValue();
                }
            case BOOLEAN:
                return cell.getBooleanCellValue();
            case FORMULA:
                return cell.getCellFormula();
            default:
                return "";
        }
    }

    private List<Integer> getColumnIndices(Row headerRow, List<String> columnNames) {
        List<Integer> indices = new ArrayList<>();
        for (Cell cell : headerRow) {
            if (columnNames.contains(cell.getStringCellValue())) {
                indices.add(cell.getColumnIndex());
            }
        }
        return indices;
    }
}




    

