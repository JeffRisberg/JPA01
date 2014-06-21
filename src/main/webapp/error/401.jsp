<%
response.addHeader("WWW-Authenticate", "BASIC realm=\"My Web Site\"");
response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	request.setAttribute("errorpage", true);
%>
<jsp:forward page="/error/forbidden.jsp" />
