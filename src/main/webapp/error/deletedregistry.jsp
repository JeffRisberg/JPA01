<%@ taglib uri="http://jakarta.apache.org/taglibs/request" prefix="req" %>
<%@ taglib uri="http://www.justgive.org/taglibs" prefix="jg" %>
<%
	request.setAttribute("errorpage", true);
%>
<req:setattribute name="title">Deleted Registry</req:setattribute>
<req:setattribute name="metaDescription"></req:setattribute>
<req:setattribute name="metaKeywords"></req:setattribute>
<req:setattribute name="pageCode">DELETED REGISTRY</req:setattribute>

<req:setattribute name="subheader">true</req:setattribute>
<req:setattribute name="content">
<h1>Deleted Registry</h1>
<p>We're sorry, the registry you have tried to access has been removed by its owner.  If you think you've reached this page in error, you can <a href="/registries/search/">search for another registry</a>.</p>
 


</req:setattribute>

<%-- this sets the section for the page which controls the header graphic, nav and third column. This can be overrided in the URL request --%>
<req:setattribute name="section">about-us</req:setattribute>

<%-- use the following template for display --%>
<jsp:forward page="/templates/template_noCol.jsp" />

