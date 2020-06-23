package com.naver.reservation.dto.api.returnObject;

import com.naver.reservation.dto.api.Comment;
import com.naver.reservation.dto.api.CommentImage;
import lombok.Data;

public @Data class CommentInfo extends Comment {

  private CommentImage commentImage;

}
