package com.dxjr.portal.stockright.entity;

import java.util.HashMap;
import java.util.Map;

/*
 * 委托状态  （1:认购  2：转让 )
 */
public enum IsProtocol { 

    YES(1,"同意"), 
    No(0,"默认"), 
    ;
    
    private IsProtocol(Integer code,String desc){
        this.code = code;
        this.desc = desc;
    }
    
    private Integer code;
    private String desc;
    
    public Integer getCode()
    {
        return code;
    }
    public String getDesc()
    {
        return desc;
    }

    public static Map<Integer,String> getEnumMap(){
        Map<Integer,String> map = new HashMap<Integer,String>();
        IsProtocol[] values =  values();
        for (IsProtocol item : values) {
            map.put(item.code, item.desc);
        }
        return map;
    }
    
    public static IsProtocol getByCode(Integer code){
    	IsProtocol[] values =  values();
        for (IsProtocol item : values)
        {
            if(item.getCode().equals(code)){
                return item;
            }
        }
        return null;
    }
}
