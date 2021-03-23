<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
 	// 发布的版本号，每次发布版本时将此版本号加1,其主要原因是引用最新的css文件，避免缓存
    String version = "1000050";
	response.setHeader("Cache-Control","no-store"); 
	response.setHeader("Pragrma","no-cache"); 
	response.setDateHeader("Expires",0); 
%>



<%-- 公共js --%>
<script type="text/javascript" src="${basePath }/js/jquery-1.11.0.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath }/js/jquery.form.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath }/js/formValidatorRegex.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath }/js/jquery-migrate-1.2.1.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath }/js/layer/layer.min.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath }/js/layer/extend/layer.ext.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath }/js/My97DatePicker/WdatePicker.js?version=<%=version%>"></script>
<script type="text/javascript" src="${basePath}/js/formatMoney.js?version=<%=version%>"></script>

<html>
<TABLE id="table" cellSpacing=0 cellPadding=0 width=790 align=center border=0>
  <TBODY>
    <TR> 
      <TD vAlign=top> <FORM id="queryForm" method="post" action='${basePath}/account/topup/sinapay/loseSend.html'>
          <br>
          <table width="781" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#000000">
            <tr> 
              <td bgcolor="#FFFFFF">新浪支付订单查询</td>
            </tr>
            <tr> 
              <td bgcolor="#FFFFFF"> <TABLE class=waikuan cellSpacing=0 cellPadding=0 
                        width=781 align=center border=0>
                  <TBODY>
                    <TR> 
                      <TD width="479" vAlign=top> <TABLE cellSpacing=0 cellPadding=0 width="100%" 
                              border=0>
                          <TBODY>
                            <TR bgColor=#f1f8fd> 
                              <TD class=landr width="30%" height=30>&nbsp;&raquo; 
                                <FONT color=#666666>开始时间：</FONT></TD>
                              <TD height=30>&nbsp; <INPUT size=45 name=startTime id="startTime"   onClick="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d %H:00:00',dateFmt:'yyyy/MM/dd HH:mm:ss'})"> 
                                <FONT 
                                color=#666666>&nbsp;</FONT> </TD>
                                
                            </TR>
                             <TR bgColor=#f1f8fd> 
                              <TD class=landr width="30%" height=30>&nbsp;&raquo; 
                                <FONT color=#666666>结束时间：</FONT></TD>
                              <TD height=30>&nbsp; <INPUT size=45 id="endTime" name=endTime  onClick="WdatePicker({skin:'whyGreen',startDate:'%y-%M-%d %H:00:00',dateFmt:'yyyy/MM/dd HH:mm:ss'})"> 
                                <FONT 
                                color=#666666>&nbsp;</FONT> </TD>
                                
                            </TR>
                             <TR bgColor=#f1f8fd> 
                              <TD class=landr width="30%" height=30>&nbsp;&raquo; 
                                <FONT color=#666666>新浪订单号：</FONT></TD>
                              <TD height=30>&nbsp; <INPUT size=45 name=v_oid id="v_oid"  > 
                                <FONT 
                                color=#666666>&nbsp;</FONT> </TD>
                                
                            </TR>

                           
                            <TR align=middle bgColor=#f1f8fd> 
                              <TD colSpan=2 height=40> <INPUT type=submit value=search name=Submit2 > 
                               
                              </TD>
                            </TR>
                          </TBODY>
                        </TABLE></TD>
                    </TR>
                  </TBODY>
                </TABLE></td>
            </tr>
          </table>
        </FORM></TD>
    </TR>
  </TBODY>
</TABLE>
</html>