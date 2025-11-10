package pharmacy.digitalAsistant.service.impl;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pharmacy.digitalAsistant.dto.response.ReportResponse;
import pharmacy.digitalAsistant.repository.*;
import pharmacy.digitalAsistant.service.abstracts.ReportService;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final PatientRepository patientRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final InventoryRepository inventoryRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public ReportResponse generateDashboardReport() {
        log.info("Generating dashboard report");

        Map<String, Object> data = new HashMap<>();
        
        // Patient statistics
        data.put("totalPatients", patientRepository.countTotalPatients());
        data.put("patientsWithChronicDiseases", patientRepository.findPatientsWithChronicDiseases().size());
        
        // Prescription statistics
        data.put("activePrescriptions", prescriptionRepository.countActivePrescriptions());
        data.put("expiringPrescriptions", prescriptionRepository.findExpiringWithinDays(
                LocalDate.now(), LocalDate.now().plusDays(7)).size());
        
        // Inventory statistics
        data.put("totalInventoryItems", inventoryRepository.countTotalInventoryItems());
        data.put("lowStockItems", inventoryRepository.countLowStockItems());
        data.put("expiringItemsThreeMonths", inventoryRepository.findExpiringWithinMonths(
                LocalDate.now(), LocalDate.now().plusMonths(3)).size());
        
        // Notification statistics
        data.put("totalNotificationsSent", notificationRepository.countByStatus(
                pharmacy.digitalAsistant.domain.enums.NotificationStatus.SENT));
        data.put("failedNotifications", notificationRepository.countByStatus(
                pharmacy.digitalAsistant.domain.enums.NotificationStatus.FAILED));

        ReportResponse response = new ReportResponse();
        response.setReportType("Dashboard");
        response.setGeneratedAt(java.time.LocalDateTime.now());
        response.setData(data);
        
        return response;
    }

    @Override
    public ReportResponse generateInventoryReport() {
        log.info("Generating inventory report");

        Map<String, Object> data = new HashMap<>();
        
        // Low stock medications
        data.put("lowStockMedications", inventoryRepository.findLowStockItems().stream()
                .map(inv -> Map.of(
                        "medicationName", inv.getMedication().getName(),
                        "currentStock", inv.getQuantity(),
                        "minimumStock", inv.getMinimumStockLevel()
                ))
                .toList());
        
        // Expiring medications (3 months)
        data.put("expiringMedicationsThreeMonths", inventoryRepository.findExpiringWithinMonths(
                LocalDate.now(), LocalDate.now().plusMonths(3)).stream()
                .limit(10)
                .map(inv -> Map.of(
                        "medicationName", inv.getMedication().getName(),
                        "expiryDate", inv.getExpiryDate(),
                        "quantity", inv.getQuantity()
                ))
                .toList());
        
        // Expiring medications (6 months)
        data.put("expiringMedicationsSixMonths", inventoryRepository.findExpiringWithinMonths(
                LocalDate.now(), LocalDate.now().plusMonths(6)).stream()
                .limit(10)
                .map(inv -> Map.of(
                        "medicationName", inv.getMedication().getName(),
                        "expiryDate", inv.getExpiryDate(),
                        "quantity", inv.getQuantity()
                ))
                .toList());

        ReportResponse response = new ReportResponse();
        response.setReportType("Inventory");
        response.setGeneratedAt(java.time.LocalDateTime.now());
        response.setData(data);
        
        return response;
    }

    @Override
    public ReportResponse generateNotificationReport() {
        log.info("Generating notification report");

        Map<String, Object> data = new HashMap<>();
        
        // Top notified patients
        List<Object[]> topNotified = notificationRepository.findTopNotifiedPatients();
        data.put("topNotifiedPatients", topNotified.stream()
                .limit(10)
                .map(row -> Map.of(
                        "patientName", row[1] + " " + row[2],
                        "notificationCount", row[3]
                ))
                .toList());

        ReportResponse response = new ReportResponse();
        response.setReportType("Notification");
        response.setGeneratedAt(java.time.LocalDateTime.now());
        response.setData(data);
        
        return response;
    }
}
