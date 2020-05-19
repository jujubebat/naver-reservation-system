package com.naver.reservation.service;

import com.naver.reservation.dto.api.CommentImage;
import java.util.List;

public interface CommentImageService {
  public List<CommentImage> getCommentImageByCommentId(Integer commentId);
}
