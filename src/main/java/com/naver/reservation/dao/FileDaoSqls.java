package com.naver.reservation.dao;

public class FileDaoSqls {
  
  public static final String SELECT_BY_ID = 
        "SELECT * \r\n" 
      + "FROM   file_info \r\n" 
      + "WHERE  id = :fileId";
  
}
