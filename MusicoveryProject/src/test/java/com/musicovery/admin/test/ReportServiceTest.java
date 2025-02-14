package com.musicovery.admin.test;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.musicovery.admin.entity.Report;
import com.musicovery.admin.service.ReportService;

import lombok.extern.slf4j.Slf4j;

@SpringBootTest
@Slf4j
public class ReportServiceTest {

	@Autowired
	private ReportService reportService;

	/* 신고 목록 조회 테스트 */
	@Test
	public void getAllReportsTest() {
		List<Report> reports = reportService.getAllReports();
		log.info("신고 목록: " + reports);
	}

	/* 특정 상태의 신고 조회 테스트 */
	@Test
	public void getReportsByStatusTest() {
		List<Report> reports = reportService.getReportsByStatus("처리중");
		log.info("처리중 신고 목록: " + reports);
	}

	/* 신고 상태 변경 테스트 */
	@Test
	public void updateReportStatusTest() {
		boolean result = reportService.updateReportStatus(1L, "완료");
		log.info("신고 상태 변경 결과: " + result);
	}
}
