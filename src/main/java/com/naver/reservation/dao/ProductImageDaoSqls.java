package com.naver.reservation.dao;

public class ProductImageDaoSqls {
  public static final String SELECT_BY_PRODUCT_ID = 
        "SELECT product_id, \r\n" 
      + "       product_image.id AS product_image_id, \r\n" 
      + "       type, \r\n" 
      + "       file_id          AS file_info_id, \r\n" 
      + "       file_name, \r\n" 
      + "       save_file_name, \r\n" 
      + "       content_type, \r\n" 
      + "       delete_flag, \r\n" 
      + "       file_info.create_date, \r\n" 
      + "       file_info.modify_date \r\n" 
      + "FROM   product_image \r\n" 
      + "       INNER JOIN product \r\n" 
      + "               ON product_image.product_id = product.id \r\n" 
      + "       INNER JOIN file_info \r\n" 
      + "               ON product_image.file_id = file_info.id \r\n" 
      + "WHERE  product_id = :productID \r\n" 
      + "       AND ( type = 'ma' OR type = 'et' )";
           
}
