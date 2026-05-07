package it.kevien.demo.inventoryservice.model.mapper;

import it.kevien.demo.inventoryservice.model.Inventory;
import it.kevien.demo.inventoryservice.model.dto.InventoryRequest;
import it.kevien.demo.inventoryservice.model.dto.InventoryResponse;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface InventoryMapper {
    Inventory toEntity(InventoryRequest inventoryRequest);
    InventoryResponse toDto(Inventory inventory);
}
