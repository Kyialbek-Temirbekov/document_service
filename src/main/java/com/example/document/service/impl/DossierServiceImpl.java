package com.example.document.service.impl;

import com.example.document.dto.DossierDto;
import com.example.document.entity.Dossier;
import com.example.document.exception.MinioException;
import com.example.document.repository.DossierRepository;
import com.example.document.service.CustomerService;
import com.example.document.service.DossierService;
import com.example.document.service.minio.MinioService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DossierServiceImpl implements DossierService {
    private final MinioService minioService;
    private final DossierRepository dossierRepository;
    private final CustomerService customerService;

    @Override
    @Transactional(readOnly = true)
    public DossierDto get(Long id) {
        try {
            Dossier dossier = dossierRepository.findById(id).orElseThrow(RuntimeException::new);

            var file = minioService.getObject(dossier.getObjectName());
            return dossier.toDossierDto(file);
        } catch (RuntimeException e) {
            throw new MinioException(e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional
    public void save(DossierDto dossierDto, MultipartFile file) {
        try {
            var customer = customerService.getLoggedInUser();
            var objectName = addFile(file);
            Dossier dossier = dossierDto.toDossier(objectName, customer);
            dossierRepository.save(dossier);
        } catch (Exception e) {
            throw new MinioException(e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional
    public void update(DossierDto dossierDto, Long id, MultipartFile file) {
        try {
            Dossier dossier = dossierRepository.findById(id).orElseThrow(RuntimeException::new);

            dossier.setTitle(dossierDto.getTitle());
            dossier.setDescription(dossierDto.getDescription());
            modifyFile(dossier.getObjectName(), file);
        } catch (RuntimeException e) {
            throw new MinioException(e.getLocalizedMessage());
        }
    }

    @Override
    @Transactional
    public void delete(Long id) {
        try {
            Dossier dossier = dossierRepository.findById(id).orElseThrow(RuntimeException::new);

            dossierRepository.deleteById(dossier.getId());
            minioService.removeObject(dossier.getObjectName());
        } catch (RuntimeException e) {
            throw new MinioException(e.getLocalizedMessage());
        }
    }

    @Override
    public String addFile(MultipartFile file) {
        if (!file.isEmpty()) {
            var objectName = getId();
            try {
                minioService.putObject(objectName, file.getBytes());
            } catch (IOException e) {
                throw new MinioException(e.getLocalizedMessage());
            }
            return objectName;
        }
        throw new IllegalArgumentException("File must not be empty");
    }

    @Override
    public void modifyFile(String objectName, MultipartFile file) {
        if (!file.isEmpty()) {
            try {
                minioService.putObject(objectName, file.getBytes());
            } catch (IOException e) {
                throw new MinioException(e.getLocalizedMessage());
            }
        }
        throw new IllegalArgumentException("File must not be empty");
    }

    private String getId() {
        return UUID.randomUUID().toString();
    }
}
