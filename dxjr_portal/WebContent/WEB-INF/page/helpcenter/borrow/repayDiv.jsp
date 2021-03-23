<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
 
${param.isHome!=null?'<h2><a href="#">放款</a></h2>':'<h1><a href="#">放款</a></h1>' }
<div class="help-title" onclick="list(50)"><a href="javascript:void(0);" id="help_title">放款<span></span></a></div>
         <div class="help-content" id="list50">
             <p>借款标满标后，财务部门放款给借款人</p>
            <p class="up" onclick="list(50)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>        

