package com.dxjr.portal.member.mapper;

import java.util.List;

import com.dxjr.portal.member.vo.BankcardPic;

public interface BankcardPicMapper {

	public List<BankcardPic> getPicsByBcId(int bcId) throws Exception;

	public int add(BankcardPic pic) throws Exception;

	public List<BankcardPic> queryBankcardPicsByUserId(Integer userId);

	public void deletePicById(int id);
	
	public String getReasonByUserId(int userId);

}
