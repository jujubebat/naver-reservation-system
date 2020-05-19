package com.naver.reservation.service.impl;

import com.naver.reservation.dao.ProductPriceDao;
import com.naver.reservation.dto.api.ProductPrice;
import com.naver.reservation.service.ProductPriceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductPriceServiceImpl implements ProductPriceService {

  private final ProductPriceDao productPriceDao;

  @Autowired
  public ProductPriceServiceImpl(ProductPriceDao productPriceDao) {
    this.productPriceDao = productPriceDao;
  }

  @Override
  public List<ProductPrice> getProductPricesByProductId(Integer productId) {
    return productPriceDao.selectByProductId(productId);
  }

}
