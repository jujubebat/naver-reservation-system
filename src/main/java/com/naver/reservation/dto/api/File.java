package com.naver.reservation.dto.api;

import lombok.Data;

public @Data class File {

  private String fileName;
  private String saveFileName;
  private String contentType;
  private boolean deleteFlag;
  private String createDate;
  private String modifyDate;

}
