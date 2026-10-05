package com.ktdsuniversity.edu.replies.web;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ktdsuniversity.edu.commons.util.ApiResponse;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;
import com.ktdsuniversity.edu.replies.service.RepliesService;
import com.ktdsuniversity.edu.replies.vo.request.ModifyReplyVO;
import com.ktdsuniversity.edu.replies.vo.request.RegistReplyVO;
import com.ktdsuniversity.edu.replies.vo.response.RepliesVO;
import com.ktdsuniversity.edu.replies.vo.response.ReplyListVO;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@RestController
@RequestMapping("/articles/{articleId}/replies")
public class RepliesController {

  private RepliesService repliesService;

  @GetMapping
  public ApiResponse<ReplyListVO> getReplies(@Pattern(regexp = "^AR-\\d{8}-\\d{6,8}$",
      message = "잘못된 요청입니다.") @PathVariable String articleId) {
    ReplyListVO replyList = this.repliesService.readAllReplies(articleId);
    return ApiResponse.OK(replyList);
  }

  @PostMapping
  public ApiResponse<RepliesVO> makeNewReply(
      @Pattern(regexp = "^AR-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String articleId,
      @Valid @ModelAttribute RegistReplyVO registReplyVO, BindingResult validationResult,
      HttpSession session) {
    if (validationResult.hasErrors()) {
      return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
    }

    MembersVO membersVO = (MembersVO) session.getAttribute("__LOGIN_USER__");
    if (membersVO == null) {
      throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
    }
    registReplyVO.setEmail(membersVO.getEmail());

    try {
      RepliesVO result = this.repliesService.createNewReply(articleId, registReplyVO);
      return ApiResponse.CREATED(result);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.FORBIDDEN(iae.getMessage());
    }

  }

  @PutMapping("/{replyId}")
  public ApiResponse<RepliesVO> updateReply(
      @Pattern(regexp = "^AR-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String articleId,
      @Pattern(regexp = "^RE-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String replyId,
      @Valid @ModelAttribute ModifyReplyVO modifyReplyVO, BindingResult validationResult,
      HttpSession session) {

    if (validationResult.hasErrors()) {
      return ApiResponse.BAD_REQUEST(validationResult.getFieldErrors());
    }

    MembersVO membersVO = (MembersVO) session.getAttribute("__LOGIN_USER__");
    if (membersVO == null) {
      throw new IllegalArgumentException("로그인이 필요한 기능입니다.");
    }
    modifyReplyVO.setEmail(membersVO.getEmail());


    try {
      RepliesVO result = this.repliesService.updateReply(articleId, replyId, modifyReplyVO);
      return ApiResponse.OK(result);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.FORBIDDEN(iae.getMessage());
    }
  }

  @DeleteMapping("/{replyId}")
  public ApiResponse<String> deleteReply(
      @Pattern(regexp = "^AR-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String articleId,
      @Pattern(regexp = "^RE-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String replyId) {
    try {
      String result = this.repliesService.deleteReply(articleId, replyId);
      return ApiResponse.OK(result);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.FORBIDDEN(iae.getMessage());
    }
  }

  @PutMapping("/recommend/{replyId}")
  public ApiResponse<Long> recommendOneReply(
      @Pattern(regexp = "^AR-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String articleId,
      @Pattern(regexp = "^RE-\\d{8}-\\d{6,8}$",
          message = "잘못된 요청입니다.") @PathVariable String replyId) {
    try {
      long result = this.repliesService.recommendOneReply(articleId, replyId);
      return ApiResponse.OK(result);
    } catch (IllegalArgumentException iae) {
      return ApiResponse.ERROR(iae.getMessage());
    }
  }

}
