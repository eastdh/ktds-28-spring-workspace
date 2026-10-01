package com.ktdsuniversity.edu.members.service;

import com.ktdsuniversity.edu.members.vo.request.RegistMembersVO;
import com.ktdsuniversity.edu.members.vo.response.MembersVO;

public interface MembersService {

  MembersVO createNewMember(RegistMembersVO registMembersVO);

}
