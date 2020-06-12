package com.naver.reservation.service.impl;

import com.naver.reservation.dao.DisplayInfoImageDao;
import com.naver.reservation.dto.api.DisplayInfoImage;
import com.naver.reservation.service.DisplayInfoImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class DisplayInfoImageServiceImpl implements DisplayInfoImageService {

  private final DisplayInfoImageDao displayInfoImageDao;

  @Autowired
  public DisplayInfoImageServiceImpl(DisplayInfoImageDao displayInfoImageDao) {
    this.displayInfoImageDao = displayInfoImageDao;
  }

  @Override
  @Transactional
  public DisplayInfoImage getDisplayInfoImageByDisplayInfoId(Integer displayInfoId) {
    return displayInfoImageDao.selectByDisplayInfoId(displayInfoId);
  }
}


