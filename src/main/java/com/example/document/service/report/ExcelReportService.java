package com.example.document.service.report;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

import static com.example.document.util.DateTimeUtil.formatDateTime;

@Service
public class ExcelReportService {
    public <T> byte[] export(List<T> objects, ReportField[] fields) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            createHeaderRowOnSheet(sheet, fields);
            objects.forEach(object -> createRowOnSheet(sheet, object, fields));
            for (int i = 0; i< fields.length; i++) {
                sheet.autoSizeColumn(i);
            }
            try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {
                workbook.write(byteArrayOutputStream);
                return byteArrayOutputStream.toByteArray();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createRowOnSheet(Sheet sheet, Object object, ReportField[] fields) {
        var row = sheet.createRow(sheet.getLastRowNum() + 1);

        for(var field: fields) {
            var cell = row.createCell(field.getOrderNumber());
            var value = field.extractValue(object);
            if(value instanceof LocalDateTime) {
                cell.setCellValue(formatDateTime((LocalDateTime)value));
            } else if(value instanceof Boolean) {
                cell.setCellValue((Boolean) value ? "Да" : "Нет");
            } else if(value != null) {
                cell.setCellValue(value.toString());
            } else {
                cell.setCellValue("");
            }
        }
    }

    private void createHeaderRowOnSheet(Sheet sheet, ReportField[] fields) {
        var headerRow = sheet.createRow(0);
        for(var field: fields) {
            var cell = headerRow.createCell(field.getOrderNumber());
            cell.setCellValue(field.getTitleOfHeader());
        }
    }
}
