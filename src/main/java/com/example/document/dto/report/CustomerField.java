package com.example.document.dto.report;

import com.example.document.entity.Customer;
import com.example.document.service.report.ReportField;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.function.Function;

@AllArgsConstructor
@Getter
public enum CustomerField implements ReportField {
    ID("№", Customer::getId),
    EMAIL("Имейл", Customer::getUsername),
    FIRST_NAME("Имя", Customer::getFirstName),
    LAST_NAME("Фамилия", Customer::getLastName),
    CREATED_AT("Дата создания", Customer::getCreatedAt),
    LAST_SIGN_IN_AT("Последний вход", Customer::getLastSignInAt),
    ACTIVE("Активный", Customer::isEnabled);

    private final String title;
    private final Function<Customer, Object> valueExtractor;


    @Override
    public int getOrderNumber() {
        return this.ordinal();
    }

    @Override
    public String getTitleOfHeader() {
        return this.title;
    }

    @Override
    public Object extractValue(Object object) {
        return this.valueExtractor.apply((Customer) object);
    }
}
