package com.dxjr.portal.stockright.entity;

import java.util.HashMap;
import java.util.Map;

/*
 * 委托状态  （1:认购  2：转让 )
 */
public enum EntrustType { 

    BUY(1,"认购"), 
    SELL(2,"转让"), 
    ;
    
    private EntrustType(Integer code,String desc){
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
        EntrustType[] values =  values();
        for (EntrustType item : values) {
            map.put(item.code, item.desc);
        }
        return map;
    }
    
    public static EntrustType getByCode(Integer code){
    	EntrustType[] values =  values();
        for (EntrustType item : values)
        {
            if(item.getCode().equals(code)){
                return item;
            }
        }
        return null;
    }
}
