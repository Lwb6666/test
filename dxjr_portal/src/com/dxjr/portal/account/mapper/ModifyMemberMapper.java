package com.dxjr.portal.account.mapper;

import com.dxjr.portal.account.vo.ModifyMember;

public interface ModifyMemberMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(ModifyMember record);

    int insertSelective(ModifyMember record);

    ModifyMember selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(ModifyMember record);

    int updateByPrimaryKey(ModifyMember record);
    
    ModifyMember findMemberById(Integer userId);
    
    public void updatePassGesturePwd(ModifyMember record);
    public void updateRrl(ModifyMember record);
    public void updateScw(ModifyMember record);
    public void updateTzj(ModifyMember record);
    public void updateWdty(ModifyMember record);
    public void updateTtx(ModifyMember record);
    
}