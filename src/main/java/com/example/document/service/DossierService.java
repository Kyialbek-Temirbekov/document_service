package com.example.document.service;

import com.example.document.dto.DossierDto;
import org.springframework.web.multipart.MultipartFile;

public interface DossierService {
    DossierDto get(Long id);
    void save(DossierDto dossierDto, MultipartFile file);
    void update(DossierDto dossierDto, Long id, MultipartFile file);
    void delete(Long id);

    String addFile(MultipartFile file);
    void modifyFile(String objectName, MultipartFile file);
}
