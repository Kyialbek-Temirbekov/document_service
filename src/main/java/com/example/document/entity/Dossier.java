package com.example.document.entity;

import com.example.document.dto.DossierDto;
import com.example.document.util.FileUtil;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Dossier {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dossier_id_gen")
    @SequenceGenerator(name = "dossier_id_gen", sequenceName = "dossier_seq", allocationSize = 1)
    private Long id;
    private String title;
    private String description;
    private String objectName;
    @ManyToOne
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    private Customer customer;

    public DossierDto toDossierDto(byte[] file) {
        return DossierDto.builder()
                .title(title)
                .description(description)
                .base64file(FileUtil.getBase64File(file))
                .build();
    }
}
