package com.naver.reservation.dao;

public class CommentDaoSqls {
  public static final String SELECT_BY_PRODUCT_ID = 
        "SELECT reservation_user_comment.id      AS comment_id, \r\n"  
      + "       reservation_user_comment.product_id, \r\n" 
      + "       reservation_info_id, \r\n" 
      + "       score, \r\n" 
      + "       COMMENT, \r\n" 
      + "       reservation_info.reservation_name, \r\n" 
      + "       reservation_info.reservation_tel AS reservation_telephone, \r\n" 
      + "       reservation_info.reservation_email, \r\n" 
      + "       reservation_info.reservation_date, \r\n" 
      + "       reservation_user_comment.create_date, \r\n" 
      + "       reservation_user_comment.modify_date \r\n" 
      + "FROM   reservation_user_comment \r\n" 
      + "       inner join reservation_info \r\n" 
      + "               ON reservation_info_id = reservation_info.id \r\n" 
      + "WHERE  reservation_user_comment.product_id = :productId";
  
  public static final String SELECT_SCORE_AVG = 
       "SELECT Avg(score) \r\n"  
      + "FROM   reservation_user_comment \r\n"  
      + "       INNER JOIN reservation_info \r\n"  
      + "               ON reservation_info_id = reservation_info.id \r\n" 
      + "WHERE  reservation_user_comment.product_id = :productId";
     
}