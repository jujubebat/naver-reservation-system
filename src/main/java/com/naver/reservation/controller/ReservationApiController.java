package com.naver.reservation.controller;

import com.naver.reservation.dto.api.Comment;
import com.naver.reservation.dto.api.DisplayInfo;
import com.naver.reservation.dto.api.DisplayInfoImage;
import com.naver.reservation.dto.api.Product;
import com.naver.reservation.dto.api.ProductImage;
import com.naver.reservation.dto.api.ProductPrice;
import com.naver.reservation.service.CategoryService;
import com.naver.reservation.service.CommentService;
import com.naver.reservation.service.DisplayInfoImageService;
import com.naver.reservation.service.DisplayInfoService;
import com.naver.reservation.service.ProductImageService;
import com.naver.reservation.service.ProductPriceService;
import com.naver.reservation.service.ProductService;
import com.naver.reservation.service.PromotionService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
@RequestMapping(path = "/api")
public class ReservationApiController {

  private final ProductService productsService;
  private final CategoryService categoriesService;
  private final PromotionService promotionsService;
  private final DisplayInfoService displayInfoService;
  private final ProductImageService productImageService;
  private final DisplayInfoImageService displayInfoImageService;
  private final ProductPriceService productPriceService;
  private final CommentService commentService;

  @Autowired
  public ReservationApiController(ProductService productsService, CategoryService categoriesService,
      PromotionService promotionsService, DisplayInfoService displayInfoService,
      ProductImageService productImageService, DisplayInfoImageService displayInfoImageService,
      ProductPriceService productPriceService, CommentService commentService) {

    this.productsService = productsService;
    this.categoriesService = categoriesService;
    this.promotionsService = promotionsService;
    this.displayInfoService = displayInfoService;
    this.productImageService = productImageService;
    this.displayInfoImageService = displayInfoImageService;
    this.productPriceService = productPriceService;
    this.commentService = commentService;

  }

  /**
   * categoryId와 start(순번)을 받아서 Products list를 json으로 반환하는 메서드. categoryId가 주어지지 않거나 0이면, 모든 카테고리에
   * 대한 Products list를 반환.
   */
  @GetMapping(path = "/products")
  public Map<String, Object> getProducts(@RequestParam(defaultValue = "0") int categoryId,
      @RequestParam(defaultValue = "0") int start) {

    List<Product> productList = productsService.getProductsByCategoryId(categoryId, start);
    int productsCount = productsService.getCountByCategory(categoryId);

    Map<String, Object> map = new HashMap<>();
    map.put("totalCount", productsCount);
    map.put("items", productList);

    return map;
  }

  /**
   * displayI는 메서드.
   * PathVariable의 required는 defaul 값은 true다.
   */
  @GetMapping(path = "/products/{displayInfoId}")
  public Map<String, Object> getDisplayInfo(
      @PathVariable(name = "displayInfoId") int displayInfoId) { 

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
    double productScoreAvg = commentService.getProductAverageScoreByProductId(productId);
    List<Comment> comments = commentService.getCommentsWithImagesByProductId(productId);

    Map<String, Object> map = new HashMap<>();

    map.put("displayInfo", displayInfo);
    map.put("productImages", productImages);
    map.put("displayInfoImage", displayInfoImage);
    map.put("productPrices", productPrices);
    map.put("averageScore", productScoreAvg);
    map.put("comments", comments);

    return map;
  }

  /**
   * Categories list를 json으로 반환하는 메서드.
   */
  @GetMapping(path = "/categories")
  public Map<String, Object> getCategories() {

    Map<String, Object> map = new HashMap<>();
    map.put("items", categoriesService.getCategories());
    return map;

  }

  /**
   * promotions list를 json으로 반환하는 메서드.
   */
  @GetMapping(path = "/promotions")
  public Map<String, Object> getPromotions() {

    Map<String, Object> map = new HashMap<>();
    map.put("items", promotionsService.getPromotions());
    return map;

  }

}
