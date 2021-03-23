<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<%@ include file="/WEB-INF/page/common/taglib.jsp" %>
<script src="${basePath }/js/myaccount/calendar.js?version=<%=version%>"></script>
<div class="aboluo-top">我的待收</div>
					<div class="aboluo-w-700">
					<div class="aboluo-leftdiv">
							<div class="aboluo-tools">
								<div class="aboluo-calendar-select-year"></div>
								<div class="aboluo-calendar-month">
									<a class="aboluo-month-a-perv" href="javascript:;"></a> <a
										class="aboluo-month-a-next" href="javascript:;"></a>
								</div>
								<input type="button" class="aboluo-toToday" value="返回今天" />
							</div>
							<div class="aboluo-rilidiv">
								<table class="aboluo-rilitable" cellspacing="0" cellpadding="0">
									<thead class="aboluo-rilithead">
										<tr>
											<th>一</th>
											<th>二</th>
											<th>三</th>
											<th>四</th>
											<th>五</th>
											<th>六</th>
                                            <th>日</td>
						                </tr>
						             </thead>
						       </table>
                           </div>
        </div>
        <div class="aboluo-rightdiv">
            <p class="aboluo-xssj"></p>
            <p class="aboluo-currday"></p>
            <div id="collection">
             <div id="collect"  style="display: none;">
            <dl class="aboluo-drds">
                <dt>
				<span>当日待收<span>
			   </dt>
               <dd>
				<span id="daycollect" ></span>元
			   </dd>
            </dl>
            <p class="aboluo-db">
				<span>定</span><strong id="fixcollect" ></strong>元</p>
            <p class="aboluo-db">
			   <span>标</span><strong id="borrowcollect"></strong>元</p>
			 </div>
			 <div id="yescollect"  style="display: none;">
			<dl class="aboluo-drds" style="border-top:1px solid #ccc;">
                <dt><span>当日已收<span></dt>
                <dd><span id="dayyescollect" ></span>元</dd>
            </dl>
            <p class="aboluo-db"><span>定</span><strong id="fixyescollect" ></strong>元</p>
            
            <p class="aboluo-db"><span>标</span><strong id="borrowyescollect"></strong>元</p>
            </div>
            </div>
             <div id="collection1">
	           <dl class="aboluo-drds mt20 center">
						<p class="center">当日既无待收也无已收</p>
					</dl>
					<p class="center"><i class="iconfont f100 blue">&#xe61c;</i></p>
					
					<p class="center f16 blue">钱箱空空，好心酸！<br>快去投资吧！</p>
             </div>
             <div id="collection2">
	           <dl class="aboluo-drds mt20 center">
						<p class="center">当日既无待收也无已收</p>
					</dl>
					<p class="center"><i class="iconfont f100 blue">&#xe61c;</i></p>
					
					<p class="center f16 blue">钱箱空空，好心酸！<br>快去投资吧！</p>
             </div>
            <p class="aboluo-btn02">
                <a href="${basePath}/dingqibao.html">继续投资</a>
            </p>
            <p class="aboluo-ssjjr"></p>
        </div>
    </div> 