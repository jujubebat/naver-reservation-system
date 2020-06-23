package com.naver.reservation.dao;

public class CommentImageDaoSqls {
  
  public static final String SELECT_BY_COMMENT_ID = 
        "SELECT reservation_user_comment_image.id AS image_id, \r\n" 
      + "       reservation_info_id, \r\n" 
      + "       reservation_user_comment_id, \r\n" 
      + "       file_id, \r\n" 
      + "       file_name, \r\n" 
      + "       save_file_name, \r\n" 
      + "       content_type, \r\n"
      + "       delete_flag, \r\n" 
      + "       create_date, \r\n" 
      + "       modify_date \r\n" 
      + "FROM   reservation_user_comment_image \r\n" 
      + "       INNER JOIN file_info \r\n" 
      + "               ON file_info.id = reservation_user_comment_image.file_id \r\n" 
      + "WHERE  reservation_user_comment_id = :commentId";
      
}
