<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8" %>
<Form name=wq Action=https://pay3.chinabank.com.cn/receiveorder.jsp method=post>

<input type=hidden name="v_oid" value="${v_oid}">
<input type=hidden name="v_mid" value="${v_mid}">
<input type=hidden name="billNo_md5" value="${billNo_md5}">
<input type=hidden name="v_url" value="${v_url}">


</form>

<script language="javascript">

document.forms[0].submit();

</script>


