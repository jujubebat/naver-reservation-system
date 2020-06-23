package com.naver.reservation.service;

import com.naver.reservation.dao.DisplayInfoImageDao;
import com.naver.reservation.dto.api.DisplayInfoImage;
import com.naver.reservation.service.DisplayInfoImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DisplayInfoImageService {

  private final DisplayInfoImageDao displayInfoImageDao;

  @Autowired
  public DisplayInfoImageService(DisplayInfoImageDao displayInfoImageDao) {
    this.displayInfoImageDao = displayInfoImageDao;
  }

  public DisplayInfoImage getDisplayInfoImageByDisplayInfoId(Integer displayInfoId) {
    return displayInfoImageDao.selectByDisplayInfoId(displayInfoId);
  }
  
}


