package com.example.ailoganalyzer.controller;

import com.example.ailoganalyzer.dto.AnalysisResponse;
import com.example.ailoganalyzer.service.LogAnalysisService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/logs")
@CrossOrigin(origins = "http://localhost:5173")
public class LogAnalysisController {

    private final LogAnalysisService service;

    public LogAnalysisController(LogAnalysisService service) {
        this.service = service;
    }

    @PostMapping(value = "/analyze", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AnalysisResponse analyze(@RequestParam("file") MultipartFile file) throws Exception {
        return service.analyzeLogFile(file);
    }

    @GetMapping("/{id}")
    public AnalysisResponse getById(@PathVariable Long id) {
        return service.getById(id);
    }

    @GetMapping
    public List<AnalysisResponse> getAll() {
        return service.getAll();
    }
}
