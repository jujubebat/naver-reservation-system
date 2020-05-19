package com.naver.reservation.service.impl;

import com.naver.reservation.dao.PromotionDao;
import com.naver.reservation.dto.api.Promotion;
import com.naver.reservation.service.PromotionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionServiceImpl implements PromotionService {

  private final PromotionDao promotionsDao;

  @Autowired
  public PromotionServiceImpl(PromotionDao promotionsDao) {
    this.promotionsDao = promotionsDao;
  }

  @Override
  public List<Promotion> getPromotions() {
    return promotionsDao.selectAll();
  }
}
