package com.naver.reservation.dao;

public class PromotionDaoSqls {
  
  public static final String SELECT_ALL =
        "SELECT promotion.id, \r\n" 
      + "       product.id               AS productId, \r\n" 
      + "       file_info.save_file_name AS productImageUrl \r\n" 
      + "FROM   promotion \r\n" 
      + "       INNER JOIN product \r\n" 
      + "               ON promotion.product_id = product.id \r\n" 
      + "       INNER JOIN product_image \r\n" 
      + "               ON product.id = \r\n" 
      + "                  product_image.product_id \r\n" 
      + "       INNER JOIN file_info \r\n" 
      + "               ON product_image.file_id = \r\n" 
      + "                  file_info.id \r\n" 
      + "WHERE  product_image.type = 'th'";
  
}


