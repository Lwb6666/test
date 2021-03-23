<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	${param.isHome!=null?'<h2><a href="#">业务模式</a></h2>':'<h1><a href="#">业务模式</a></h1>' }
         <div class="help-title" onclick="list(1)"><a href="javascript:void(0);" id="help_title">平台性质<span></span></a></div>
         <div class="help-content" id="list1" style="display: none;">
            <p>顶玺金融为专业P2P信息中介平台，为资金需求方和资金借出方提供撮合交易服务。顶玺金融官网上所发布的借款信息<%-- （净值标除外） --%>均由典当行等合作机构提供，同时合作机构在顶玺金融发布借款信息时，还需提供一定数额的“履约保证金”。</p>
            <p class="up" onclick="list(1)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
         <div class="help-title" onclick="list(2)"><a href="javascript:void(0);" id="help_title">什么是&ldquo;履约保证金&rdquo;?<span></span></a></div>
         <div class="help-content" id="list2">
            <p> 是指顶玺金融合作机构，如典当行等，在提供借款信息的同时，为保障出借人的财产安全而充值一定金额到平台，作为借款者到期还款的履约保证，该资金可用于投标但不可提现。</p>
            <p class="up" onclick="list(2)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
         <div class="help-title" onclick="list(3)"><a href="javascript:void(0);" id="help_title">如何启动安全保障计划<span></span></a></div>
         <div class="help-content" id="list3">
            <p>借款标出现逾期后，由顶玺金融启动“本金保障计划”用“风险备用金账户”进行债权回购，保障借出人的资金安全。同时顶玺金融会要求合作机构进行债权回购，如合作机构不履约则由顶玺金融扣除其在平台的“履约保证金”，根据双方签署的协议，通过法律手段追究合作机构的责任。</p>
            <p class="up" onclick="list(3)"><a href="javascript:void(0);" id="help_title">&nbsp;</a></p>
         </div>
