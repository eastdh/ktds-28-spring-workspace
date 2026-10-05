package com.ktdsuniversity.edu.replies.service;

import com.ktdsuniversity.edu.replies.vo.request.ModifyReplyVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistReplyVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.ReplyListVO;

public interface RepliesService {

  ReplyListVO readAllReplies(String articleId);

  RepliesVO createNewReply(String articleId, RegistReplyVO registReplyVO);

  RepliesVO updateReply(String articleId, String replyId, ModifyReplyVO modifyReplyVO);

  String deleteReply(String articleId, String replyId);

  long recommendOneReply(String articleId, String replyId);

}
