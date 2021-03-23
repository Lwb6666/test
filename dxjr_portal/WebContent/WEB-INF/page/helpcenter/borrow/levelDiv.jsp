<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
 
${param.isHome!=null?'<h2><a href="#">外访调查</a></h2>':'<h1><a href="#">外访调查</a></h1>' }

	<div class="help-title" onclick="list(42)">
		<a href="javascript:void(0);" id="help_title">外访调查<span></span></a>
	</div>
	<div class="help-content" id="list42">
		<p>合同订立后，权证人员陪同客户实际看房,之后前往公证处和房产交易中心办理相关手续</p>
		<p class="up" onclick="list(42)">
			<a href="javascript:void(0);" id="help_title">&nbsp;</a>
		</p>
	</div>

