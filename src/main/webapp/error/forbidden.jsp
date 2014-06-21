<%@ taglib uri="http://jakarta.apache.org/taglibs/request" prefix="req" %>
<%@ taglib uri="http://www.justgive.org/taglibs" prefix="jg" %>
<%
	request.setAttribute("errorpage", true);
%>
<req:setattribute name="title">Access Not Permitted</req:setattribute>
<req:setattribute name="metaDescription"></req:setattribute>
<req:setattribute name="metaKeywords"></req:setattribute>
<req:setattribute name="pageCode">FORBIDDEN</req:setattribute>
<req:setattribute name="subheader">true</req:setattribute>
<req:setattribute name="content">
 
<h1>Forbidden</h1>  
<p>You do not have permission to access this area.</p>
</req:setattribute>

<%-- this sets the section for the page which controls the header graphic, nav and third column. This can be overrided in the URL request --%>
<req:setattribute name="section">about-us</req:setattribute>

<%-- use the following template for display --%>
<jsp:forward page="/templates/template_noCol.jsp" />


