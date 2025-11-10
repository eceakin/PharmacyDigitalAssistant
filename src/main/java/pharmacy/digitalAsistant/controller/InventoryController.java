package pharmacy.digitalAsistant.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pharmacy.digitalAsistant.dto.request.InventoryRequest;
import pharmacy.digitalAsistant.dto.request.StockTransactionRequest;
import pharmacy.digitalAsistant.dto.response.InventoryResponse;
import pharmacy.digitalAsistant.dto.response.StockTransactionResponse;
import pharmacy.digitalAsistant.service.abstracts.InventoryService;
import pharmacy.digitalAsistant.util.ResponseUtil;

import java.util.List;

@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    /**
     * Create new inventory entry
     * POST /api/inventory
     */
    @PostMapping
    public ResponseEntity<?> createInventory(@Valid @RequestBody InventoryRequest request) {
        InventoryResponse response = inventoryService.createInventory(request);
        return ResponseUtil.created(response, "Envanter kaydı başarıyla oluşturuldu");
    }

    /**
     * Update inventory entry
     * PUT /api/inventory/{id}
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateInventory(
            @PathVariable Long id,
            @Valid @RequestBody InventoryRequest request) {
        InventoryResponse response = inventoryService.updateInventory(id, request);
        return ResponseUtil.success(response, "Envanter kaydı başarıyla güncellendi");
    }

    /**
     * Get inventory by ID
     * GET /api/inventory/{id}
     */
    @GetMapping("/{id}")
    public ResponseEntity<?> getInventoryById(@PathVariable Long id) {
        InventoryResponse response = inventoryService.getInventoryById(id);
        return ResponseUtil.success(response);
    }

    /**
     * Get all inventory
     * GET /api/inventory
     */
    @GetMapping
    public ResponseEntity<?> getAllInventory() {
        List<InventoryResponse> responses = inventoryService.getAllInventory();
        return ResponseUtil.success(responses);
    }

    /**
     * Delete inventory entry
     * DELETE /api/inventory/{id}
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteInventory(@PathVariable Long id) {
        inventoryService.deleteInventory(id);
        return ResponseUtil.success("Envanter kaydı başarıyla silindi");
    }

    /**
     * Get low stock items
     * GET /api/inventory/low-stock
     */
    @GetMapping("/low-stock")
    public ResponseEntity<?> getLowStockItems() {
        List<InventoryResponse> responses = inventoryService.getLowStockItems();
        return ResponseUtil.success(responses);
    }

    /**
     * Get expiring items (within specified months)
     * GET /api/inventory/expiring?months=3
     */
    @GetMapping("/expiring")
    public ResponseEntity<?> getExpiringItems(@RequestParam(defaultValue = "3") int months) {
        List<InventoryResponse> responses = inventoryService.getExpiringItems(months);
        return ResponseUtil.success(responses);
    }

    /**
     * Get expired items
     * GET /api/inventory/expired
     */
    @GetMapping("/expired")
    public ResponseEntity<?> getExpiredItems() {
        List<InventoryResponse> responses = inventoryService.getExpiredItems();
        return ResponseUtil.success(responses);
    }

    /**
     * Record stock transaction (IN/OUT)
     * POST /api/inventory/{id}/transaction
     */
    @PostMapping("/{id}/transaction")
    public ResponseEntity<?> recordStockTransaction(
            @PathVariable Long id,
            @Valid @RequestBody StockTransactionRequest request) {
        StockTransactionResponse response = inventoryService.recordStockTransaction(id, request);
        return ResponseUtil.created(response, "Stok hareketi başarıyla kaydedildi");
    }

    /**
     * Get inventory transactions
     * GET /api/inventory/{id}/transactions
     */
    @GetMapping("/{id}/transactions")
    public ResponseEntity<?> getInventoryTransactions(@PathVariable Long id) {
        List<StockTransactionResponse> responses = inventoryService.getInventoryTransactions(id);
        return ResponseUtil.success(responses);
    }
}