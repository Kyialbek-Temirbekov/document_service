package com.example.document.service.report;

public interface ReportField {
    int getOrderNumber();
    String getTitleOfHeader();
    Object extractValue(Object object);
}
