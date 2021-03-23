package com.dxjr.portal.quickFinancing.service;

import javax.servlet.http.HttpServletRequest;

import com.dxjr.portal.quickFinancing.entity.QuickFinancing;

public interface QuickFinancingService {

   public String insertQucikFinance(QuickFinancing quickFinancing,HttpServletRequest request)throws Exception; 
}
