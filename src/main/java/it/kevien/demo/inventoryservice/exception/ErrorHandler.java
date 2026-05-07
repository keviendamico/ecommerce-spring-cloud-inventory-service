package it.kevien.demo.inventoryservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(InventoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }

    @ExceptionHandler(InventoryQuantityNotEnoughException.class)
    public ResponseEntity<String> handleQuantityNotEnoughException(InventoryQuantityNotEnoughException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> handleGenericException(Exception ex) {
        return ResponseEntity.internalServerError().body(ex.getMessage());
    }
}
