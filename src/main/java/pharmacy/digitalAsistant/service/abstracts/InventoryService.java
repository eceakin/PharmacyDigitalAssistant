package pharmacy.digitalAsistant.service.abstracts;


import pharmacy.digitalAsistant.dto.request.InventoryRequest;
import pharmacy.digitalAsistant.dto.request.StockTransactionRequest;
import pharmacy.digitalAsistant.dto.response.InventoryResponse;
import pharmacy.digitalAsistant.dto.response.StockTransactionResponse;

import java.util.List;

public interface InventoryService {
    
    InventoryResponse createInventory(InventoryRequest request);
    InventoryResponse updateInventory(Long id, InventoryRequest request);
    InventoryResponse getInventoryById(Long id);
    List<InventoryResponse> getAllInventory();
    void deleteInventory(Long id);
    
    List<InventoryResponse> getLowStockItems();
    List<InventoryResponse> getExpiringItems(int months);
    List<InventoryResponse> getExpiredItems();
    
    StockTransactionResponse recordStockTransaction(Long inventoryId, StockTransactionRequest request);
    List<StockTransactionResponse> getInventoryTransactions(Long inventoryId);
}