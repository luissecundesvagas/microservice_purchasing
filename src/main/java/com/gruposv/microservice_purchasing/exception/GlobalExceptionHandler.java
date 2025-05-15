package com.gruposv.microservice_purchasing.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.gruposv.microservice_purchasing.dto.ApiResponseDTO;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.exception.DuplicateInventoryPolicyException;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.exception.InventoryPlanningRunNotFoundException;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.exception.NotFoundInventoryPolicyException;
import com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.exception.PlanningSuggestionNotFoundException;
import com.gruposv.microservice_purchasing.modules.product.exception.*;
import com.gruposv.microservice_purchasing.modules.shipping.exception.ShipmentNotFoundException;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_balance.DuplicateStockBalanceException;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_balance.InsufficientStockException;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_balance.StockBalanceNotFoundException;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_location.StockLocationCodeDuplicateException;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_location.StockLocationNameDuplicateException;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_location.StockLocationNotFoundException;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_lot_details.DuplicateStockLotDetailsException;
import com.gruposv.microservice_purchasing.modules.stock.exception.stock_lot_details.NotFoundStockLotDetailsException;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateNameProductException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleDuplicateNameProductException(DuplicateNameProductException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponseDTO<>("error", HttpStatus.CONFLICT.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(DuplicateSkuCodeException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleDuplicateSkuCodeException(DuplicateSkuCodeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponseDTO<>("error", HttpStatus.CONFLICT.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleProductNotFoundException(ProductNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO<>("error", HttpStatus.NOT_FOUND.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDTO<Map<String, String>>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();

        ex.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO<>("error", HttpStatus.BAD_REQUEST.value(), errors, ex.getMessage()));
    }

    @ExceptionHandler(DuplicateInventoryPolicyException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleDuplicateInventoryPolicyException(DuplicateInventoryPolicyException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponseDTO<>("error", HttpStatus.CONFLICT.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(InventoryPlanningRunNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleInventoryPlanningRunNotFoundException(InventoryPlanningRunNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO<>("error", HttpStatus.NOT_FOUND.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(NotFoundInventoryPolicyException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleNotFoundInventoryPolicyException(NotFoundInventoryPolicyException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO<>("error", HttpStatus.NOT_FOUND.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(PlanningSuggestionNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handlePlanningSuggestionNotFoundException(PlanningSuggestionNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO<>("error", HttpStatus.NOT_FOUND.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(AttributeNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleAttributeNotFoundException(AttributeNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO<>("error", HttpStatus.NOT_FOUND.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleCategoryNotFoundException(CategoryNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO<>("error", HttpStatus.NOT_FOUND.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(DuplicateAttributeException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleDuplicateAttributeException(DuplicateAttributeException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponseDTO<>("error", HttpStatus.CONFLICT.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(DuplicateCategoryException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleDuplicateCategoryException(DuplicateCategoryException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponseDTO<>("error", HttpStatus.CONFLICT.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(ShipmentNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleShipmentNotFoundException(ShipmentNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO<>("error", HttpStatus.NOT_FOUND.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(DuplicateStockBalanceException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleDuplicateStockBalanceException(DuplicateStockBalanceException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponseDTO<>("error", HttpStatus.CONFLICT.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(StockBalanceNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleStockBalanceNotFoundException(StockBalanceNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO<>("error", HttpStatus.NOT_FOUND.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(StockLocationCodeDuplicateException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleStockLocationCodeDuplicateException(StockLocationCodeDuplicateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponseDTO<>("error", HttpStatus.CONFLICT.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(StockLocationNameDuplicateException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleStockLocationNameDuplicateException(StockLocationNameDuplicateException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponseDTO<>("error", HttpStatus.CONFLICT.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(StockLocationNotFoundException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleStockLocationNotFoundException(StockLocationNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO<>("error", HttpStatus.NOT_FOUND.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(DuplicateStockLotDetailsException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleDuplicateStockLotDetailsException(DuplicateStockLotDetailsException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                .body(new ApiResponseDTO<>("error", HttpStatus.CONFLICT.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(NotFoundStockLotDetailsException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleNotFoundStockLotDetailsException(NotFoundStockLotDetailsException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ApiResponseDTO<>("error", HttpStatus.NOT_FOUND.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(InsufficientStockException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleInsufficientStockException(InsufficientStockException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO<>("error", HttpStatus.BAD_REQUEST.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(IllegalStockMovementException.class)
    public ResponseEntity<ApiResponseDTO<String>> handleIllegalStockMovementException(IllegalStockMovementException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ApiResponseDTO<>("error", HttpStatus.BAD_REQUEST.value(), null, ex.getMessage()));
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ApiResponseDTO<Object>> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String errorMessage = String.format("O valor '%s' para o parâmetro '%s' deve ser do tipo %s.",
                ex.getValue(), ex.getName(),
                ex.getRequiredType().getSimpleName());
        ApiResponseDTO<Object> apiResponse = new ApiResponseDTO<>("error", HttpStatus.BAD_REQUEST.value(), null, "Erro de entrada de dados. Verifique se o dados do corpo da requisição estão corretos.");
        return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDTO<String>> handleGenericException(Exception ex) {
        // Obter informações sobre a linha do erro
        StackTraceElement[] stackTrace = ex.getStackTrace();
        String lineNumber = "Linha não disponível";
        if (stackTrace.length > 0) {
            lineNumber = String.valueOf(stackTrace[0].getLineNumber());
        }

        String errorMessageWithLocation = ex.getMessage() + " (Linha: " + lineNumber + ")";

        // Imprimir o erro no console (opcional, mas útil para log)
        System.err.println("Ocorreu um erro: " + errorMessageWithLocation);
        ex.printStackTrace(); // Imprime a stack trace completa para mais detalhes

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ApiResponseDTO<>("error", HttpStatus.INTERNAL_SERVER_ERROR.value(), null, errorMessageWithLocation));
    }
}
