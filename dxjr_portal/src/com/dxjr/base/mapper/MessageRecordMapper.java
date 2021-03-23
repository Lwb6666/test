package com.dxjr.base.mapper;

import java.util.List;

import com.dxjr.base.entity.MessageRecord;

public interface MessageRecordMapper {
	
	 int insert(MessageRecord messageRecord);
	  
	 List<MessageRecord> findMessageRecord(MessageRecord messageRecord);
}