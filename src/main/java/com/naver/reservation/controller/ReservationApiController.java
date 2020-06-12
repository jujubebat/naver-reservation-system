package com.naver.reservation.controller;

import com.naver.reservation.dto.api.Comment;
import com.naver.reservation.dto.api.DisplayInfo;
import com.naver.reservation.dto.api.DisplayInfoImage;
import com.naver.reservation.dto.api.ProductImage;
import com.naver.reservation.dto.api.ProductPrice;
import com.naver.reservation.dto.api.paramObject.ReservationInfoParam;
import com.naver.reservation.dto.api.returnObject.CategoryList;
import com.naver.reservation.dto.api.returnObject.DetailDisplayInfo;
import com.naver.reservation.dto.api.returnObject.ProductList;
import com.naver.reservation.dto.api.returnObject.PromotionList;
import com.naver.reservation.dto.api.returnObject.ReservationInfoDetail;
import com.naver.reservation.dto.api.returnObject.ReservationInfo;
import com.naver.reservation.dto.api.returnObject.UserReservationList;
import com.naver.reservation.service.CategoryService;
import com.naver.reservation.service.CommentService;
import com.naver.reservation.service.DisplayInfoImageService;
import com.naver.reservation.service.DisplayInfoService;
import com.naver.reservation.service.ProductImageService;
import com.naver.reservation.service.ProductPriceService;
import com.naver.reservation.service.ProductService;
import com.naver.reservation.service.PromotionService;
import com.naver.reservation.service.ReservationInfoService;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
  private final ReservationInfoService reservationInfoService;

  /**
   * 주석.
   */
  @Autowired
  public ReservationApiController(ProductService productsService, CategoryService categoriesService,
      PromotionService promotionsService, DisplayInfoService displayInfoService,
      ProductImageService productImageService, DisplayInfoImageService displayInfoImageService,
      ProductPriceService productPriceService, CommentService commentService,
      ReservationInfoService reservationInfoService) {

    this.productsService = productsService;
    this.categoriesService = categoriesService;
    this.promotionsService = promotionsService;
    this.displayInfoService = displayInfoService;
    this.productImageService = productImageService;
    this.displayInfoImageService = displayInfoImageService;
    this.productPriceService = productPriceService;
    this.commentService = commentService;
    this.reservationInfoService = reservationInfoService;
  }

  /**
   * 주석.
   */
  @GetMapping(path = "/products")
  public ProductList getProducts(@RequestParam(defaultValue = "0") int categoryId,
      @RequestParam(defaultValue = "0") int start) {

    return new ProductList(productsService.getCountByCategory(categoryId),
        productsService.getProductsByCategoryId(categoryId, start));

  }

  /**
   * 주석.
   */
  @GetMapping(path = "/products/{displayInfoId}")
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
    List<Comment> comments = commentService.getCommentsWithImagesByProductId(productId);

    return new DetailDisplayInfo(displayInfo, productImages, displayInfoImage, productPrices,
        averageScore, comments);
    
  }

  /**
   * 주석.
   */
  @GetMapping(path = "/categories")
  public CategoryList getCategories() {

    return new CategoryList(categoriesService.getCategories());

  }

  /**
   * 주석.
   */
  @GetMapping(path = "/promotions")
  public PromotionList getPromotions() {

    return new PromotionList(promotionsService.getPromotions());

  }

  /**
   * 주석.
   */
  @PostMapping(path = "/reservations")
  public ReservationInfo postReservations(@RequestBody ReservationInfoParam reservationParam) {

    return reservationInfoService.addReservationInfo(reservationParam);

  }
 
  /**
   * 주석.
   */
  @GetMapping(path = "/reservations")
  public UserReservationList getReservations(
      @RequestParam(name = "reservationEmail") String reservationEmail) {

    List<ReservationInfoDetail> reservationInfoDetail =
        reservationInfoService.getReservationInfoDetailListByEmail(reservationEmail);
    
    return new UserReservationList(reservationInfoDetail);

  }
  
  /**
   * 주석.
   */
  @PutMapping("/reservations/{reservationId}")
  public ReservationInfo cancelReservation(
      @PathVariable(name = "reservationId") int reservationId) {

    return reservationInfoService.cancelReservation(reservationId);

  }

}
