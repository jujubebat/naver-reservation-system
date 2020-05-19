package com.naver.reservation.dao;

public class ProductDaoSqls {
  
  public static final String SELECT_ALL = 
        "SELECT display_info.id          AS displayInfoId, \r\n" 
      + "       product.id               AS productId, \r\n" 
      + "       description              AS productDescription, \r\n" 
      + "       display_info.place_name, \r\n" 
      + "       content                  AS productContent, \r\n" 
      + "       file_info.save_file_name AS productImageUrl \r\n" 
      + "FROM   product \r\n" 
      + "       INNER JOIN display_info \r\n" 
      + "               ON product.id = display_info.product_id \r\n" 
      + "       INNER JOIN product_image \r\n" 
      + "               ON product.id = product_image.product_id \r\n" 
      + "       INNER JOIN file_info \r\n" 
      + "               ON product_image.file_id = file_info.id \r\n" 
      + "WHERE  product_image.type = 'th' \r\n" 
      + "LIMIT  :start, :limit";

  public static final String SELECT_BY_CATEGORY_ID = 
        "SELECT display_info.id          AS displayInfoId, \r\n" 
      + "       product.id               AS productId, \r\n" 
      + "       description              AS productDescription, \r\n" 
      + "       display_info.place_name, \r\n" 
      + "       content                  AS productContent, \r\n" 
      + "       file_info.save_file_name AS productImageUrl \r\n" 
      + "FROM   product \r\n" 
      + "       INNER JOIN display_info \r\n" 
      + "               ON product.id = display_info.product_id \r\n" 
      + "       INNER JOIN product_image \r\n" 
      + "               ON product.id = product_image.product_id \r\n" 
      + "       INNER JOIN file_info \r\n" 
      + "               ON product_image.file_id = file_info.id \r\n" 
      + "WHERE  product.category_id = :categoryId \r\n" 
      + "       AND product_image.type = 'th' \r\n" 
      + "LIMIT  :start, :limit";
  
  public static final String SELECT_COUNT = 
        "SELECT Count(*) \r\n" 
      + "FROM   product \r\n" 
      + "       INNER JOIN display_info \r\n" 
      + "               ON product.id = display_info.product_id \r\n" 
      + "       INNER JOIN product_image \r\n" 
      + "               ON product.id = product_image.product_id \r\n" 
      + "       INNER JOIN file_info \r\n" 
      + "               ON product_image.file_id = file_info.id \r\n" 
      + "WHERE  product_image.type = 'th'";


  public static final String SELECT_COUNT_BY_CATEGORY_ID =
        "SELECT Count(*) \r\n" 
      + "FROM   product \r\n" 
      + "       INNER JOIN display_info \r\n" 
      + "               ON product.id = display_info.product_id \r\n" 
      + "       INNER JOIN product_image \r\n" 
      + "               ON product.id = product_image.product_id \r\n" 
      + "       INNER JOIN file_info \r\n" 
      + "               ON product_image.file_id = file_info.id \r\n" 
      + "WHERE  product_image.type = 'th' \r\n" 
      + "       AND product.category_id = :categoryId \r\n" 
      + "GROUP  BY product.category_id";
      
}


