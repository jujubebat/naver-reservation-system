package com.naver.reservation.service;

import com.naver.reservation.dto.api.Comment;
import java.util.List;

public interface CommentService {

  public List<Comment> getCommentsWithImagesByProductId(Integer productId);

  public double getProductAverageScoreByProductId(Integer productId);

}

