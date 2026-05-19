package com.example.ailoganalyzer.dto;

public class AnalysisResponse {
    private Long id;
    private String fileName;
    private String summary;
    private String severity;
    private String impactedService;
    private String rootCause;
    private String suggestedFix;
    private String rcaSummary;
    private String status;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFileName() { return fileName; }
    public void setFileName(String fileName) { this.fileName = fileName; }
    public String getSummary() { return summary; }
    public void setSummary(String summary) { this.summary = summary; }
    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }
    public String getImpactedService() { return impactedService; }
    public void setImpactedService(String impactedService) { this.impactedService = impactedService; }
    public String getRootCause() { return rootCause; }
    public void setRootCause(String rootCause) { this.rootCause = rootCause; }
    public String getSuggestedFix() { return suggestedFix; }
    public void setSuggestedFix(String suggestedFix) { this.suggestedFix = suggestedFix; }
    public String getRcaSummary() { return rcaSummary; }
    public void setRcaSummary(String rcaSummary) { this.rcaSummary = rcaSummary; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
