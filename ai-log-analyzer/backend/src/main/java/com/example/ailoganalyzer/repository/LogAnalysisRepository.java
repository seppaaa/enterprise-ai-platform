package com.example.ailoganalyzer.repository;

import com.example.ailoganalyzer.entity.LogAnalysis;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LogAnalysisRepository extends JpaRepository<LogAnalysis, Long> {
    List<LogAnalysis> findAllByOrderByCreatedAtDesc();
}
