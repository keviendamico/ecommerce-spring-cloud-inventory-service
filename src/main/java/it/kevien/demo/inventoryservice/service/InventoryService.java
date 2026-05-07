package it.kevien.demo.inventoryservice.service;

import it.kevien.demo.inventoryservice.exception.InventoryNotFoundException;
import it.kevien.demo.inventoryservice.exception.InventoryQuantityNotEnoughException;
import it.kevien.demo.inventoryservice.model.Inventory;
import it.kevien.demo.inventoryservice.model.dto.InventoryRequest;
import it.kevien.demo.inventoryservice.model.dto.InventoryResponse;
import it.kevien.demo.inventoryservice.model.mapper.InventoryMapper;
import it.kevien.demo.inventoryservice.repository.InventoryRepository;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    public InventoryResponse getInventory(Long productId) {
        Optional<Inventory> opt = inventoryRepository.findByProductId(productId);
        if (opt.isPresent()) {
            Inventory inventory = opt.get();
            return inventoryMapper.toDto(inventory);
        }
        throw new InventoryNotFoundException("Inventory not found for product ID: " + productId);
    }

    public InventoryResponse decreaseInventory(Long productId, Integer quantity) {
        Optional<Inventory> opt = inventoryRepository.findByProductId(productId);
        if (opt.isPresent()) {
            Inventory inventory = opt.get();
            if (inventory.getQuantity() < quantity) {
                throw new InventoryQuantityNotEnoughException("Inventory quantity less than product quantity. Product ID: " + productId);
            }
            inventory.setQuantity(inventory.getQuantity() - quantity);
            inventoryRepository.save(inventory);
            return inventoryMapper.toDto(inventory);
        }
        throw new InventoryNotFoundException("Inventory not found for product ID: " + productId);
    }

    public InventoryResponse increaseInventory(Long productId, Integer quantity) {
        Optional<Inventory> opt = inventoryRepository.findByProductId(productId);
        if (opt.isPresent()) {
            Inventory inventory = opt.get();
            inventory.setQuantity(inventory.getQuantity() + quantity);
            inventoryRepository.save(inventory);
            return inventoryMapper.toDto(inventory);
        }
        throw new InventoryNotFoundException("Inventory not found for product ID: " + productId);
    }

    public InventoryResponse addInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = inventoryMapper.toEntity(inventoryRequest);
        inventoryRepository.save(inventory);
        return inventoryMapper.toDto(inventory);
    }

    public void deleteInventory(Long productId) {
        Optional<Inventory> opt = inventoryRepository.findByProductId(productId);
        if (opt.isPresent()) {
            Inventory inventory = opt.get();
            inventoryRepository.delete(inventory);
            return;
        }
        throw new InventoryNotFoundException("Inventory not found for product ID: " + productId);
    }

}
