package com.naver.reservation.dto.api.returnObject;

import com.naver.reservation.dto.api.CommentDetail;
import com.naver.reservation.dto.api.DisplayInfo;
import com.naver.reservation.dto.api.DisplayInfoImage;
import com.naver.reservation.dto.api.ProductImage;
import com.naver.reservation.dto.api.ProductPrice;
import java.util.List;
import lombok.Data;

public @Data class DetailDisplayInfo {
  
  private DisplayInfo displayInfo;
  private List<ProductImage> productImages;
  private DisplayInfoImage displayInfoImage;
  private List<ProductPrice> productPrices;
  private double averageScore;
  private List<CommentDetail> comments;

  public DetailDisplayInfo(DisplayInfo displayInfo, List<ProductImage> productImages,
      DisplayInfoImage displayInfoImage, List<ProductPrice> productPrices, double averageScore,
      List<CommentDetail> comments) {
    this.displayInfo = displayInfo;
    this.productImages = productImages;
    this.displayInfoImage = displayInfoImage;
    this.productPrices = productPrices;
    this.averageScore = averageScore;
    this.comments = comments;
  }

}
