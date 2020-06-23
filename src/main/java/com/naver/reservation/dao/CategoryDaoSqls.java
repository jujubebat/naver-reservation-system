package com.naver.reservation.dao;

public class CategoryDaoSqls {
  
  public static final String SELECT_ALL = 
        "SELECT category.id,\r\n"
      + "       category.NAME,\r\n" 
      + "       COUNT(*) AS count\r\n" 
      + "FROM   product\r\n" 
      + "       INNER JOIN category\r\n" 
      + "               ON product.category_id = category.id\r\n" 
      + "GROUP  BY category.id";
  
}
