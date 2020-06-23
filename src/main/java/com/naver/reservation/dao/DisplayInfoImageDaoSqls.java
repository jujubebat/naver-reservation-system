package com.naver.reservation.dao;

public class DisplayInfoImageDaoSqls {
  
  public static final String SELECT_BY_ID = 
        "SELECT display_info_image.id AS display_info_image_id, \r\n" 
      + "       display_info_id, \r\n" 
      + "       file_id, \r\n" 
      + "       file_name, \r\n" 
      + "       save_file_name, \r\n" 
      + "       content_type, \r\n" 
      + "       delete_flag, \r\n" 
      + "       file_info.create_date, \r\n" 
      + "       file_info.modify_date \r\n" 
      + "FROM   display_info_image \r\n" 
      + "       INNER JOIN display_info \r\n" 
      + "               ON display_info_image.display_info_id = display_info.id \r\n" 
      + "       INNER JOIN file_info \r\n" 
      + "               ON display_info_image.file_id = file_info.id \r\n" 
      + "WHERE  display_info_id = :displayInfoId";
  
}
