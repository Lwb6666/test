<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
 
${param.isHome!=null?'<h2><a href="#">如何申请</a></h2>':'<h1><a href="#">如何申请</a></h1>' }
<div class="help-title" onclick="list(1)">
	<a href="##" id="help_title"></a><span></span>如何申请
</div>
<div class="help-content" id="list1" style="display: none;">
    <p>借款申请人通过顶玺官网-我要融资功能在线填写并提交借款申请信息，等待理财专员联系</p> 
	<p class="up" onclick="list(1)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
</div>
