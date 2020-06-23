package com.naver.reservation.service;

import com.naver.reservation.dao.CommentDao;
import com.naver.reservation.dto.api.CommentDetail;
import com.naver.reservation.dto.api.CommentImage;
import com.naver.reservation.service.CommentImageService;
import com.naver.reservation.service.CommentService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

  private final CommentImageService commentImageService;
  private final CommentDao commentDao;

  @Autowired
  public CommentService(CommentImageService commentImageService, CommentDao commentDao) {
    this.commentImageService = commentImageService;
    this.commentDao = commentDao;
  }

  public List<CommentDetail> getCommentsWithImagesByProductId(Integer productId) {
    List<CommentDetail> comments = commentDao.selectByProductId(productId);

    for (CommentDetail comment : comments) { // 객체는 참조형태이므로 이런식으로 원본 리스트 수정이 가능하다.
      List<CommentImage> commentImages =
          commentImageService.getCommentImageByCommentId(comment.getCommentId());
      comment.setCommentImages(commentImages);
    }

    return comments;
  }

  public double getProductAverageScoreByProductId(Integer productId) {
    return commentDao.selectAverageScoreByProductId(productId);
  }
  
}
