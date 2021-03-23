<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ include file="/WEB-INF/page/common/taglib.jsp"%>

<script type="text/javascript">
$('.choosebox1 li a').click(function(){
	var thisToggle = $(this).is('.size_radioToggle1') ? $(this) : $(this).prev();
	var checkBox = thisToggle.prev();
	checkBox.trigger('click');
	$('.size_radioToggle1').removeClass('current');
	thisToggle.addClass('current');
	RechargeBank=thisToggle.attr("value"); 
});		

</script>
</head>
<body>
         <div class="content choosebox1">
          <ul class="clearfix">
          <c:forEach items="${banks }" var="b" varStatus="i">
	          <li <c:if test="${i.index%4==3}">class='last'</c:if>>
					 <a href="javascript:void(0);" class="size_radioToggle1" value="${b.name }"><span class="value"><img
			                                src="${basePath }/images/myaccount/banklogo/${b.desc }.gif"/></span></a>
				</li>	
			</c:forEach>
        </ul>
        <input type="hidden" name="payChannel" value="debit" />
       </div>
