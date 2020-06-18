package com.naver.reservation.service;

import com.naver.reservation.dao.ProductPriceDao;
import com.naver.reservation.dto.api.ProductPrice;
import com.naver.reservation.service.ProductPriceService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductPriceService {

  private final ProductPriceDao productPriceDao;

  @Autowired
  public ProductPriceService(ProductPriceDao productPriceDao) {
    this.productPriceDao = productPriceDao;
  }

  public List<ProductPrice> getProductPricesByProductId(Integer productId) {
    return productPriceDao.selectByProductId(productId);
  }

}
