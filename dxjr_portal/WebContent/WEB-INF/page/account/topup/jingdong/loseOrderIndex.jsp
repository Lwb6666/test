<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>
<html>
<TABLE cellSpacing=0 cellPadding=0 width=790 align=center border=0>
  <TBODY>
    <TR> 
      <TD vAlign=top> <FORM method="post" action="${basePath}/wx/online/loseSend.html">
          <br>
          <table width="481" border="0" align="center" cellpadding="4" cellspacing="1" bgcolor="#000000">
            <tr> 
              <td bgcolor="#FFFFFF">京东支付订单补单</td>
            </tr>
            <tr> 
              <td bgcolor="#FFFFFF"> <TABLE class=waikuan cellSpacing=0 cellPadding=0 
                        width=481 align=center border=0>
                  <TBODY>
                    <TR> 
                      <TD width="479" vAlign=top> <TABLE cellSpacing=0 cellPadding=0 width="100%" 
                              border=0>
                          <TBODY>
                            <TR bgColor=#f1f8fd> 
                              <TD class=landr width="39%" height=30>&nbsp;&raquo; 
                                <FONT color=#666666>京东支付订单号：</FONT>
                              </TD>
                              <TD height=30>&nbsp; <input size="40" name="tradeId" value="" maxlength="40">
                                <FONT color=#666666>&nbsp;</FONT> 
                              </TD>                                
                            </TR>
                            <TR align=middle bgColor=#f1f8fd> 
                              <TD colSpan=2 height=40> <INPUT type=submit value="提交" name=Submit2></TD>                             
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