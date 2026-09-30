package com.ktdsuniversity.edu.replies.service;

import com.ktdsuniversity.edu.replies.vo.request.ModifyReplyVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistReplyVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.ReplyListVO;

public interface RepliesService {

  /**
   * 댓글 목록 조회
   * 
   * @param articleId
   * @return 댓글 목록
   */
  ReplyListVO readAllReplies(String articleId);

  /**
   * 댓글 등록 요청
   * 
   * @param articleId
   * @param registReplyVO
   * @return 등록된 댓글
   */
  RepliesVO createNewReply(String articleId, RegistReplyVO registReplyVO);

  /**
   * 댓글 수정 요청
   * 
   * @param articleId
   * @param replyId
   * @param modifyReplyVO
   * @return 수정된 댓글
   */
  RepliesVO updateReply(String articleId, String replyId, ModifyReplyVO modifyReplyVO);

  /**
   * 댓글 삭제 요청
   * 
   * @param articleId
   * @param replyId
   * @return 삭제한 댓글의 PK
   */
  String deleteReply(String articleId, String replyId);

  /**
   * 댓글 추천
   * 
   * @param articleId
   * @param replyId
   * @return 댓글 추천 수
   */
  long recommendOneReply(String articleId, String replyId);

}

