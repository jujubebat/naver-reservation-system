package com.naver.reservation.service;

import com.naver.reservation.dto.api.ProductImage;
import java.util.List;

public interface ProductImageService { 
  public List<ProductImage> getProductImagesByProductId(Integer productId);
}


