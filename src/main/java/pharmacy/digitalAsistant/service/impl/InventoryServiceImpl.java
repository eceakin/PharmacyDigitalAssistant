package pharmacy.digitalAsistant.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pharmacy.digitalAsistant.domain.entity.Inventory;
import pharmacy.digitalAsistant.domain.entity.Medication;
import pharmacy.digitalAsistant.domain.entity.StockTransaction;
import pharmacy.digitalAsistant.domain.enums.TransactionType;
import pharmacy.digitalAsistant.dto.request.InventoryRequest;
import pharmacy.digitalAsistant.dto.request.StockTransactionRequest;
import pharmacy.digitalAsistant.dto.response.InventoryResponse;
import pharmacy.digitalAsistant.dto.response.StockTransactionResponse;
import pharmacy.digitalAsistant.exception.InsufficientStockException;
import pharmacy.digitalAsistant.exception.ResourceNotFoundException;
import pharmacy.digitalAsistant.exception.ValidationException;
import pharmacy.digitalAsistant.repository.InventoryRepository;
import pharmacy.digitalAsistant.repository.MedicationRepository;
import pharmacy.digitalAsistant.repository.StockTransactionRepository;
import pharmacy.digitalAsistant.service.abstracts.InventoryService;
import pharmacy.digitalAsistant.util.ValidationUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class InventoryServiceImpl implements InventoryService {

    private final InventoryRepository inventoryRepository;
    private final MedicationRepository medicationRepository;
    private final StockTransactionRepository stockTransactionRepository;

    @Override
    public InventoryResponse createInventory(InventoryRequest request) {
        log.info("Creating inventory for medication ID: {}", request.getMedicationId());

        Medication medication = medicationRepository.findById(request.getMedicationId())
                .orElseThrow(() -> new ResourceNotFoundException("İlaç bulunamadı: " + request.getMedicationId()));

        // Validate stock quantity
        if (!ValidationUtil.isValidStockQuantity(request.getQuantity())) {
            throw new ValidationException("Geçersiz stok miktarı");
        }

        // Validate minimum stock level
        if (!ValidationUtil.isValidMinimumStockLevel(request.getMinimumStockLevel())) {
            throw new ValidationException("Geçersiz minimum stok seviyesi");
        }

        // Validate expiry date (must be in the future)
        if (request.getExpiryDate().isBefore(LocalDate.now())) {
            throw new ValidationException("Son kullanma tarihi geçmiş olamaz");
        }

        Inventory inventory = new Inventory();
        inventory.setMedication(medication);
        inventory.setQuantity(request.getQuantity());
        inventory.setMinimumStockLevel(request.getMinimumStockLevel());
        inventory.setExpiryDate(request.getExpiryDate());
        inventory.setBatchNumber(request.getBatchNumber());
        inventory.setPurchasePrice(request.getPurchasePrice());
        inventory.setSalePrice(request.getSalePrice());

        Inventory savedInventory = inventoryRepository.save(inventory);
        log.info("Inventory created successfully with ID: {}", savedInventory.getId());

        // Record initial stock transaction
        if (request.getQuantity() > 0) {
            StockTransaction transaction = new StockTransaction();
            transaction.setInventory(savedInventory);
            transaction.setTransactionType(TransactionType.IN);
            transaction.setQuantity(request.getQuantity());
            transaction.setPerformedBy(request.getPerformedBy() != null ? request.getPerformedBy() : "System");
            transaction.setReason("İlk stok girişi");
            transaction.setTransactionDate(LocalDateTime.now());
            stockTransactionRepository.save(transaction);
        }

        return mapToResponse(savedInventory);
    }

    @Override
    public InventoryResponse updateInventory(Long id, InventoryRequest request) {
        log.info("Updating inventory with ID: {}", id);

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Envanter kaydı bulunamadı: " + id));

        // Validate stock quantity
        if (!ValidationUtil.isValidStockQuantity(request.getQuantity())) {
            throw new ValidationException("Geçersiz stok miktarı");
        }

        // Validate minimum stock level
        if (!ValidationUtil.isValidMinimumStockLevel(request.getMinimumStockLevel())) {
            throw new ValidationException("Geçersiz minimum stok seviyesi");
        }

        inventory.setMinimumStockLevel(request.getMinimumStockLevel());
        inventory.setExpiryDate(request.getExpiryDate());
        inventory.setBatchNumber(request.getBatchNumber());
        inventory.setPurchasePrice(request.getPurchasePrice());
        inventory.setSalePrice(request.getSalePrice());

        // Note: Quantity updates should be done via stock transactions
        Inventory updatedInventory = inventoryRepository.save(inventory);
        log.info("Inventory updated successfully with ID: {}", updatedInventory.getId());

        return mapToResponse(updatedInventory);
    }

    @Override
    @Transactional(readOnly = true)
    public InventoryResponse getInventoryById(Long id) {
        log.info("Fetching inventory with ID: {}", id);

        Inventory inventory = inventoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Envanter kaydı bulunamadı: " + id));

        return mapToResponse(inventory);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getAllInventory() {
        log.info("Fetching all inventory");

        return inventoryRepository.findAllWithMedication().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteInventory(Long id) {
        log.info("Deleting inventory with ID: {}", id);

        if (!inventoryRepository.existsById(id)) {
            throw new ResourceNotFoundException("Envanter kaydı bulunamadı: " + id);
        }

        inventoryRepository.deleteById(id);
        log.info("Inventory deleted successfully with ID: {}", id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getLowStockItems() {
        log.info("Fetching low stock items");

        return inventoryRepository.findLowStockItems().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getExpiringItems(int months) {
        log.info("Fetching items expiring within {} months", months);

        LocalDate now = LocalDate.now();
        LocalDate targetDate = now.plusMonths(months);

        return inventoryRepository.findExpiringWithinMonths(now, targetDate).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<InventoryResponse> getExpiredItems() {
        log.info("Fetching expired items");

        return inventoryRepository.findExpiredItems(LocalDate.now()).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public StockTransactionResponse recordStockTransaction(Long inventoryId, StockTransactionRequest request) {
        log.info("Recording stock transaction for inventory ID: {}", inventoryId);

        Inventory inventory = inventoryRepository.findById(inventoryId)
                .orElseThrow(() -> new ResourceNotFoundException("Envanter kaydı bulunamadı: " + inventoryId));

        // Validate quantity
        if (request.getQuantity() == null || request.getQuantity() <= 0) {
            throw new ValidationException("Geçersiz miktar");
        }

        // Process transaction based on type
        if (request.getTransactionType() == TransactionType.IN) {
            inventory.addStock(request.getQuantity());
            log.info("Added {} items to inventory", request.getQuantity());
        } else if (request.getTransactionType() == TransactionType.OUT) {
            if (inventory.getQuantity() < request.getQuantity()) {
                throw new InsufficientStockException(
                        "Yetersiz stok. Mevcut: " + inventory.getQuantity() + ", İstenen: " + request.getQuantity()
                );
            }
            inventory.removeStock(request.getQuantity());
            log.info("Removed {} items from inventory", request.getQuantity());
        }

        inventoryRepository.save(inventory);

        // Create transaction record
        StockTransaction transaction = new StockTransaction();
        transaction.setInventory(inventory);
        transaction.setTransactionType(request.getTransactionType());
        transaction.setQuantity(request.getQuantity());
        transaction.setPerformedBy(request.getPerformedBy());
        transaction.setReason(request.getReason());
        transaction.setReferenceNumber(request.getReferenceNumber());
        transaction.setNotes(request.getNotes());
        transaction.setTransactionDate(LocalDateTime.now());

        StockTransaction savedTransaction = stockTransactionRepository.save(transaction);
        log.info("Stock transaction recorded successfully with ID: {}", savedTransaction.getId());

        return mapTransactionToResponse(savedTransaction);
    }

    @Override
    @Transactional(readOnly = true)
    public List<StockTransactionResponse> getInventoryTransactions(Long inventoryId) {
        log.info("Fetching transactions for inventory ID: {}", inventoryId);

        return stockTransactionRepository.findByInventoryId(inventoryId).stream()
                .map(this::mapTransactionToResponse)
                .collect(Collectors.toList());
    }

    // Helper methods
    private InventoryResponse mapToResponse(Inventory inventory) {
        InventoryResponse response = new InventoryResponse();
        response.setId(inventory.getId());
        response.setMedicationId(inventory.getMedication().getId());
        response.setMedicationName(inventory.getMedication().getName());
        response.setQuantity(inventory.getQuantity());
        response.setMinimumStockLevel(inventory.getMinimumStockLevel());
        response.setExpiryDate(inventory.getExpiryDate());
        response.setBatchNumber(inventory.getBatchNumber());
        response.setPurchasePrice(inventory.getPurchasePrice());
        response.setSalePrice(inventory.getSalePrice());
        response.setLowStock(inventory.isLowStock());
        response.setExpired(inventory.isExpired());
        response.setCreatedAt(inventory.getCreatedAt());
        response.setUpdatedAt(inventory.getUpdatedAt());
        return response;
    }

    private StockTransactionResponse mapTransactionToResponse(StockTransaction transaction) {
        StockTransactionResponse response = new StockTransactionResponse();
        response.setId(transaction.getId());
        response.setInventoryId(transaction.getInventory().getId());
        response.setMedicationName(transaction.getInventory().getMedication().getName());
        response.setTransactionType(transaction.getTransactionType());
        response.setQuantity(transaction.getQuantity());
        response.setPerformedBy(transaction.getPerformedBy());
        response.setReason(transaction.getReason());
        response.setReferenceNumber(transaction.getReferenceNumber());
        response.setNotes(transaction.getNotes());
        response.setTransactionDate(transaction.getTransactionDate());
        return response;
    }
}