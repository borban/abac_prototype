package com.orban.abac_prototype;

import org.springframework.web.bind.annotation.*;
import java.util.*;
import org.springframework.security.access.prepost.PreAuthorize;


@RestController @RequestMapping("/documents")
public class DocumentController {
    private final DocumentRepository repo;
    public DocumentController(DocumentRepository repo){ this.repo = repo; }


    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('doc_reader','admin')") // RBAC baseline
    public Document get(@PathVariable UUID id){ return repo.findById(id).orElseThrow(); }
}
