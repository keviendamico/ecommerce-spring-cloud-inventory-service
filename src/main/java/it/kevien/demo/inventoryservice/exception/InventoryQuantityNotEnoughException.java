package it.kevien.demo.inventoryservice.exception;

public class InventoryQuantityNotEnoughException extends RuntimeException {

    public InventoryQuantityNotEnoughException(String message) {
        super(message);
    }
}
