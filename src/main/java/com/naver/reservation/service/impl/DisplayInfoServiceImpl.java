package com.naver.reservation.service.impl;

import com.naver.reservation.dao.DisplayInfoDao;
import com.naver.reservation.dto.api.DisplayInfo;
import com.naver.reservation.service.DisplayInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisplayInfoServiceImpl implements DisplayInfoService {

  private final DisplayInfoDao displayInfoDao;

  @Autowired
  public DisplayInfoServiceImpl(DisplayInfoDao displayInfoDao) {
    this.displayInfoDao = displayInfoDao;
  }

  @Override
  public DisplayInfo getDisplayInfoById(Integer id) {

    return displayInfoDao.selectByDisplayInfoId(id);
    
  }
  
}
