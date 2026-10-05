package com.ktdsuniversity.edu.members.service;

import com.ktdsuniversity.edu.members.vo.request.LoginMemberVO;
import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

public interface MembersService {

  MembersVO createNewMember(RegistMembersVO registMembersVO);

  MembersVO readMember(LoginMemberVO loginMemberVO);

  String updateLogoutStatus(String email);

  String deleteMember(String email, String password);

}
