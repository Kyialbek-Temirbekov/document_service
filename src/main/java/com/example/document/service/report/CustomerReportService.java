package com.example.document.service.report;

import com.example.document.entity.Customer;
import com.example.document.dto.report.CustomerField;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import static com.example.document.util.DateTimeUtil.formatDateTime;

@Service
public class CustomerReportService  {
    public byte[] export(List<Customer> customers) {
        try (XSSFWorkbook workbook = new XSSFWorkbook()) {
            Sheet sheet = workbook.createSheet();
            createHeaderRowOnSheet(sheet);
            customers.forEach(customer -> createRowOnSheet(sheet,customer));
            for (int i = 0; i< CustomerField.values().length; i++) {
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

        row.createCell(CustomerField.ID.ordinal()).setCellValue(customer.getId());
        row.createCell(CustomerField.EMAIL.ordinal()).setCellValue(customer.getUsername());
        row.createCell(CustomerField.FIRST_NAME.ordinal()).setCellValue(customer.getFirstName());
        row.createCell(CustomerField.LAST_NAME.ordinal()).setCellValue(customer.getLastName());
        row.createCell(CustomerField.LAST_SIGN_IN_AT.ordinal()).setCellValue(formatDateTime(customer.getLastSignInAt()));
        row.createCell(CustomerField.ACTIVE.ordinal()).setCellValue(customer.isEnabled());
        row.createCell(CustomerField.CREATED_AT.ordinal()).setCellValue(formatDateTime(customer.getCreatedAt()));
    }

    private void createHeaderRowOnSheet(Sheet sheet) {

        var headerRow = sheet.createRow(0);
        for(var field: CustomerField.values()) {
            var cell = headerRow.createCell(field.ordinal());
            cell.setCellValue(field.name());
        }
    }
}
