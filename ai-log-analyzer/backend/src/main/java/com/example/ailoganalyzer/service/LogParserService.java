package com.example.ailoganalyzer.service;

import org.springframework.stereotype.Service;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LogParserService {

    private static final List<String> IMPORTANT_KEYWORDS = List.of(
            "ERROR", "WARN", "Exception", "Timeout", "timeout", "failed", "FAILED",
            "Kafka", "database", "DB", "connection", "500", "NullPointerException",
            "OutOfMemory", "DataBufferLimitException", "Redis", "SSL", "authentication"
    );

    public String extractImportantLines(String logContent) {
        String extracted = Arrays.stream(logContent.split("\\R"))
                .filter(this::containsImportantKeyword)
                .limit(120)
                .collect(Collectors.joining("\n"));

        if (extracted.isBlank()) {
            return Arrays.stream(logContent.split("\\R"))
                    .limit(80)
                    .collect(Collectors.joining("\n"));
        }
        return extracted;
    }

    private boolean containsImportantKeyword(String line) {
        return IMPORTANT_KEYWORDS.stream().anyMatch(line::contains);
    }
}
