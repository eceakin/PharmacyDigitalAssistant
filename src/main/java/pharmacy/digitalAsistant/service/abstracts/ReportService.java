package pharmacy.digitalAsistant.service.abstracts;

import pharmacy.digitalAsistant.dto.response.ReportResponse;

public interface ReportService {
    ReportResponse generateDashboardReport();
    ReportResponse generateInventoryReport();
    ReportResponse generateNotificationReport();
}