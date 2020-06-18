package com.naver.reservation.service;

import com.naver.reservation.dao.DisplayInfoDao;
import com.naver.reservation.dto.api.DisplayInfo;
import com.naver.reservation.service.DisplayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisplayInfoService {

  private final DisplayInfoDao displayInfoDao;

  @Autowired
  public DisplayInfoService(DisplayInfoDao displayInfoDao) {
    this.displayInfoDao = displayInfoDao;
  }

  public DisplayInfo getDisplayInfoById(Integer id) {

    return displayInfoDao.selectByDisplayInfoId(id);
    
  }
  
}
