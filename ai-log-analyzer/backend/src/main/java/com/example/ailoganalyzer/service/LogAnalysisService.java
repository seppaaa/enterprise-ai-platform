package com.example.ailoganalyzer.service;

import com.example.ailoganalyzer.dto.AnalysisResponse;
import com.example.ailoganalyzer.entity.LogAnalysis;
import com.example.ailoganalyzer.exception.ResourceNotFoundException;
import com.example.ailoganalyzer.repository.LogAnalysisRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;

@Service
public class LogAnalysisService {

    private final LogParserService logParserService;
    private final AiAnalysisService aiAnalysisService;
    private final LogAnalysisRepository repository;

    public LogAnalysisService(LogParserService logParserService,
                              AiAnalysisService aiAnalysisService,
                              LogAnalysisRepository repository) {
        this.logParserService = logParserService;
        this.aiAnalysisService = aiAnalysisService;
        this.repository = repository;
    }

    public AnalysisResponse analyzeLogFile(MultipartFile file) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new IllegalArgumentException("Please upload a valid log file.");
        }

        String fileName = file.getOriginalFilename() == null ? "uploaded-log.txt" : file.getOriginalFilename();
        String content = new String(file.getBytes(), StandardCharsets.UTF_8);
        String importantLines = logParserService.extractImportantLines(content);
        AnalysisResponse aiResponse = aiAnalysisService.analyze(fileName, importantLines);

        LogAnalysis saved = repository.save(toEntity(aiResponse));
        return toResponse(saved);
    }

    public AnalysisResponse getById(Long id) {
        LogAnalysis analysis = repository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Analysis not found for id: " + id));
        return toResponse(analysis);
    }

    public List<AnalysisResponse> getAll() {
        return repository.findAllByOrderByCreatedAtDesc()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    private LogAnalysis toEntity(AnalysisResponse response) {
        LogAnalysis entity = new LogAnalysis();
        entity.setFileName(response.getFileName());
        entity.setSummary(response.getSummary());
        entity.setSeverity(response.getSeverity());
        entity.setImpactedService(response.getImpactedService());
        entity.setRootCause(response.getRootCause());
        entity.setSuggestedFix(response.getSuggestedFix());
        entity.setRcaSummary(response.getRcaSummary());
        entity.setStatus(response.getStatus());
        return entity;
    }

    private AnalysisResponse toResponse(LogAnalysis entity) {
        AnalysisResponse response = new AnalysisResponse();
        response.setId(entity.getId());
        response.setFileName(entity.getFileName());
        response.setSummary(entity.getSummary());
        response.setSeverity(entity.getSeverity());
        response.setImpactedService(entity.getImpactedService());
        response.setRootCause(entity.getRootCause());
        response.setSuggestedFix(entity.getSuggestedFix());
        response.setRcaSummary(entity.getRcaSummary());
        response.setStatus(entity.getStatus());
        return response;
    }
}
