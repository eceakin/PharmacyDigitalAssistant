package pharmacy.digitalAsistant.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy.digitalAsistant.dto.response.ReportResponse;
import pharmacy.digitalAsistant.service.abstracts.ReportService;
import pharmacy.digitalAsistant.util.ResponseUtil;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/dashboard")
    public ResponseEntity<?> getDashboardReport() {
        ReportResponse response = reportService.generateDashboardReport();
        return ResponseUtil.success(response);
    }

    @GetMapping("/inventory")
    public ResponseEntity<?> getInventoryReport() {
        ReportResponse response = reportService.generateInventoryReport();
        return ResponseUtil.success(response);
    }

    @GetMapping("/notifications")
    public ResponseEntity<?> getNotificationReport() {
        ReportResponse response = reportService.generateNotificationReport();
        return ResponseUtil.success(response);
    }
}