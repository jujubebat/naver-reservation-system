package com.naver.reservation.dao;

public class ReservationUserCommentImageDaoSqls {
  
  public static final String SELECT_BY_ID = 
        "SELECT reservation_user_comment_image.id                          AS imageId, \r\n" 
      + "       reservation_user_comment_image.reservation_info_id         AS reservationInfoId,\r\n"
      + "       reservation_user_comment_image.reservation_user_comment_id AS reservationUserCommentId, \r\n"
      + "       reservation_user_comment_image.file_id                     AS fileId, \r\n"
      + "       file_info.file_name                                        AS fileName, \r\n"
      + "       file_info.save_file_name                                   AS  saveFileName, \r\n"
      + "       file_info.content_type                                     AS contentType, \r\n"
      + "       file_info.delete_flag                                      AS deleteFlag, \r\n"
      + "       file_info.create_date                                      AS createDate, \r\n"
      + "       file_info.modify_date                                      AS modifyDate \r\n"
      + "FROM   reservationdb.reservation_user_comment_image \r\n"
      + "       INNER JOIN file_info \r\n"
      + "               ON reservation_user_comment_image.file_id = file_info.id \r\n"
      + "WHERE  reservation_user_comment_image.id = :id";
  
}
