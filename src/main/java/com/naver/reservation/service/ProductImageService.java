package com.naver.reservation.service;

import com.naver.reservation.dao.ProductImageDao;
import com.naver.reservation.dto.api.ProductImage;
import com.naver.reservation.service.ProductImageService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductImageService {

  private final ProductImageDao productImageDao;

  @Autowired
  public ProductImageService(ProductImageDao productImageDao) {
    this.productImageDao = productImageDao;
  }
  
  public List<ProductImage> getProductImagesByProductId(Integer productId) {
    return productImageDao.selectByProductId(productId);
  }

}