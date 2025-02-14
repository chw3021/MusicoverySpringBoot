package com.musicovery.admin.controller;

import com.musicovery.admin.entity.Report;
import com.musicovery.admin.service.ReportService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/reports")
public class ReportController {
    private final ReportService reportService;

    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    // 신고 목록 조회
    @GetMapping
    public List<Report> getAllReports() {
        return reportService.getAllReports();
    }

    // 특정 상태의 신고 조회
    @GetMapping("/status/{status}")
    public List<Report> getReportsByStatus(@PathVariable String status) {
        return reportService.getReportsByStatus(status);
    }

    // 신고 상태 변경
    @PutMapping("/{reportId}/status")
    public boolean updateReportStatus(@PathVariable Long reportId, @RequestParam String status) {
        return reportService.updateReportStatus(reportId, status);
    }
}
