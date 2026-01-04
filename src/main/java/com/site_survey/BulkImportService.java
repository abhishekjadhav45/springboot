package com.site_survey;

import com.opencsv.CSVReader;
import com.opencsv.exceptions.CsvException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class BulkImportService {

    public List<Space> parseCsv(MultipartFile file) throws IOException, CsvException {
        List<Space> spaces = new ArrayList<>();
        try (CSVReader csvReader = new CSVReader(new InputStreamReader(file.getInputStream()))) {
            List<String[]> records = csvReader.readAll();
            for (String[] record : records) {
                if (record.length >= 3) {
                    Space space = new Space();
                    space.setName(record[0]);
                    space.setType(record[1]);
                    space.setArea(Double.parseDouble(record[2]));
                    spaces.add(space);
                }
            }
        }
        return spaces;
    }

    public List<Space> parseXlsx(MultipartFile file) throws IOException {
        List<Space> spaces = new ArrayList<>();
        try (Workbook workbook = new XSSFWorkbook(file.getInputStream())) {
            Sheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                if (row.getRowNum() == 0) continue; // Skip header
                Space space = new Space();
                
                // Add null checks to prevent NPE
                Cell nameCell = row.getCell(0);
                if (nameCell != null) {
                    space.setName(nameCell.getStringCellValue());
                }
                
                Cell typeCell = row.getCell(1);
                if (typeCell != null) {
                    space.setType(typeCell.getStringCellValue());
                }
                
                Cell areaCell = row.getCell(2);
                if (areaCell != null) {
                    space.setArea(areaCell.getNumericCellValue());
                }
                
                spaces.add(space);
            }
        }
        return spaces;
    }
}