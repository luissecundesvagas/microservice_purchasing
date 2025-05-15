package com.gruposv.microservice_purchasing.modules.inventory_planning_and_control.exception;

public class DuplicateInventoryPolicyException extends RuntimeException {
  public DuplicateInventoryPolicyException(String message) {
    super(message);
  }
}
