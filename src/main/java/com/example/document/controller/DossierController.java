package com.example.document.controller;

import com.example.document.dto.DossierDto;
import com.example.document.service.DossierService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Document Service")
@RestController
@RequestMapping("/document")
@RequiredArgsConstructor
public class DossierController {
    private final DossierService dossierService;

    @GetMapping("/{id}")
    ResponseEntity<DossierDto> get(@PathVariable Long id) {
        return new ResponseEntity<>(dossierService.get(id), HttpStatus.OK);
    }
    @PostMapping
    ResponseEntity<String> save(@RequestPart DossierDto document,@RequestPart MultipartFile file) {
        dossierService.save(document, file);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }
    @PutMapping("/{id}")
    ResponseEntity<String> update(@ModelAttribute DossierDto dossierDto, @PathVariable Long id) {
        dossierService.update(dossierDto, id, dossierDto.getMultipartFile());
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @DeleteMapping("/{id}")
    ResponseEntity<String> delete(@PathVariable Long id) {
        dossierService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
