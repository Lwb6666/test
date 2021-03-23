<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
	<!-- 净值标：不显示用户信息和审核状态 -->
	<c:if test="${borrow.borrowtype != 3 && borrow.borrowtype != 4}">
	<div class="jkb-mian">
                	<div class="jkb-title">借贷人信息</div>
                    <table width="100%" border="0" class="jkb-table">
                      <tr>
                        <td>用户名<span class="blue mal20">${borrower.username }</span></td>
                        <c:if test="${borrow.isNotice==null}">   
                        <td>
                        <%-- 
                        	信用评级
                         <c:if test="${borrow.creditRating== 'A'}">
                           <span class="jkb-abcd jkb-a">A</span>
                         </c:if>
                          <c:if test="${borrow.creditRating== 'B'}">
                           <span class="jkb-abcd jkb-b">B</span>
                         </c:if>
                          <c:if test="${borrow.creditRating== 'C'}">
                           <span class="jkb-abcd jkb-c">C</span>
                         </c:if>
                          <c:if test="${borrow.creditRating== 'D'}">
                           <span class="jkb-abcd jkb-d">D</span>
                         </c:if>
                         --%>
                        </td>
                        </c:if>
                        <td></td>
                      </tr>
                    </table>
                    <div class="jkb-titbox">基本信息</div>
                     <table width="100%" border="0" class="jkb-table">
                       <c:if test="${borrow.isNotice!=null}"> 
                      <tr>
                        <td>年龄<span class="gary mal20">${borrower.age }</span></td>
                        <td>性别<span class="gary mal20">${borrower.sex }</span></td>
                        <td>学历<span class="gary mal20">${portal:desc(810, borrower.education)}</span></td>
                      </tr>
                       <tr>
                        <td>婚姻<span class="gary mal20">${portal:desc(811, borrower.maritalStatus)}</span></td>
                        <td colspan="2">借款用途<span class="gary mal20">${borrow.borrowUse}</span></td>
                      </tr>
                      </c:if>
                      <c:if test="${borrow.isNotice==null}"> 
                      <tr>
                        <td>年龄<span class="gary mal20">${borrower.age }</span></td>
                        <td>学历<span class="gary mal20">${portal:desc(810, borrower.education)}</span></td>
                        <td>婚姻<span class="gary mal20">${portal:desc(811, borrower.maritalStatus)}</span></td>
                      </tr>
                      </c:if>
                    </table>
                    <c:if test="${borrow.isNotice==null}"> 
                    <div class="jkb-titbox">资产信息</div>
                     <table width="100%" border="0" class="jkb-table">
                      <tr>
                        <td>收入<span class="gary mal20">${borrower.income }<c:if test="${null!=borrower.income }">元</c:if></span></td>
                      <c:if test="${borrow.borrowtype != 1}"> 
                        <td>房产<span class="gary mal20"><input name="hasHouse" disabled="disabled" type="radio"
							<c:if test="${null!=mortgageVo && null!=mortgageVo.hasHouse && mortgageVo.hasHouse==1 }">checked</c:if> />
							是&nbsp;&nbsp; <input name="hasHouse" disabled="disabled"
							type="radio" value=""
							<c:if test="${null==mortgageVo || null==mortgageVo.hasHouse || mortgageVo.hasHouse==0}">checked</c:if> />
							否&nbsp;&nbsp;</span></td>
                        <td>房贷<span class="gary mal20">
                          <input name="" type="radio" disabled="disabled"
							<c:if test="${null!=mortgageVo && null!=mortgageVo.hasHouse && mortgageVo.hasHouse==1 && null!=mortgageVo.hasHouseMortgage && mortgageVo.hasHouseMortgage==1}">checked</c:if> />
							是&nbsp;&nbsp; <input name="" type="radio" disabled="disabled"
							value=""
							<c:if test="${null==mortgageVo || null==mortgageVo.hasHouse || mortgageVo.hasHouse==0 || null==mortgageVo.hasHouseMortgage || mortgageVo.hasHouseMortgage==0}">checked</c:if> />
							否&nbsp;&nbsp;
                        </span></td> 
                        </c:if>
                      </tr>
                      <c:if test="${borrow.borrowtype != 1}"> 
                      <tr>
                        <td>车产<span class="gary mal20">
                        <input name="" type="radio" disabled="disabled"
							<c:if test="${null!=mortgageVo && null!=mortgageVo.hasCar && mortgageVo.hasCar==1}">checked</c:if> />
							是&nbsp;&nbsp; <input name="" type="radio" disabled="disabled"
							value=""
							<c:if test="${null==mortgageVo || null==mortgageVo.hasCar || mortgageVo.hasCar==0}">checked</c:if> />
							否&nbsp;&nbsp;
                        </span></td>
                        <td>车贷<span class="gary mal20">
                        <input name="" type="radio" disabled="disabled"
							<c:if test="${null!=mortgageVo && null!=mortgageVo.hasCar && mortgageVo.hasCar==1 && null!=mortgageVo.hasCarMortgage && mortgageVo.hasCarMortgage==1}">checked</c:if> />
							是&nbsp;&nbsp; <input name="" type="radio" disabled="disabled"
							value=""
							<c:if test="${null==mortgageVo || null==mortgageVo.hasCar || mortgageVo.hasCar==0 || null==mortgageVo.hasCarMortgage || mortgageVo.hasCarMortgage==0}">checked</c:if> />
							否&nbsp;&nbsp;
                        </span></td>
                      </tr> 
                      </c:if>
                    </table>
                    </c:if>
                    <c:if test="${borrow.isNotice==null}"> 
                    <div class="jkb-titbox">工作信息</div>
                     <table width="100%" border="0" class="jkb-table">
                      <tr>
                        <td>公司行业<span class="gary mal20">${borrower.industry}</span></td>
                        <td>公司规模<span class="gary mal20">${portal:desc(812, borrower.scale)}</span></td>
                        <td>工作岗位<span class="gary mal20">${borrower.jobTitle }</span></td>
                      </tr>
                      
                      <tr>
                        <c:if test="${borrow.borrowtype == 1}"> 
                        <td>所在城市<span class="gary mal20">${borrower.workCity }</span></td>
                        <td>工作时间<span class="gary mal20">${borrower.workYears }年</span></td>
                        </c:if>
                        <c:if test="${borrow.borrowtype != 1}"> 
                          <td>工作城市<span class="gary mal20">${borrower.workCity }</span></td>
                        </c:if>
                      </tr>
                    </table>
                    </c:if>
                   <c:if test="${borrow.isNotice!=null}"> 
                     <div class="jkb-titbox">工作信息</div>
	                     <table width="100%" border="0" class="jkb-table">
	                      <tr>
	                        <td>所在省份<span class="gary mal20">${borrower.workCity }</span></td>
	                        <td>工作时间<span class="gary mal20">
	                       <c:if test="${null!=borrower.workYears }">
	                         <c:if test="${borrower.workYears==1 }">1~3年(含)</c:if>
	                         <c:if test="${borrower.workYears==2 }">3~5年(含)</c:if>
	                         <c:if test="${borrower.workYears==3 }">5~10年(含)</c:if>
	                         <c:if test="${borrower.workYears==4 }">10年以上</c:if>
	                        </c:if></span></td>
                            <td>月收入<span class="gary mal20"><c:if test="${null!=borrower.userIncome }">${borrower.userIncome }-${borrower.userIncome+2000.00 }元</c:if></span></td>
	                      </tr>
	                    </table>
	                     </c:if>
                    <div class="jkb-titbox">信用信息</div>
                      <c:if test="${borrow.isNotice==null}"> 
                     <table width="100%" border="0" class="jkb-table">
                      <tr>
                        <td>成功借款<span class="gary mal20">${borrowDetailCreditVo.borrowCount }笔</span></td>
                        <td>借款总额<span class="gary mal20"><fmt:formatNumber value="${borrowDetailCreditVo.borrowAccount == null ?0:borrowDetailCreditVo.borrowAccount }"
								pattern="#,##0.##" />元</span></td>
                        <td>信用额度<span class="gary mal20"><fmt:formatNumber value="${borrowDetailCreditVo.creditMoney == null ?0:borrowDetailCreditVo.creditMoney}"
								pattern="#,##0.##" />元</span></td>
                      </tr>
                      
                      <tr>
                        <td>待还本息<span class="gary mal20"><fmt:formatNumber value="${borrowDetailCreditVo.waitToPay }"
								pattern="#,##0.##" />元</span></td>
                      </tr>
                    </table>
                   </c:if>
                     <c:if test="${borrow.isNotice!=null}"> 
                      <table width="100%" border="0" class="jkb-table">
                      <tr>
                        <td>信用额度<span class="gary mal20"><fmt:formatNumber value="${borrowDetailCreditVo.creditMoney == null ?0:borrowDetailCreditVo.creditMoney}"
								pattern="#,##0.##" />元</span></td>
                        <td>借款总额<span class="gary mal20"><fmt:formatNumber value="${borrowDetailCreditVo.borrowAccount == null ?0:borrowDetailCreditVo.borrowAccount }"
								pattern="#,##0.##" />元</span></td>
                        <td>待还本息<span class="gary mal20"><fmt:formatNumber value="${borrowDetailCreditVo.waitToPay }"
								pattern="#,##0.##" />元</span></td>
                      </tr>
                    </table>
                     </c:if>
                    </div>
                  <c:if test="${borrow.isNotice==null}"> 
                    <div class="jkb-mian">
					<div class="jkb-title">信用档案</div>
                    
                    <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="xuda-table">
                      <tr class="tbl-title">
                        <td width="33%">审核项目</td>
                        <td width="33%">状态</td>
                        <td width="33%">通过日期</td>
                      </tr>
                      <c:forEach items="${accountUploadDocs}" var="uploadDoc" varStatus="sta">
                     
                      <tr>
                        <c:if test="${borrow.borrowtype == 1}"> 
                        <td>${portal:desc(817, uploadDoc.style)}</td>
                        </c:if>
                         <c:if test="${borrow.borrowtype != 1}"> 
                        <td><a style="color:#999;" href="javascript:void(0);" onclick="toShowDocs('${uploadDoc.style}');">${portal:desc(817, uploadDoc.style)}</a></td>
                        </c:if>
						<td class="yellow"><i class="icon sucess"></i></td></td>
						<td><fmt:formatDate value="${uploadDoc.uploadTime }" pattern="yyyy-MM-dd" /></td>
                      </tr>
                       </c:forEach>
                    </table>
                   
                        <p>1.顶玺金融及其合作机构将始终秉持客观公正的原则，严控风险，最大程度的尽力确保借入者信息的真实性，但不保证审核信息100%无误。</p>
                        <p>2.借入者若长期逾期，其个人信息将被公布。</p>
                 
	
                    </div>
                    </c:if>
                     <c:if test="${borrow.isNotice!=null}"> 
                     <div class="jkb-mian">
					<div class="jkb-title">信用档案</div>
                    <table width="100%" border="0" cellspacing="0" cellpadding="0"  class="xuda-table">
                      <tr class="tbl-title">
                        <td width="33%">审核项目</td>
                        <td width="33%">状态</td>
                        <td width="33%">通过日期</td>
                      </tr>
                      <tr>
                        <td >身份证</td>
                        <td class="yellow"><i class="icon sucess"></i></td>
                        <td><fmt:formatDate value="${borrow.addtime}" pattern="yyyy-MM-dd"/></td>
                      </tr>
                      <tr>
                        <td>手机认证 </td>
                        <td class="yellow"><i class="icon sucess"></i></td>
                        <td><fmt:formatDate value="${borrow.addtime}" pattern="yyyy-MM-dd"/></td>
                      </tr>
                     <c:if test="${borrower.emergencyContact!=null}"> 
                      <tr>
                        <td>紧急联系人认证</td>
                        <td class="yellow"><i class="icon sucess"></i></td>
                        <td><fmt:formatDate value="${borrow.addtime}" pattern="yyyy-MM-dd"/></td>
                      </tr>
                      </c:if>
                      <c:if test="${borrower.userIncome>0}"> 
                      <tr>
                        <td>工作认证</td>
                        <td class="yellow"><i class="icon sucess"></i></td>
                        <td><fmt:formatDate value="${borrow.addtime}" pattern="yyyy-MM-dd"/></td>
                      </tr>
                      </c:if>
                      <c:if test="${borrower.userIncome>0}"> 
                      <tr>
                        <td>收入认证</td>
                        <td class="yellow"><i class="icon sucess"></i></td>
                        <td><fmt:formatDate value="${borrow.addtime}" pattern="yyyy-MM-dd"/></td>
                      </tr>
                      </c:if>
                      <tr>
                        <td>个人信用报告</td>
                        <td class="yellow"><i class="icon sucess"></i></td>
                           <td><fmt:formatDate value="${borrow.addtime}" pattern="yyyy-MM-dd"/></td>
                      </tr>
                    </table>
                        <p>1.顶玺金融及其合作机构将始终秉持客观公正的原则，严控风险，最大程度的尽力确保借入者信息的真实性，但不保证审核信息100%无误。</p>
                        <p>2.借入者若长期逾期，其个人信息将被公布。</p>
                    </div>
                      </c:if>                    
                   <c:if test="${borrow.content != null and borrow.content != '' }">
                     <div class="jkb-mian">
					<div class="jkb-title">借款描述</div>
                     <p> ${borrow.content }</p>
                    </div>
                    </c:if>
              </c:if>
    <c:if test="${borrow.borrowtype == 3 || borrow.borrowtype == 4}">
	             <div class="jkb-mian">
	             
                    <table width="100%" border="0" class="jkb-table">
                      <tr>
                       
                        <td>信用评级
                         <c:if test="${borrow.creditRating== 'A'}">
                           <span class="jkb-abcd jkb-a">A</span>
                         </c:if>
                          <c:if test="${borrow.creditRating== 'B'}">
                           <span class="jkb-abcd jkb-b">B</span>
                         </c:if>
                          <c:if test="${borrow.creditRating== 'C'}">
                           <span class="jkb-abcd jkb-c">C</span>
                         </c:if>
                          <c:if test="${borrow.creditRating== 'D'}">
                           <span class="jkb-abcd jkb-d">D</span>
                         </c:if>
                        </td>
                        <td></td>
                      </tr>
                    </table>

                    
                    <div class="jkb-titbox">信用信息</div>
                     <table width="100%" border="0" class="jkb-table">
                     
                      <tr>
                        <td>成功借款<span class="gary mal20">${borrowDetailCreditVo.borrowCount }笔</span></td>
                        <td>借款总额<span class="gary mal20"><fmt:formatNumber value="${borrowDetailCreditVo.borrowAccount == null ?0:borrowDetailCreditVo.borrowAccount }"
								pattern="#,##0.##" />元</span></td>
                        <td>信用额度<span class="gary mal20"><fmt:formatNumber value="${borrowDetailCreditVo.borrowAccount == null ?0:borrowDetailCreditVo.borrowAccount}"
								pattern="#,##0.##" />元</span></td>
                      </tr>
                      
                      <tr>
                        <td>待还本息<span class="gary mal20"><fmt:formatNumber value="${borrowDetailCreditVo.waitToPay }"
								pattern="#,##0.##" />元</span></td>
                      </tr>
                    </table>
                   
                    </div>
 
              </c:if>
