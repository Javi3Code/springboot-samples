package org.jeycode.samples.domain.orders.models;

public enum OrderStatus {
  ACTIVE("Active"),
  COMPLETED("Completed"),
  CANCELED("Canceled");

  private final String displayName;

  OrderStatus(String displayName) {
    this.displayName = displayName;
  }

  @Override
  public String toString() {
    return displayName;
  }
}

