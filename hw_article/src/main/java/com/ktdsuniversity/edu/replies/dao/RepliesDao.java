package com.ktdsuniversity.edu.replies.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ktdsuniversity.edu.replies.vo.request.ModifyReplyVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistReplyVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;

@Mapper
public interface RepliesDao {

  long selectRepliesCountByArticleId(String articleId);

  List<RepliesVO> selectAllRepliesByArticleId(String articleId);

  int insertNewReply(String articleId, RegistReplyVO registReplyVO);

  RepliesVO selectReplyByReplyId(String id);

  int updateReplyByArticleIdAndReplyId(String articleId, String replyId,
      ModifyReplyVO modifyReplyVO);

  int deleteReply(String articleId, String replyId);

  int updateIncreaseRecommendCount(String articleId, String replyId);

  long getRecommendCount(String articleId, String replyId);

}