<script type="text/javascript">
/**
 * 显示认证明细
 */
function toShowDocs(style){
	$.layer({
		type : 2,
	    fix : false,
		shade : [0.6, '#E3E3E3' , true],
		shadeClose : true,
		border : [10 , 0.7 , '#272822', true],
		title : ['',false],
		offset : ['50px',''],
		area : ['1000px','500px'],
		iframe : {src : '${basePath}/borrowdoc/showInner/${borrow.id}/'+style+'.html'},
		close : function(index){
			layer.close(index);
		}
	});	
}


$(document).ready(function (){
	// 判断其他选项存在否   BUG #3279 我要投标-投标专区-借款详情 中审核下的其他项按照思维逻辑应该放至最后    
	if ($('#borrowInfoId tr a').filter(function( index ) {
	    return $(this).attr('onclick')=="toShowDocs('4');";
	   })==null) {
		return;
	}
	//前端修改 将 "其他" 移动到最下面  background: #e1e4e9':'background: #fafbfc'
	$('#borrowInfoId').append($('#borrowInfoId tr a').filter(function( index ) {
	    return $(this).attr('onclick')=="toShowDocs('4');";
	  }).parent().parent());
	//修改 背景颜色 
	$('#borrowInfoId').find('tr[style]').each(function (index){
		 if(index%2==0){
		    $(this).attr("style","background: #e1e4e9");
		 }else{
			 $(this).attr("style","background: #fafbfc");
		 }
	});
});






</script>