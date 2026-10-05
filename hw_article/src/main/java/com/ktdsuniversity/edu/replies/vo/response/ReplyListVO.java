package com.ktdsuniversity.edu.replies.vo.response;

import java.util.List;
import lombok.Data;

@Data
public class ReplyListVO {
  private long replyCount;

  private List<RepliesVO> replyList;
}
