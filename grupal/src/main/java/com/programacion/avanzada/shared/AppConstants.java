package com.programacion.avanzada.shared;

public class AppConstants {
  private AppConstants() {}

  public static final String PREFIX_INVENTORY = "inv:";
  public static final String PREFIX_BOOK = "book:";
  public static final String PREFIX_CUSTOMER = "customer:";
  public static final String PREFIX_AUTHOR = "author:";
  public static final String PREFIX_PURCHASE_ORDER = "order:";

  public static String buildInventoryKey(String bookIsbn) {
    return PREFIX_INVENTORY + bookIsbn;
  }

  public static String buildBookKey(String bookIsbn) {
    return PREFIX_BOOK + bookIsbn;
  }

  public static String buildCustomerKey(String customerId) {
    return PREFIX_CUSTOMER + customerId;
  }

  public static String buildAuthorKey(String authorId) {
    return PREFIX_AUTHOR + authorId;
  }

  public static String buildPurchaseOrderKey(String orderId) {
    return PREFIX_PURCHASE_ORDER + orderId;
  }
}
