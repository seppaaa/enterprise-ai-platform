package com.example.ailoganalyzer.service;

import com.example.ailoganalyzer.dto.AnalysisResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AiAnalysisService {

    @Value("${ai.provider:mock}")
    private String aiProvider;

    public AnalysisResponse analyze(String fileName, String importantLogLines) {
        // This starter project uses mock AI by default so you can run it without API keys.
        // Later you can replace this method with Spring AI/OpenAI ChatClient implementation.
        AnalysisResponse response = new AnalysisResponse();
        response.setFileName(fileName);
        response.setSeverity(detectSeverity(importantLogLines));
        response.setImpactedService(detectService(importantLogLines));
        response.setSummary(buildSummary(importantLogLines));
        response.setRootCause(buildRootCause(importantLogLines));
        response.setSuggestedFix(buildSuggestedFix(importantLogLines));
        response.setRcaSummary("The uploaded logs indicate a production issue that should be reviewed by checking the impacted service, related dependencies, timeout settings, and recent deployment changes.");
        response.setStatus("COMPLETED");
        return response;
    }

    private String detectSeverity(String logs) {
        if (logs.contains("OutOfMemory") || logs.contains("CRITICAL") || logs.contains("500")) return "CRITICAL";
        if (logs.contains("ERROR") || logs.contains("Exception") || logs.contains("Timeout")) return "HIGH";
        if (logs.contains("WARN")) return "MEDIUM";
        return "LOW";
    }

    private String detectService(String logs) {
        if (logs.contains("PaymentService")) return "PaymentService";
        if (logs.contains("OrderService")) return "OrderService";
        if (logs.contains("Kafka")) return "Kafka/Event Processing Service";
        if (logs.contains("Redis")) return "Redis Cache Layer";
        if (logs.contains("database") || logs.contains("DB")) return "Database Layer";
        return "Application Service";
    }

    private String buildSummary(String logs) {
        if (logs.contains("Kafka")) return "The logs show Kafka-related failures or timeout issues during event processing.";
        if (logs.contains("Redis")) return "The logs show Redis connectivity or cache access issues.";
        if (logs.contains("database") || logs.contains("DB")) return "The logs show database connectivity or query execution issues.";
        if (logs.contains("NullPointerException")) return "The logs show a NullPointerException, likely due to missing or unexpected data.";
        return "The logs contain warning or error patterns that require investigation.";
    }

    private String buildRootCause(String logs) {
        if (logs.contains("Timeout")) return "A downstream dependency may be slow or unavailable, causing request timeout.";
        if (logs.contains("Kafka")) return "Kafka broker, topic configuration, producer timeout, or network connectivity may be causing failures.";
        if (logs.contains("Redis")) return "Redis endpoint, SSL configuration, credentials, or network routing may be incorrect.";
        if (logs.contains("NullPointerException")) return "The application may be trying to access a null object because validation or null checks are missing.";
        return "Root cause requires checking dependency health, recent code changes, and application configuration.";
    }

    private String buildSuggestedFix(String logs) {
        if (logs.contains("Kafka")) return "Check Kafka broker health, topic availability, producer retries, timeout values, and SSL configuration.";
        if (logs.contains("Redis")) return "Verify Redis host, port, SSL setting, private endpoint/VNet routing, credentials, and firewall rules.";
        if (logs.contains("database") || logs.contains("DB")) return "Check database connection pool, query performance, credentials, and network connectivity.";
        if (logs.contains("NullPointerException")) return "Add null checks, validate input payloads, and review the object initialization flow.";
        return "Review the full stack trace, recent deployment, configuration changes, and dependency availability.";
    }
}
