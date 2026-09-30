package com.ktdsuniversity.edu.replies.dao;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import com.ktdsuniversity.edu.replies.vo.request.ModifyReplyVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistReplyVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;

@Mapper
public interface RepliesDao {

  /**
   * 해당 게시글의 총 댓글 수를 반환
   * 
   * @param articleId
   * @return 게시글의 총 댓글 수
   */
  public long selectRepliesCountByArticleId(String articleId);

  /**
   * 해당 게시글의 모든 댓글을 반환
   * 
   * @param articleId
   * @return 게시글의 모든 댓글
   */
  public List<RepliesVO> selectAllRepliesByArticleId(String articleId);

  /**
   * 댓글의 아이디로 댓글의 정보를 조회한다.
   * 
   * @param id
   * @return 댓글의 PK로 조회한 댓글 정보
   */
  public RepliesVO selectReplyByReplyId(String id);

  /**
   * 클라이언트가 보내준 댓글 등록 정보를 데이터베이스에 insert 한다.
   * 
   * @param registReplyVO
   * @return inserted row 개수
   */
  public int insertNewReply(RegistReplyVO registReplyVO);


  /**
   * 게시글 아이디와 댓글 아이디 이메일로 본인 댓글인지 조회한 후
   * 댓글 수정 정보로 해당 댓글을 수정한다.
   * 
   * @param articleId
   * @param replyId
   * @param modifyReplyVO
   * @return updated row 개수
   */
  public int updateReplyByArticleIdAndReplyId(String articleId, String replyId,
      ModifyReplyVO modifyReplyVO);

  /**
   * 게시글 아이디와 댓글 아이디로 해당 댓글을 삭제한다.
   * 이 때 논리 삭제만 진행한다.
   * 
   * @param articleId
   * @param replyId
   * @return deleted row 개수
   */
  public int deleteReply(String articleId, String replyId);

  /**
   * 댓글의 추천수를 1 증가시킨다.
   * 
   * @param articleId
   * @param replyId
   * @return updated row 개수
   */
  public int updateIncreaseRecommendCount(String articleId, String replyId);

  /**
   * 게시글 아이디와 댓글 아이디로 해당 댓글의 추천수를 반환한다.
   * 
   * @param articleId
   * @param replyId
   * @return 댓글 추천 수
   */
  public long getRecommendCount(String articleId, String replyId);

}
