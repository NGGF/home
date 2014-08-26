<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<div class="ErrorMsg">
<s:if test="hasActionErrors()">
    <s:iterator value="actionErrors">                   
          <s:property escape="false"/>        
    </s:iterator>
</s:if>
<s:if test="hasActionMessages()">
    <s:iterator value="actionMessages">
        <script type="text/javascript">
        	if(jQuery){
        		jQuery(function(){
        			niiMessage('<s:property escape="false"/>');
            	});
        	}else{
        		alert('<s:property escape="false"/>');
        	}
        </script>
    </s:iterator>
</s:if>
<s:if test="hasFieldErrors()">
    <s:iterator value="fieldErrors">
    	<s:property value="value"/>        
    </s:iterator>
</s:if>
</div>


