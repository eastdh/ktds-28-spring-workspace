package com.ktdsuniversity.edu.members.dao;

import org.apache.ibatis.annotations.Mapper;
import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

@Mapper
public interface MembersDao {

  int selectEmailCount(String email);

  int selectNicknameCount(String nickname);

  int insertNewMember(RegistMembersVO registMembersVO);

  MembersVO selectMemberByEmail(String email);

}
