package it.kevien.demo.inventoryservice.controller;

import it.kevien.demo.inventoryservice.model.dto.InventoryAdjustmentRequest;
import it.kevien.demo.inventoryservice.model.dto.InventoryRequest;
import it.kevien.demo.inventoryservice.model.dto.InventoryResponse;
import it.kevien.demo.inventoryservice.service.InventoryService;
import lombok.AllArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/inventory")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{productId}")
    public ResponseEntity<InventoryResponse> getInventory(@PathVariable Long productId) {
        InventoryResponse inventoryResponse = inventoryService.getInventory(productId);
        return ResponseEntity.ok(inventoryResponse);
    }

    @PostMapping
    public ResponseEntity<InventoryResponse> createInventory(@RequestBody InventoryRequest inventoryRequest) {
        InventoryResponse inventoryResponse = inventoryService.addInventory(inventoryRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(inventoryResponse);
    }

    @PatchMapping("/{productId}/decrease")
    public ResponseEntity<InventoryResponse> decreaseInventory(@PathVariable Long productId, @RequestBody InventoryAdjustmentRequest inventoryAdjustmentRequest) {
        InventoryResponse inventoryResponse = inventoryService.decreaseInventory(productId, inventoryAdjustmentRequest.quantity());
        return ResponseEntity.ok(inventoryResponse);
    }

    @PatchMapping("/{productId}/increase")
    public ResponseEntity<InventoryResponse> increaseInventory(@PathVariable Long productId, @RequestBody InventoryAdjustmentRequest inventoryAdjustmentRequest) {
        InventoryResponse inventoryResponse = inventoryService.increaseInventory(productId, inventoryAdjustmentRequest.quantity());
        return ResponseEntity.ok(inventoryResponse);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<InventoryResponse> deleteInventory(@PathVariable Long productId) {
        inventoryService.deleteInventory(productId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
