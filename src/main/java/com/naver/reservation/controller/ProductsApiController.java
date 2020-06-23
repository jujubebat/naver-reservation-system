package com.naver.reservation.controller;

import com.naver.reservation.dto.api.CommentDetail;
import com.naver.reservation.dto.api.DisplayInfo;
import com.naver.reservation.dto.api.DisplayInfoImage;
import com.naver.reservation.dto.api.Product;
import com.naver.reservation.dto.api.ProductImage;
import com.naver.reservation.dto.api.ProductPrice;
import com.naver.reservation.dto.api.returnObject.DetailDisplayInfo;
import com.naver.reservation.dto.api.returnObject.ProductList;
import com.naver.reservation.service.CommentService;
import com.naver.reservation.service.DisplayInfoImageService;
import com.naver.reservation.service.DisplayInfoService;
import com.naver.reservation.service.ProductImageService;
import com.naver.reservation.service.ProductPriceService;
import com.naver.reservation.service.ProductService;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping(path = "/api/products")
public class ProductsApiController {

  private final ProductService productsService;
  private final DisplayInfoService displayInfoService;
  private final ProductImageService productImageService;
  private final DisplayInfoImageService displayInfoImageService;
  private final ProductPriceService productPriceService;
  private final CommentService commentService;
  
 /*
  * [질문] 
  * 아래 생성자에 대한 질문 입니다. 현재 구글 자바 컨밴션을 적용하고 있는데, 코드 체커가  한 라인이 100이 넘으면
  * 아래처럼 줄이라고 이야기 해주는데, 이 규칙을 적용하는 것이 좋은건지 궁금합니다.
  * */
  
  @Autowired
  public ProductsApiController(ProductService productsService, 
      DisplayInfoService displayInfoService, 
      ProductImageService productImageService,
      DisplayInfoImageService displayInfoImageService,
      ProductPriceService productPriceService,
      CommentService commentService) {
    this.productsService = productsService;
    this.displayInfoService = displayInfoService;
    this.productImageService = productImageService;
    this.displayInfoImageService = displayInfoImageService;
    this.productPriceService = productPriceService;
    this.commentService = commentService;
  }
  
  @GetMapping
  public ProductList getProducts(@RequestParam(defaultValue = "0") int categoryId,
      @RequestParam(defaultValue = "0") int start) {
    int totalCount = productsService.getCountByCategory(categoryId);
    List<Product> products = productsService.getProductsByCategoryId(categoryId, start);
        
    return new ProductList(totalCount, products);
  }

  @GetMapping(path = "/{displayInfoId}")
  public DetailDisplayInfo getDisplayInfo(@PathVariable(name = "displayInfoId") int displayInfoId) {
    DisplayInfo displayInfo = displayInfoService.getDisplayInfoById(displayInfoId);
    Optional<DisplayInfo> optDisplayInfo = Optional.ofNullable(displayInfo);

    if (!optDisplayInfo.isPresent()) {
      return null;
    }

    int productId = displayInfo.getProductId();

    List<ProductImage> productImages = productImageService.getProductImagesByProductId(productId);
    DisplayInfoImage displayInfoImage =
        displayInfoImageService.getDisplayInfoImageByDisplayInfoId(displayInfoId);
    List<ProductPrice> productPrices = productPriceService.getProductPricesByProductId(productId);
    double averageScore = commentService.getProductAverageScoreByProductId(productId);
    List<CommentDetail> comments = commentService.getCommentsWithImagesByProductId(productId);

    return new DetailDisplayInfo(displayInfo, productImages, displayInfoImage, productPrices,
        averageScore, comments);
  }

}
