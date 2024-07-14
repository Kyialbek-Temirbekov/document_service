package com.example.document.service.impl;

import com.example.document.entity.Customer;
import com.example.document.entity.CustomerFields;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class CustomerReportService  {
    public byte[] export(List<Customer> customers) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            createHeaderRowOnSheet(sheet);
            customers.forEach(customer -> createRowOnSheet(sheet,customer));
            for (int i=0; i<CustomerFields.values().length; i++) {
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

    private void createRowOnSheet(Sheet sheet, Customer customer) {
        var row = sheet.createRow(sheet.getLastRowNum() + 1);

        row.createCell(CustomerFields.ID.ordinal()).setCellValue(customer.getId());
        row.createCell(CustomerFields.EMAIL.ordinal()).setCellValue(customer.getUsername());
        row.createCell(CustomerFields.FIRST_NAME.ordinal()).setCellValue(customer.getFirstName());
        row.createCell(CustomerFields.LAST_NAME.ordinal()).setCellValue(customer.getLastName());
        row.createCell(CustomerFields.LAST_SIGN_IN_AT.ordinal()).setCellValue(customer.getLastSignInAt());
        row.createCell(CustomerFields.ACTIVE.ordinal()).setCellValue(customer.isEnabled());
        var dateCell = row.createCell(CustomerFields.CREATED_AT.ordinal());
        dateCell.setCellValue(customer.getCreatedAt());
        CellStyle cellStyle = sheet.getWorkbook().createCellStyle();
        cellStyle.setDataFormat(sheet.getWorkbook().getCreationHelper().createDataFormat().getFormat("yyyy-MM-dd HH:mm:ss"));
        dateCell.setCellStyle(cellStyle);
    }

    private void createHeaderRowOnSheet(Sheet sheet) {
        var headerRow = sheet.createRow(0);
        for(var field: CustomerFields.values()) {
            var cell = headerRow.createCell(field.ordinal());
            cell.setCellValue(field.name());
        }
    }
}
