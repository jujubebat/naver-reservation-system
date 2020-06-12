package com.naver.reservation.dao;

public class ProductPriceDaoSqls {
  
  public static final String SELECT_BY_PRODUCT_ID =
      
        "SELECT id AS productPriceId, \r\n" 
      + "       product_id, \r\n" 
      + "       price_type_name, \r\n" 
      + "       price, \r\n" 
      + "       discount_rate, \r\n" 
      + "       create_date, \r\n" 
      + "       modify_date \r\n" 
      + "FROM   product_price \r\n" 
      + "WHERE  product_id = :productId";
  
  public static final String SELECT_PRICE_BY_PRODUCT_ID = 
      
        "SELECT \r\n"
      + "    price\r\n"
      + "FROM\r\n"
      + "    product_price\r\n"
      + "WHERE\r\n"
      + "    id = :productId";
  
}
