package com.naver.reservation.dao;

public class DisplayInfoDaoSqls {
  public static final String SELECT_BY_DISPLAY_INFO_ID = 
        "SELECT product_id, \r\n" 
      + "       category_id, \r\n" 
      + "       display_info.id AS display_info_id, \r\n" 
      + "       category.NAME   AS category_name, \r\n" 
      + "       description     AS product_description, \r\n" 
      + "       content         AS product_content, \r\n" 
      + "       event           AS product_event, \r\n" 
      + "       opening_hours, \r\n" 
      + "       place_name, \r\n" 
      + "       place_lot, \r\n" 
      + "       place_street, \r\n" 
      + "       tel             AS telephone, \r\n" 
      + "       display_info.homepage, \r\n" 
      + "       display_info.email, \r\n" 
      + "       display_info.create_date, \r\n" 
      + "       display_info.modify_date \r\n" 
      + "FROM   display_info \r\n" 
      + "       INNER JOIN product \r\n" 
      + "               ON display_info.product_id = product.id \r\n" 
      + "       INNER JOIN category \r\n" 
      + "               ON product.category_id = category.id \r\n" 
      + "WHERE  display_info.id = :displayInfoId";
}
