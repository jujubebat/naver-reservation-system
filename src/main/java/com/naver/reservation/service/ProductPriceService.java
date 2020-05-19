package com.naver.reservation.service;

import com.naver.reservation.dto.api.ProductPrice;
import java.util.List;

public interface ProductPriceService {
  public List<ProductPrice> getProductPricesByProductId(Integer productId);
}
