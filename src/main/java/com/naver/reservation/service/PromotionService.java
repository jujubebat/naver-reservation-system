package com.naver.reservation.service;

import com.naver.reservation.dao.PromotionDao;
import com.naver.reservation.dto.api.Promotion;
import com.naver.reservation.service.PromotionService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PromotionService {

  private final PromotionDao promotionsDao;

  @Autowired
  public PromotionService(PromotionDao promotionsDao) {
    this.promotionsDao = promotionsDao;
  }

  public List<Promotion> getPromotions() {
    return promotionsDao.selectAll();
  }
}
