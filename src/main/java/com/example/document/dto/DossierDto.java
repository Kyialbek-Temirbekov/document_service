package com.example.document.dto;

import com.example.document.entity.Customer;
import com.example.document.entity.Dossier;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DossierDto {
    private String title;
    private String description;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String base64file;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private MultipartFile multipartFile;

    public Dossier toDossier(String objectName, Customer customer) {
        return Dossier.builder()
                .title(title)
                .description(description)
                .objectName(objectName)
                .customer(customer)
                .build();
    }
}
