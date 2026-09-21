package com.ktdsuniversity.edu.members.service;

import org.springframework.stereotype.Service;
import com.ktdsuniversity.edu.members.dao.MembersDao;
import com.ktdsuniversity.edu.members.vo.response.MemberListVO;

@Service
public class MembersServiceImpl implements MembersService {

  private MembersDao membersDao;

  public MembersServiceImpl(MembersDao membersDao) {
    this.membersDao = membersDao;
  }


  @Override
  public MemberListVO getMembersList() {

    MemberListVO list = new MemberListVO();

    list.setMembersCount(this.membersDao.selectMemberCount());
    list.setMembersList(this.membersDao.selectAllMembers());


    return list;
  }

}
